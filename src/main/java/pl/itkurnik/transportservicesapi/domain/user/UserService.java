package pl.itkurnik.transportservicesapi.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.itkurnik.transportservicesapi.domain.user.dto.CreateUserRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator validator;

    public void registerUser(CreateUserRequest request) {
        validator.validateCreateRequestData(request);
        validator.validateIfUserExists(request.getEmail());

        createUser(request);
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
