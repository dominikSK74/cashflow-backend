package pl.sci.cashflowbackend.shops;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Shops {
    private String id;
    private String name;

    public Shops(String name) {
        this.name = name;
    }
}