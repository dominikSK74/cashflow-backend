package pl.sci.cashflowbackend.expenses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesDto3 {
    private String id;
    private String name;
    private String cost;
    private String categories;
    private String date;
}
