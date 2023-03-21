package pl.sci.cashflowbackend.settings;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.sci.cashflowbackend.settings.Settings;

import java.util.Optional;

public interface SettingsRepository extends MongoRepository<Settings, String> {

    Optional<Settings> findSettingsByUserId(String userId);
}
