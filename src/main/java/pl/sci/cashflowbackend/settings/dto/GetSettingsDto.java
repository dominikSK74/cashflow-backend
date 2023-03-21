package pl.sci.cashflowbackend.settings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sci.cashflowbackend.settings.enums.ChartTimeRange;
import pl.sci.cashflowbackend.settings.enums.ChartType;
import pl.sci.cashflowbackend.settings.enums.Language;
import pl.sci.cashflowbackend.settings.enums.Theme;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSettingsDto {
    private ChartType chartType;
    private ChartTimeRange chartTimeRange;
    private Language language;
    private Theme theme;
}
