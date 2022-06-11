package pl.itkurnik.transportservicesapi.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.itkurnik.transportservicesapi.api.Constants;
import pl.itkurnik.transportservicesapi.api.ErrorCodes;
import pl.itkurnik.transportservicesapi.domain.user.dto.CreateUserRequest;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(CreateUserRequest request) {
        validateRequestData(request);
        validateIfUserExists(request.getEmail());
        createUser(request);
    }

    private void validateRequestData(CreateUserRequest request) {
        Pattern emailPattern = Pattern.compile("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$");
        Matcher matcher = emailPattern.matcher(request.getEmail());
        if (!matcher.matches()) {
            throw new RuntimeException(ErrorCodes.INVALID_EMAIL);
        }

        boolean passwordIsTooShort = request.getPassword().length() < Constants.MINIMAL_PASSWORD_LENGTH;
        if (passwordIsTooShort) {
            throw new RuntimeException(ErrorCodes.PASSWORD_TOO_SHORT);
        }
    }

    private void validateIfUserExists(String email) {
        Optional<UserEntity> userByEmail = findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new RuntimeException(String.format(ErrorCodes.USER_ALREADY_EXISTS, email));
        }
    }

    private void createUser(CreateUserRequest request) {
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
