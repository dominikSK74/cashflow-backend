package pl.sci.cashflowbackend.settings;

import org.springframework.stereotype.Service;
import pl.sci.cashflowbackend.settings.dto.GetSettingsDto;
import pl.sci.cashflowbackend.settings.enums.ChartTimeRange;
import pl.sci.cashflowbackend.settings.enums.ChartType;
import pl.sci.cashflowbackend.settings.enums.Language;
import pl.sci.cashflowbackend.settings.enums.Theme;

import java.util.Optional;

@Service
public class SettingsService {

    private SettingsRepository settingsRepository;

    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public boolean generateDefaultSettings(String userId){

        Optional<Settings> settingsOptional = settingsRepository.findSettingsByUserId(userId);

        if(settingsOptional.isEmpty()){

            Settings settings = new Settings(
                    userId,
                    ChartType.DOUGHNUT,
                    ChartTimeRange.MONTH,
                    Language.EN,
                    Theme.LIGHT
            );
            settingsRepository.insert(settings);
            return true;
        }
        return false;
    }

    public GetSettingsDto getSettings(String userId){

        Optional<Settings> settingsOptional = settingsRepository.findSettingsByUserId(userId);

        if(settingsOptional.isPresent()){
            return new GetSettingsDto(
                    settingsOptional.get().getChartType(),
                    settingsOptional.get().getChartTimeRange(),
                    settingsOptional.get().getLanguage(),
                    settingsOptional.get().getTheme()
                    );
        }
        return null;
    }
}
