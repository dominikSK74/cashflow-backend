package pl.sci.cashflowbackend.expenses.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpensesDto {
    private String name;
    private String cost;
    private String categories;
    private String date;
}
