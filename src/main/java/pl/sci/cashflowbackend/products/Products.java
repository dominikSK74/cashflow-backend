package pl.sci.cashflowbackend.products;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Products {
    @Id
    private String id;
    private String name;
    private String categoryId;

    public Products(String name, String categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }
}
