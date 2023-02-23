package pl.sci.cashflowbackend.products;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductsRepository extends MongoRepository<Products, String> {
    Optional<Products> findProductsByName(String name);
}
