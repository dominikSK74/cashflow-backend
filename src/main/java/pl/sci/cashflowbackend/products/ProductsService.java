package pl.sci.cashflowbackend.products;

import org.springframework.stereotype.Service;
import pl.sci.cashflowbackend.categories.Categories;
import pl.sci.cashflowbackend.categories.CategoryService;
import pl.sci.cashflowbackend.expenses.Expenses;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto2;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProductsService {

    private ProductsRepository productsRepository;
    private CategoryService categoryService;

    public ProductsService(ProductsRepository productsRepository,
                           CategoryService categoryService) {
        this.productsRepository = productsRepository;
        this.categoryService = categoryService;
    }

    public ArrayList<ExpensesDto2> getPossibleCategories(ArrayList<ExpensesDto2> expensesArrayList){

        expensesArrayList.forEach( expenses -> {
            Optional<Products> products = productsRepository.findProductsByName(expenses.getName());
            String category = null;

            if(products.isPresent()){
                category = categoryService.findCategoryNameById(products.get().getCategoryId());
            }
            expenses.setCategories(category);
        });
        return expensesArrayList;
    }

    public void setProductCategory(String name, String category){
        if(categoryService.categoryIsExistByName(category)){
            String categoryId = categoryService.findCategoryIdByCategoryName(category);
            Products products = new Products(name, categoryId);
            productsRepository.insert(products);
        }
    }
}
