package pl.sci.cashflowbackend.expenses;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpensesRepository extends MongoRepository<Expenses, String> {

    List<Expenses> findByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate);
    List<Expenses> findByUserIdAndDate(String userId, LocalDate date);
}
