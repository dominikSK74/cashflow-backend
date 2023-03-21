package pl.sci.cashflowbackend.settings;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.sci.cashflowbackend.settings.enums.ChartTimeRange;
import pl.sci.cashflowbackend.settings.enums.ChartType;
import pl.sci.cashflowbackend.settings.enums.Language;
import pl.sci.cashflowbackend.settings.enums.Theme;

@Data
@Document
public class Settings {

    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private String userId;

    private ChartType chartType;

    private ChartTimeRange chartTimeRange;

    private Language language;

    private Theme theme;

    public Settings(String userId, ChartType chartType, ChartTimeRange chartTimeRange, Language language, Theme theme) {
        this.userId = userId;
        this.chartType = chartType;
        this.chartTimeRange = chartTimeRange;
        this.language = language;
        this.theme = theme;
    }
}
