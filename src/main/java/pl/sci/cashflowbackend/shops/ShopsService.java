package pl.sci.cashflowbackend.shops;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopsService {

    private ShopsRepository shopsRepository;

    public ShopsService(ShopsRepository shopsRepository) {
        this.shopsRepository = shopsRepository;
    }

    public ArrayList<String> getAllShops(){
        List<Shops> shops = shopsRepository.findAll();
        List<String> shopsNames = new ArrayList<>();

        shops.forEach( element ->{
            shopsNames.add(element.getName());
        });

        return (ArrayList<String>) shopsNames;
    }
}
