package pl.itkurnik.transportservicesapi.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.itkurnik.transportservicesapi.api.ErrorCodes;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserEntity user) {
        validateIfUserExists(user);
        createUser(user);
    }

    private void validateIfUserExists(UserEntity user) {
        Optional<UserEntity> userByEmail = findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new RuntimeException(String.format(ErrorCodes.USER_ALREADY_EXISTS, user.getEmail()));
        }
    }

    private void createUser(UserEntity user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
