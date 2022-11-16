package pl.sci.cashflowbackend.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto = " +
                "email='" + email + '\'' +
                ", password='" + password;
    }
}
