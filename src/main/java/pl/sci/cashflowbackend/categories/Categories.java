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

    private String namePl;

    public Categories(String name, String namePl) {
        this.name = name;
        this.namePl = namePl;
    }
}
