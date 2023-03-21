package pl.sci.cashflowbackend.settings.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sci.cashflowbackend.jwt.Jwt;
import pl.sci.cashflowbackend.settings.SettingsService;
import pl.sci.cashflowbackend.settings.dto.GetSettingsDto;
import pl.sci.cashflowbackend.user.UserService;

@RestController
public class SettingsController {

    private UserService userService;
    private Jwt jwt;
    private SettingsService settingsService;

    public SettingsController(UserService userService,
                              Jwt jwt,
                              SettingsService settingsService) {
        this.userService = userService;
        this.jwt = jwt;
        this.settingsService = settingsService;
    }

    @GetMapping("/api/settings/get-settings")
    public ResponseEntity<GetSettingsDto> getSettings(@RequestHeader("Authorization") String token){

        String userId = this.userService.findUserIdByUsername(jwt.extractUsername(token.substring(7)));
        return ResponseEntity.ok(this.settingsService.getSettings(userId));
    }

    @PatchMapping("/api/settings/set-settings")
    public ResponseEntity<?> setSettings(@RequestHeader("Authorization") String token,
                                         @RequestBody GetSettingsDto getSettingsDto){

        String userId = this.userService.findUserIdByUsername(jwt.extractUsername(token.substring(7)));
        this.settingsService.setSettings(userId, getSettingsDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
