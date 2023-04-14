package pl.sci.cashflowbackend.categories;

import org.springframework.stereotype.Service;
import pl.sci.cashflowbackend.settings.SettingsService;
import pl.sci.cashflowbackend.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoriesRepository categoriesRepository;
    private PrivateCategoriesRepository privateCategoriesRepository;
    private UserService userService;
    private SettingsService settingsService;

    public CategoryService(CategoriesRepository categoriesRepository,
                           PrivateCategoriesRepository privateCategoriesRepository,
                           UserService userService,
                           SettingsService settingsService) {
        this.categoriesRepository = categoriesRepository;
        this.privateCategoriesRepository = privateCategoriesRepository;
        this.userService = userService;
        this.settingsService = settingsService;
    }

    ArrayList<String> getAllCategories(String username) {
        ArrayList<String> list = new ArrayList<>();
        List<Categories> categories = new ArrayList<>();
        List<PrivateCategories> privateCategories = new ArrayList<>();
        String userId = userService.findUserIdByUsername(username);

        categories = categoriesRepository.findAll();
        if(settingsService.isEnglishLang(userId)){
            categories.forEach(categories1 -> {
                list.add(categories1.getName());
            });
        }else{
            categories.forEach(categories1 -> {
                list.add(categories1.getNamePl());
            });
        }

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

    public String findCategoryIdByCategoryNamePl(String name){
        Optional<Categories> category = categoriesRepository.findCategoriesByNamePl(name);
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

    public String findCategoryNamePlById(String id){
        Optional<Categories> categories = categoriesRepository.findById(id);
        if(categories.isPresent()){
            return categories.get().getNamePl();
        }
        return "0";
    }

    public String findPrivateCategoryNameById(String id){
        Optional<PrivateCategories> privateCategories = privateCategoriesRepository.findById(id);
        if(privateCategories.isPresent()){
            return privateCategories.get().getName();
        }
        return "0";
    }

    public boolean categoryIsExistByName(String categoryName){
        Optional<Categories> categories = categoriesRepository.findCategoriesByName(categoryName);
        return categories.isPresent();
    }

    public boolean categoryIsExistByNamePl(String categoryName){
        Optional<Categories> categories = categoriesRepository.findCategoriesByNamePl(categoryName);
        return categories.isPresent();
    }
}
