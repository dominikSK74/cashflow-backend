package pl.sci.cashflowbackend.expenses;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document
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
    private LocalDate date;
}
