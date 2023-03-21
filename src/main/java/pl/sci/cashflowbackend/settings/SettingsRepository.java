package pl.sci.cashflowbackend.settings;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SettingsRepository extends MongoRepository<Settings, String> {

    Optional<Settings> findSettingsByUserId(String userId);
}
