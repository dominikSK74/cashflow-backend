package pl.sci.cashflowbackend.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.sci.cashflowbackend.user.UserService;
import pl.sci.cashflowbackend.user.dto.UserDetailsDto;

@Service
public class CustomInMemoryUserDetailsManager implements UserDetailsService {
    private final UserService userService;

    public CustomInMemoryUserDetailsManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findUserDetailsByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", username)));
    }

    private UserDetails createUserDetails(UserDetailsDto detailsDto) {
        return User.builder()
                .username(detailsDto.getEmail())
                .password(detailsDto.getPassword())
                .roles(detailsDto.getRole())
                .build();
    }
}
