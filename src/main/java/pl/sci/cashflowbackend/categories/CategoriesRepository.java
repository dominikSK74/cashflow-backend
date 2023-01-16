package pl.sci.cashflowbackend.categories;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoriesRepository extends MongoRepository<Categories, String> {

}
