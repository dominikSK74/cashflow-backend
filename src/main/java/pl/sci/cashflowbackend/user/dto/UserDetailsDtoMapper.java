package pl.sci.cashflowbackend.user.dto;

import pl.sci.cashflowbackend.user.User;

public class UserDetailsDtoMapper {
    public static UserDetailsDto map(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String role = user.getRole().toString();
        return new UserDetailsDto(email, password, role);
    }
}
