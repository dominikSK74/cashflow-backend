package pl.sci.cashflowbackend.categories;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivateCategoriesRepository extends MongoRepository<PrivateCategories, String> {
}
