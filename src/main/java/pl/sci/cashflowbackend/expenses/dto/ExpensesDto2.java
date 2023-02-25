package pl.sci.cashflowbackend.expenses.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ExpensesDto2 {
    private String name;
    private BigDecimal cost;
    private String categories;
    private String date;
}
