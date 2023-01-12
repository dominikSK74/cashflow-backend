package pl.sci.cashflowbackend.categories;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Categories {
    @Id
    private String id;
    private String name;

    public Categories(String name) {
        this.name = name;
    }
}
