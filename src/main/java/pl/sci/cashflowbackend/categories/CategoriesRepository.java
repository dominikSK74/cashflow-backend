package pl.sci.cashflowbackend.categories;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoriesRepository extends MongoRepository<Categories, String> {
    Optional<Categories> findCategoriesByName(String name);
    Optional<Categories> findCategoriesByNamePl(String name);
}
