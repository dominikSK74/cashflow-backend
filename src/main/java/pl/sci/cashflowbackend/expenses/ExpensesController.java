package pl.sci.cashflowbackend.expenses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import pl.sci.cashflowbackend.expenses.dto.ExpensesDto;
import pl.sci.cashflowbackend.jwt.Jwt;

import java.util.ArrayList;

@RestController
public class ExpensesController {

    private Jwt jwt;
    private ExpensesService expensesService;

    public ExpensesController(Jwt jwt, ExpensesService expensesService) {
        this.expensesService = expensesService;
        this.jwt = jwt;
    }

    @PostMapping("/api/expenses/add")
    public ResponseEntity<?> addExpenses(@RequestHeader("Authorization") String token,
                                                @RequestBody ArrayList<ExpensesDto> dtoList){
        String bearer = token.substring(7);
        String username = jwt.extractUsername(bearer);

        if(expensesService.addExpenses(username, dtoList)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
