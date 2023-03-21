package pl.sci.cashflowbackend.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sci.cashflowbackend.settings.SettingsService;
import pl.sci.cashflowbackend.user.dto.UserDetailsDto;
import pl.sci.cashflowbackend.user.dto.UserDetailsDtoMapper;
import pl.sci.cashflowbackend.user.dto.UserDto;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final Validator validator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final SettingsService settingsService;

    public UserService(
            Validator validator,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            SettingsService settingsService
    ) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.settingsService = settingsService;
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

            if(settingsService.generateDefaultSettings(findUserIdByUsername(userDto.getEmail()))){
                return true;
            }
            return false;
        }else{
            return false;
        }
    }

    public String getUserRoleByEmail(String email){
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()){
            return user.get().getRole().toString();
        }
        return null;
    }

    public Optional<UserDetailsDto> findUserDetailsByEmail(String email){
        return userRepository.findUserByEmail(email).map(UserDetailsDtoMapper::map);
    }

    public String findUserIdByUsername(String username){
        Optional<User> user = userRepository.findUserByEmail(username);
        if(user.isPresent()){
            return user.get().getId();
        }
        return null;
    }
}