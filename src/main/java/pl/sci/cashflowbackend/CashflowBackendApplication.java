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
//            Categories cat1 = new Categories("angielska nazwa","Produkty zbożowe");
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

//        Categories cat1 = new Categories("FUEL", "PALIWO");
//        Categories cat2 = new Categories("PARKING AND FEES", "PARKING I OPŁATY");
//        Categories cat3 = new Categories("TRANSPORTATION", "PRZEJAZDY");
//        Categories cat4 = new Categories("SERVICE AND PARTS", "SERWIS I CZĘŚCI");
//        Categories cat5 = new Categories("INSURANCE", "UBEZPIECZENIE");
//        Categories cat6 = new Categories("EATING OUT", "JEDZENIE POZA DOMEM");
//        Categories cat7 = new Categories("CIGARETTES", "PAPIEROSY");
//        Categories cat8 = new Categories("PETS", "ZWIERZĘTA");
//        Categories cat9 = new Categories("ALCOHOL", "ALKOHOL");
//        Categories cat10 = new Categories("HOUSEHOLD CHEMICALS", "CHEMIA DOMOWA");
//        Categories cat11 = new Categories("ACCESSORIES AND EQUIPMENT", "AKCESORIA I WYPOSAŻENIE");
//        Categories cat12 = new Categories("RENOVATION", "REMONT");
//        Categories cat13 = new Categories("GARDEN", "OGRÓD");
//        Categories cat14 = new Categories("HOME SERVICES", "USŁUGI DOMOWE");
//        Categories cat15 = new Categories("CHILDREN'S ITEMS", "ARTYKUŁY DZIECIĘCE");
//        Categories cat16 = new Categories("PRE-SCHOOL AND CARE", "PRZEDSZKOLE I OPIEKA");
//        Categories cat17 = new Categories("SCHOOL AND SUPPLIES", "SZKOŁA I WYPRAWKA");
//        Categories cat18 = new Categories("EXTRA-CURRICULAR ACTIVITIES", "ZAJĘCIA DODATKOWE");
//        Categories cat19 = new Categories("EDUCATION", "EDUKACJA");
//        Categories cat20 = new Categories("ELECTRONICS", "ELEKTRONIKA");
//        Categories cat21 = new Categories("MULTIMEDIA", "MULTIMEDIA");
//        Categories cat22 = new Categories("BOOKS", "KSIĄŻKI");
//        Categories cat23 = new Categories("GIFTS", "PREZENTY");
//        Categories cat24 = new Categories("FOOTWEAR", "OBUWIE");
//        Categories cat25 = new Categories("CLOTHING", "ODZIEŻ");
//        Categories cat26 = new Categories("HEALTH AND BEAUTY", "ZDROWIE I URODA");
//        Categories cat27 = new Categories("RENT AND LEASE", "CZYNSZ I WYNAJEM");
//        Categories cat28 = new Categories("GAS", "GAZ");
//        Categories cat29 = new Categories("HEATING", "OGRZEWANIE");
//        Categories cat30 = new Categories("FEES AND INTEREST", "OPŁATY I ODSETKI");
//        Categories cat31 = new Categories("TAXES", "PODATKI");
//        Categories cat32 = new Categories("ELECTRICITY", "PRĄD");
//        Categories cat33 = new Categories("LOAN REPAYMENTS", "SPŁATY RAT");
//        Categories cat34 = new Categories("TV", "TV");
//        Categories cat35 = new Categories("INTERNET", "INTERNET");
//        Categories cat36 = new Categories("PHONE", "TELEFON");
//
//        Categories cat37 = new Categories("INSURANCE", "UBEZPIECZENIA");
//        Categories cat38 = new Categories("WATER AND SEWER", "WODA I KANALIZACJA");
//        Categories cat39 = new Categories("TRAVEL AND TRIPS", "PODRÓŻE I WYJAZDY");
//        Categories cat40 = new Categories("SPORTS AND HOBBIES", "SPORT I HOBBY");
//        Categories cat41 = new Categories("OUTINGS AND EVENTS", "WYJŚCIA I WYDARZENIA");
//        Categories cat42 = new Categories("VEGETABLES", "WARZYWA");
//        Categories cat43 = new Categories("FRUITS", "OWOCE");
//        Categories cat44 = new Categories("DAIRY", "NABIAŁ");
//
//        Categories cat45 = new Categories("BAKERY", "PIECZYWO");
//        Categories cat46 = new Categories("MEAT", "MIĘSO");
//        Categories cat47 = new Categories("SWEETS", "SŁODYCZE");
//
//
//        repo.insert(cat1);
//        repo.insert(cat2);
//        repo.insert(cat3);
//        repo.insert(cat4);
//        repo.insert(cat5);
//        repo.insert(cat6);
//        repo.insert(cat7);
//        repo.insert(cat8);
//        repo.insert(cat9);
//        repo.insert(cat10);
//        repo.insert(cat11);
//        repo.insert(cat12);
//        repo.insert(cat13);
//        repo.insert(cat14);
//        repo.insert(cat15);
//        repo.insert(cat16);
//        repo.insert(cat17);
//        repo.insert(cat18);
//        repo.insert(cat19);
//        repo.insert(cat20);
//        repo.insert(cat21);
//        repo.insert(cat22);
//        repo.insert(cat23);
//        repo.insert(cat24);
//        repo.insert(cat25);
//        repo.insert(cat26);
//        repo.insert(cat27);
//        repo.insert(cat28);
//        repo.insert(cat29);
//        repo.insert(cat30);
//        repo.insert(cat31);
//        repo.insert(cat32);
//        repo.insert(cat33);
//        repo.insert(cat34);
//        repo.insert(cat35);
//        repo.insert(cat36);
//        repo.insert(cat37);
//        repo.insert(cat38);
//        repo.insert(cat39);
//        repo.insert(cat40);
//        repo.insert(cat41);
//        repo.insert(cat42);
//        repo.insert(cat43);
//        repo.insert(cat44);
//        repo.insert(cat45);
//        repo.insert(cat46);
//        repo.insert(cat47);

    }

}
