package pl.sci.cashflowbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class CashflowBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CashflowBackendApplication.class, args);
    }

}
