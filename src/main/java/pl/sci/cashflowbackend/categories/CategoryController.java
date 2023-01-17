package pl.sci.cashflowbackend.categories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sci.cashflowbackend.categories.dto.NewPrivateCategoryDto;
import pl.sci.cashflowbackend.jwt.Jwt;

import java.util.ArrayList;

@RestController
public class CategoryController {

    private CategoryService categoryService;
    private Jwt jwt;

    public CategoryController(CategoryService categoryService,
                              Jwt jwt) {
        this.categoryService = categoryService;
        this.jwt = jwt;
    }

    @GetMapping("/api/category/get-all")
    public ResponseEntity<ArrayList<String>> getAllCategories(@RequestHeader("Authorization") String token){
        String bearer = token.substring(7);
        String username = jwt.extractUsername(bearer);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories(username));
    }

    @PostMapping("/api/category/add-private-category")
    public ResponseEntity<?> addPrivateCategory(@RequestHeader("Authorization") String token,
                                                @RequestBody NewPrivateCategoryDto dto){
        String bearer = token.substring(7);
        String username = jwt.extractUsername(bearer);
        categoryService.addNewPrivateCategory(dto.getName(), username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
