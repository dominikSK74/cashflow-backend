package pl.sci.cashflowbackend.user;

import jakarta.servlet.ServletOutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.sci.cashflowbackend.user.dto.UserDto;

import java.util.Optional;

@RestController
public class UserController {

    @PostMapping("/api/user/register")
    ResponseEntity<?> register(@RequestBody UserDto userDto){
        //System.out.println(userDto.toString());

        //TODO: Sprawdzić czy email jest poprawny oraz czy hasło posiada więcej niż 8 znaków
        //      Zwrócić błąd jeśli nie spełnia wymagań lub jeśli taki użytkownik już istnieje
        //      Utworzyć użytkownika w bazie danych jeśli dane są poprawne.

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
