package pl.sci.cashflowbackend.categories;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class PrivateCategories {
    @Id
    private String id;
    private String userId;
    private String name;

    public PrivateCategories(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
