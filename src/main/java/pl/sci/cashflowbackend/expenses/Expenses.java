package pl.sci.cashflowbackend.expenses;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document
@NoArgsConstructor
public class Expenses {
    @Id
    private String id;
    @NotNull
    private String userId;
    private String categoryId;
    private String privateCategoryId;
    @NotNull
    private BigDecimal cost;
    @NotNull
    private String name;
    @NotNull
    private LocalDate date;

    public Expenses(String userId, String categoryId, String privateCategoryId, BigDecimal cost, String name, LocalDate date) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.privateCategoryId = privateCategoryId;
        this.cost = cost;
        this.name = name;
        this.date = date;
    }
}
