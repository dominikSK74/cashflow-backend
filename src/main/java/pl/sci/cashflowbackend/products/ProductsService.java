package pl.sci.cashflowbackend.products;

import org.springframework.stereotype.Service;
import pl.sci.cashflowbackend.categories.CategoryService;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto2;
import pl.sci.cashflowbackend.settings.SettingsService;
import pl.sci.cashflowbackend.user.UserService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductsService {

    private ProductsRepository productsRepository;
    private CategoryService categoryService;
    private SettingsService settingsService;
    private UserService userService;

    public ProductsService(ProductsRepository productsRepository,
                           CategoryService categoryService,
                           SettingsService settingsService,
                           UserService userService) {
        this.productsRepository = productsRepository;
        this.categoryService = categoryService;
        this.settingsService = settingsService;
        this.userService = userService;
    }

    public ArrayList<ExpensesDto2> getPossibleCategories(ArrayList<ExpensesDto2> expensesArrayList, String userId){

        expensesArrayList.forEach( expenses -> {
            Optional<Products> products = productsRepository.findProductsByName(expenses.getName());
            String category = null;

            if(products.isPresent()){
                if(this.settingsService.isEnglishLang(userId)){
                    category = categoryService.findCategoryNameById(products.get().getCategoryId());
                }else{
                    category = categoryService.findCategoryNamePlById(products.get().getCategoryId());
                }
            }
            expenses.setCategories(category);
        });
        return expensesArrayList;
    }

    public void setProductCategory(String name, String category, String username){

        String userId = this.userService.findUserIdByUsername(username);

        if(this.settingsService.isEnglishLang(userId)){
            if(categoryService.categoryIsExistByName(category)){
                String categoryId = categoryService.findCategoryIdByCategoryName(category);
                Products products = new Products(name, categoryId);
                productsRepository.insert(products);
            }
        }else{
            if(categoryService.categoryIsExistByNamePl(category)){
                String categoryId = categoryService.findCategoryIdByCategoryNamePl(category);
                Products products = new Products(name, categoryId);
                productsRepository.insert(products);
            }
        }
    }
}
