package pl.sci.cashflowbackend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.sci.cashflowbackend.jwt.Jwt;
import pl.sci.cashflowbackend.user.dto.UserDto;

@RestController
public class UserController {

    private final UserService userService;
    private final Jwt jwt;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService,
                          Jwt jwt,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwt = jwt;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/api/user/register")
    ResponseEntity<?> register(@RequestBody UserDto userDto){
        if(userService.registerUser(userDto)){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<String> generateToken(@RequestBody UserDto userDto) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
            );
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid username/password");
        }
        return ResponseEntity.status(HttpStatus.OK).body(jwt.generateToken(userDto.getEmail()));
    }

    //TODO: Logowanie użytkownika i security
    //      Security Config
    //      CustomInMemoryUserDetails
    //      Cors Config
    //      JWT token
    //      User Authentication
}
