package pl.sci.cashflowbackend.expenses;

import org.springframework.stereotype.Service;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto;

import java.util.ArrayList;

@Service
public class ExpensesService {

    void addExpenses(String username, ArrayList<ExpensesDto> listDto){
        //TODO: konwert dane z listDTO na odpowiednie i dodanie do expenses
    }
}
