package pl.sci.cashflowbackend.products;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.sci.cashflowbackend.jwt.Jwt;
import pl.sci.cashflowbackend.products.dto.ProductDto;

@RestController
public class ProductsController {

    private ProductsService productsService;
    private Jwt jwt;

    public ProductsController(ProductsService productsService,
                              Jwt jwt) {
        this.productsService = productsService;
        this.jwt = jwt;
    }

    @PostMapping("/api/products/set-category")
    public ResponseEntity<?> setProductCategory(@RequestHeader("Authorization") String token,
                                                @RequestBody ProductDto product){

        String bearer = token.substring(7);
        String username = jwt.extractUsername(bearer);

        this.productsService.setProductCategory(product.getName(), product.getCategory(), username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}