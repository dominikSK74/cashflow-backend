package pl.sci.cashflowbackend.expenses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto2;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto3;
import pl.sci.cashflowbackend.expenses.dto.ExpensesGetDataDto;
import pl.sci.cashflowbackend.jwt.Jwt;

import java.time.LocalDate;
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

    @PostMapping("/api/expenses/upload-image")
    public ResponseEntity<ArrayList<ExpensesDto2>> uploadImage(@RequestHeader("Authorization") String token,
                                                               @RequestParam("image") MultipartFile file) throws Exception {

        String bearer = token.substring(7);
        String username = jwt.extractUsername(bearer);

        ArrayList<ExpensesDto2> expensesDto2ArrayList = expensesService.uploadImage(file, username);

        if(expensesDto2ArrayList.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }else{
            ObjectMapper mapper = new ObjectMapper();
            String json = "";

            try {
                json = mapper.writeValueAsString(expensesDto2ArrayList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(expensesDto2ArrayList, httpHeaders, HttpStatus.OK);
        }
    }

    @GetMapping("/api/expenses/get-data")
    public ResponseEntity<ExpensesGetDataDto> getDataByMonth(@RequestHeader("Authorization") String token,
                                     @RequestParam("month") int month,
                                     @RequestParam("year") int year){

        ExpensesGetDataDto result = this.expensesService.getDataByMonth(token, month, year);

        if(result.checkData()){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/api/expenses/get-data-year")
    public ResponseEntity<ExpensesGetDataDto> getDataByYear(@RequestHeader("Authorization") String token,
                                                      @RequestParam("year") int year){

        ExpensesGetDataDto result = this.expensesService.getDataByYear(token, year);

        if(result.checkData()){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/api/expenses/get-data-day")
    public ResponseEntity<ExpensesGetDataDto> getDataByDay(@RequestHeader("Authorization") String token,
                                                             @RequestParam("day") int day,
                                                             @RequestParam("month") int month,
                                                             @RequestParam("year") int year){

        ExpensesGetDataDto result = this.expensesService.getDataByDay(token, day, month, year);

        if(result.checkData()){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/api/expenses/get-data-week")
    public ResponseEntity<ExpensesGetDataDto> getDataByWeek(@RequestHeader("Authorization") String token,
                                                            @RequestParam("firstDay") int firstDay,
                                                            @RequestParam("firstMonth") int firstMonth,
                                                            @RequestParam("firstYear") int firstYear,
                                                            @RequestParam("lastDay") int lastDay,
                                                            @RequestParam("lastMonth") int lastMonth,
                                                            @RequestParam("lastYear") int lastYear){

        LocalDate start = LocalDate.of(firstYear, firstMonth + 1, firstDay);
        LocalDate end = LocalDate.of(lastYear, lastMonth + 1, lastDay);

        ExpensesGetDataDto result = this.expensesService.getDataByWeek(token, start, end);

        if(result.checkData()){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/api/expenses/get-all-user-expenses")
    public ResponseEntity<ArrayList<ExpensesDto3>> getAllUserExpenses(@RequestHeader("Authorization") String token){

        return ResponseEntity.ok(this.expensesService.getAllUserExpenses(token));
    }

    @DeleteMapping("/api/expenses/delete-expense/{expenseId}")
    public ResponseEntity<?> deleteExpense(@RequestHeader("Authorization") String token,
                                           @PathVariable String expenseId){

        if(this.expensesService.deleteExpense(expenseId, token)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @GetMapping("/api/expenses/get-expense-by-id")
    public ResponseEntity<ExpensesDto3> getExpenseById(@RequestHeader("Authorization") String token,
                                                       @RequestParam("expenseId") String expenseId){

        ExpensesDto3 expensesDto3 = this.expensesService.getExpenseById(expenseId, token);
        if(expensesDto3 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(expensesDto3);
    }

    @PutMapping("/api/expenses/edit-expense")
    public ResponseEntity<?> editExpense(@RequestHeader("Authorization") String token,
                                         @RequestBody ExpensesDto3 expensesDto3){
        if( this.expensesService.editExpense(expensesDto3, token)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}