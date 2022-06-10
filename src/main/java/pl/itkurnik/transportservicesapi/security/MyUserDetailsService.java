package pl.itkurnik.transportservicesapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.itkurnik.transportservicesapi.api.ErrorCodes;
import pl.itkurnik.transportservicesapi.domain.user.UserEntity;
import pl.itkurnik.transportservicesapi.domain.user.UserService;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userByEmail = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCodes.USER_NOT_FOUND));


        return new User(userByEmail.getEmail(), userByEmail.getPassword(), Collections.emptyList());
    }

    public Boolean validateToken(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            UserDetails user = loadUserByUsername(username);
            return jwtUtil.validateToken(token, user);
        } catch (Exception e) {
            return false;
        }
    }
}
