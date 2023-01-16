package pl.sci.cashflowbackend.categories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
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
}
