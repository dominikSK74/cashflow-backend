package pl.sci.cashflowbackend.user.dto;

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
