package pl.sci.cashflowbackend.expenses;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpensesRepository extends MongoRepository<Expenses, String> {

    List<Expenses> findByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate);
    List<Expenses> findByUserIdAndDate(String userId, LocalDate date);
    List<Expenses> findByUserId(String userId);

    Optional<Expenses> findByIdAndUserId(String id, String userId);
    void deleteById(String id);
}
