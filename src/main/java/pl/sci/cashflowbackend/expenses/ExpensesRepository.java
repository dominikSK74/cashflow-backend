package pl.sci.cashflowbackend.expenses;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpensesRepository extends MongoRepository<Expenses, String> {
}
