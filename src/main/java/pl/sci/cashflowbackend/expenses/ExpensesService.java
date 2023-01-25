package pl.sci.cashflowbackend.expenses;

import org.springframework.stereotype.Service;
import pl.sci.cashflowbackend.categories.CategoryService;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto;
import pl.sci.cashflowbackend.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ExpensesService {
    private UserService userService;
    private CategoryService categoryService;
    private ExpensesRepository expensesRepository;

    public ExpensesService(UserService userService,
                           CategoryService categoryService,
                           ExpensesRepository expensesRepository) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.expensesRepository = expensesRepository;
    }

    boolean addExpenses(String username, ArrayList<ExpensesDto> listDto){
        String userId = userService.findUserIdByUsername(username);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        AtomicBoolean conflict = new AtomicBoolean(false);
        ArrayList<Expenses> listExpenses = new ArrayList<>();

        listDto.forEach(element -> {
            String categoryId = categoryService.findCategoryIdByCategoryName(element.getCategories());
            String privateCategoryId = categoryService.findPrivateCategoryIdByPrivateCategoryName(element.getCategories());
            LocalDate date = LocalDate.parse(element.getDate(), formatter).plusDays(1);
            BigDecimal cost = new BigDecimal(element.getCost());
            String name = element.getName();

            Expenses expenses = new Expenses(userId, categoryId, privateCategoryId, cost, name, date);
            listExpenses.add(expenses);

            if(categoryId == "0" && privateCategoryId == "0"){
                conflict.set(true);
            }
        });
        // If conflict STOP adding and send HTTP status
        if(conflict.getOpaque()){
            return false;
        }

        listExpenses.forEach(exp -> {
            expensesRepository.insert(exp);
        });
        return true;
    }
}