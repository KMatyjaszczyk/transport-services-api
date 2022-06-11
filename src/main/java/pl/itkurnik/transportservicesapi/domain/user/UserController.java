package pl.itkurnik.transportservicesapi.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.itkurnik.transportservicesapi.api.ErrorCodes;
import pl.itkurnik.transportservicesapi.domain.user.dto.CreateUserRequest;
import pl.itkurnik.transportservicesapi.domain.user.dto.TokenValidationResponse;
import pl.itkurnik.transportservicesapi.security.JwtUtil;
import pl.itkurnik.transportservicesapi.security.MyUserDetailsService;
import pl.itkurnik.transportservicesapi.security.model.AuthenticationRequest;
import pl.itkurnik.transportservicesapi.security.model.AuthenticationResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        log.info("Logging in with username {}", request.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException(ErrorCodes.INCORRECT_USERNAME_OR_PASSWORD, e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/register")
    public void register(@RequestBody CreateUserRequest request) {
        log.info("Registering user {}", request.getEmail());
        userService.registerUser(request);
    }

    @PostMapping("/validate")
    public TokenValidationResponse validateToken(@RequestBody String token) {
        log.info("Validating token {}", token);
        return new TokenValidationResponse(userDetailsService.validateToken(token));
    }
}
