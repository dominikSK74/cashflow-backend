package pl.sci.cashflowbackend.categories;

import org.springframework.stereotype.Service;
import pl.sci.cashflowbackend.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoriesRepository categoriesRepository;
    private PrivateCategoriesRepository privateCategoriesRepository;
    private UserService userService;

    public CategoryService(CategoriesRepository categoriesRepository,
                           PrivateCategoriesRepository privateCategoriesRepository,
                           UserService userService) {
        this.categoriesRepository = categoriesRepository;
        this.privateCategoriesRepository = privateCategoriesRepository;
        this.userService = userService;
    }

    ArrayList<String> getAllCategories(String username) {
        ArrayList<String> list = new ArrayList<>();
        List<Categories> categories = new ArrayList<>();
        List<PrivateCategories> privateCategories = new ArrayList<>();

        categories = categoriesRepository.findAll();
        categories.forEach(categories1 -> {
            list.add(categories1.getName());
        });

        String userId = userService.findUserIdByUsername(username);
        privateCategories = privateCategoriesRepository.findAllByUserId(userId);
        privateCategories.forEach(privateCategories1 -> {
            list.add(privateCategories1.getName());
        });

        return list;
    }

    void addNewPrivateCategory(String categoryName, String username){
        String userId = userService.findUserIdByUsername(username);
        PrivateCategories privateCategories = new PrivateCategories(userId, categoryName);
        privateCategoriesRepository.insert(privateCategories);
    }

    public String findCategoryIdByCategoryName(String name){
        Optional<Categories> category = categoriesRepository.findCategoriesByName(name);
        if(category.isPresent()){
            return category.get().getId();
        }
      return "0";
    }

    public String findPrivateCategoryIdByPrivateCategoryName(String name){
        Optional<PrivateCategories> privateCategory = privateCategoriesRepository.findPrivateCategoriesByName(name);
        if(privateCategory.isPresent()){
            return privateCategory.get().getId();
        }
        return "0";
    }

    public String findCategoryNameById(String id){
        Optional<Categories> categories = categoriesRepository.findById(id);
        if(categories.isPresent()){
            return categories.get().getName();
        }
        return "0";
    }
}
