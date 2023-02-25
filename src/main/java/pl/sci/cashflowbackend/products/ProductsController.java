package pl.sci.cashflowbackend.products;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.sci.cashflowbackend.products.dto.ProductDto;

@RestController
public class ProductsController {

    private ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/api/products/set-category")
    public ResponseEntity<?> setProductCategory(@RequestBody ProductDto product){

        this.productsService.setProductCategory(product.getName(), product.getCategory());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}