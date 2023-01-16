package pl.sci.cashflowbackend.categories;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PrivateCategoriesRepository extends MongoRepository<PrivateCategories, String> {

    List<PrivateCategories> findAllByUserId(String id);
}
