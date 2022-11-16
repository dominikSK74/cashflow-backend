package pl.sci.cashflowbackend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.sci.cashflowbackend.user.dto.UserDto;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user/register")
    ResponseEntity<?> register(@RequestBody UserDto userDto){
        if(userService.registerUser(userDto)){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    //TODO: Logowanie użytkownika i security
    //      Security Config
    //      CustomInMemoryUserDetails
    //      Cors Config
    //      JWT token
    //      User Authentication
}
