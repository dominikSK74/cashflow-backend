package pl.sci.cashflowbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.sci.cashflowbackend.categories.Categories;
import pl.sci.cashflowbackend.categories.CategoriesRepository;
import pl.sci.cashflowbackend.categories.PrivateCategories;
import pl.sci.cashflowbackend.categories.PrivateCategoriesRepository;
import pl.sci.cashflowbackend.products.Products;
import pl.sci.cashflowbackend.products.ProductsRepository;
import pl.sci.cashflowbackend.shops.Shops;
import pl.sci.cashflowbackend.shops.ShopsRepository;

//exclude={SecurityAutoConfiguration.class}
@SpringBootApplication
public class CashflowBackendApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CashflowBackendApplication.class, args);

//        ProductsRepository repo4 = context.getBean(ProductsRepository.class);
//        Products mleko = new Products("Wypas. Mleko %  ", "63c0041b6963bd0dc4509251");
//        repo4.insert(mleko);



//        ShopsRepository repo3 = context.getBean(ShopsRepository.class);
//
//        Shops lidl = new Shops("lidl");
//        Shops auchan = new Shops("auchan");
//
//        repo3.insert(lidl);
//        repo3.insert(auchan);


//        CategoriesRepository repo = context.getBean(CategoriesRepository.class);
//        PrivateCategoriesRepository repo2 = context.getBean(PrivateCategoriesRepository.class);
//
//        //Dodanie kategorii
//        Categories cat1 = new Categories("Produkty zbożowe");
//        Categories cat2 = new Categories("Mleko i produkty mleczne");
//        Categories cat3 = new Categories("Cukier i słodycze");
//        Categories cat4 = new Categories("Warzywa i owoce");
//
//        repo.insert(cat1);
//        repo.insert(cat2);
//        repo.insert(cat3);
//        repo.insert(cat4);
//
//        PrivateCategories priv1 = new PrivateCategories("63752e607dd3a55eaf5636d5", "Paliwo");
//        PrivateCategories priv2 = new PrivateCategories("63c58136caa33a640df3acf9", "Tytoń");
//
//        repo2.insert(priv1);
//        repo2.insert(priv2);



    }

}
