package pl.sci.cashflowbackend.expenses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesGetDataDto {
    private String[] categories;
    private BigDecimal[] prices;

    public boolean checkData(){
        if(categories.length <= 0 || prices.length <= 0){
            return false;
        }
        return true;
    }
}
