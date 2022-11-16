package pl.sci.cashflowbackend.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sci.cashflowbackend.user.dto.UserDto;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final Validator validator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            Validator validator,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    boolean userValidator(User user){
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if (!constraintViolations.isEmpty()) {
            //Not valid data
            return false;
        } else {
            //Valid data
            return true;
        }
    }

    boolean emailIsExist(UserDto userDto){
        Optional<User> user = userRepository.findUserByEmail(userDto.getEmail());
        if(user.isPresent()){
            return true;
        }else{
            return false;
        }
    }

    boolean registerUser(UserDto userDto){
        User user = new User(userDto.getEmail(), userDto.getPassword(), Role.USER);
        if(userValidator(user) && !emailIsExist(userDto)){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.insert(user);
            return true;
        }else{
            return false;
        }
    }
}