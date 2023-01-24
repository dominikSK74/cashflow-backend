package pl.sci.cashflowbackend.categories;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PrivateCategoriesRepository extends MongoRepository<PrivateCategories, String> {

    List<PrivateCategories> findAllByUserId(String id);
    Optional<PrivateCategories> findPrivateCategoriesByName(String name);
}
