package pl.itkurnik.transportservicesapi.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.itkurnik.transportservicesapi.api.Constants;
import pl.itkurnik.transportservicesapi.api.ErrorCodes;
import pl.itkurnik.transportservicesapi.domain.user.dto.CreateUserRequest;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private static final Pattern emailPattern = Pattern.compile("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$");

    private final UserRepository userRepository;

    public void validateCreateRequestData(CreateUserRequest request) {
        boolean emailIsValid = emailPattern.matcher(request.getEmail()).matches();
        if (!emailIsValid) {
            throw new RuntimeException(ErrorCodes.INVALID_EMAIL);
        }

        boolean passwordIsTooShort = request.getPassword().length() < Constants.MINIMAL_PASSWORD_LENGTH;
        if (passwordIsTooShort) {
            throw new RuntimeException(ErrorCodes.PASSWORD_TOO_SHORT);
        }
    }

    public void validateIfUserExists(String email) {
        Optional<UserEntity> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new RuntimeException(String.format(ErrorCodes.USER_ALREADY_EXISTS, email));
        }
    }
}
