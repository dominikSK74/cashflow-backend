package pl.sci.cashflowbackend.expenses;

import net.sourceforge.tess4j.Tesseract;
import java.awt.image.BufferedImage;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.sci.cashflowbackend.categories.CategoryService;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto2;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto3;
import pl.sci.cashflowbackend.expenses.dto.ExpensesGetDataDto;
import pl.sci.cashflowbackend.jwt.Jwt;
import pl.sci.cashflowbackend.products.ProductsService;
import pl.sci.cashflowbackend.settings.SettingsService;
import pl.sci.cashflowbackend.shops.ShopsService;
import pl.sci.cashflowbackend.shops.templates.Biedronka;
import pl.sci.cashflowbackend.user.UserService;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExpensesService {
    private UserService userService;
    private CategoryService categoryService;
    private ExpensesRepository expensesRepository;
    private ShopsService shopsService;
    private ProductsService productsService;
    private Jwt jwt;
    private SettingsService settingsService;

    public ExpensesService(UserService userService,
                           CategoryService categoryService,
                           ExpensesRepository expensesRepository,
                           ShopsService shopsService,
                           ProductsService productsService,
                           Jwt jwt,
                           SettingsService settingsService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.expensesRepository = expensesRepository;
        this.shopsService = shopsService;
        this.productsService = productsService;
        this.jwt = jwt;
        this.settingsService = settingsService;
    }

    boolean addExpenses(String username, ArrayList<ExpensesDto> listDto){
        String userId = userService.findUserIdByUsername(username);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        AtomicBoolean conflict = new AtomicBoolean(false);
        ArrayList<Expenses> listExpenses = new ArrayList<>();

        listDto.forEach(element -> {

            String categoryId;
            if(this.settingsService.isEnglishLang(userId)){
                categoryId = categoryService.findCategoryIdByCategoryName(element.getCategories());
            }else{
                categoryId = categoryService.findCategoryIdByCategoryNamePl(element.getCategories());
            }

            String privateCategoryId = categoryService.findPrivateCategoryIdByPrivateCategoryName(element.getCategories());
            LocalDate date = LocalDate.parse(element.getDate(), formatter).plusDays(1);
            BigDecimal cost = new BigDecimal(element.getCost());
            String name = element.getName();

            Expenses expenses = new Expenses(userId, categoryId, privateCategoryId, cost, name, date);
            listExpenses.add(expenses);

            if(categoryId == "0" && privateCategoryId == "0"){
                conflict.set(true);
            }
        });
        // If conflict STOP adding and send HTTP status
        if(conflict.getOpaque()){
            return false;
        }

        listExpenses.forEach(exp -> {
            expensesRepository.insert(exp);
        });
        return true;
    }

    public ArrayList<ExpensesDto2> uploadImage(MultipartFile file, String username) throws IOException {

        String userId = this.userService.findUserIdByUsername(username);

        // GET RECEIPT LINES
        ArrayList<String> lines = doOCR(file);
        // GET SHOP
        String shop = checkShop(lines);
        LocalDate date = LocalDate.now();
        ArrayList<ExpensesDto2> expensesArrayList = new ArrayList<>();

        if(shop.equals("lidl")){
            date = getDateFromLidlReceipt(lines);
            expensesArrayList = lidl(lines);
        }else if(shop.equals("auchan")){
            System.out.println("auchan");
        }else if(shop.equals("Jeronimo Martins Polska S. A.")){
            date = Biedronka.getDate(lines);
            expensesArrayList = Biedronka.getProducts(lines);
        }else{
            return null;
        }

        // CATEGORY SEARCH
        expensesArrayList = productsService.getPossibleCategories(expensesArrayList, userId);

        // SET DATE
        String finalDate = date.toString();
        expensesArrayList.forEach(expenses -> {
            expenses.setDate(finalDate);
        });

        return expensesArrayList;
    }

    public ArrayList<String> doOCR(MultipartFile file) throws IOException{

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        ArrayList<String> lines = new ArrayList<>();
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("D:\\CashFlow\\cashflow-backend\\src\\main\\resources\\tessdata-main");
        tesseract.setLanguage("pol");

        try {
            String receipt = tesseract.doOCR(bufferedImage);
            String[] parts = receipt.split("\n");

            for(String part : parts){
                lines.add(part);
            }

        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }

    public String checkShop(ArrayList<String> lines){
        ArrayList<String> shops = shopsService.getAllShops();

        for(int i = 0; i<lines.size(); i++){
            for(int j = 0; j<shops.size(); j++){
                if(lines.get(i).toLowerCase().contains(shops.get(j).toLowerCase())){
                    return shops.get(j);
                }
            }
        }

        return null;
    }

    public ArrayList<ExpensesDto2> lidl(ArrayList<String> lines){
        ArrayList<ExpensesDto2> expensesArrayList = new ArrayList<>();

        // GET PRODUCTS
        lines.forEach( line -> {
            if (line.contains("*")) {
                String[] parts = line.split("\\*");
                String product = parts[0].replaceAll("[0-9,]+", "");

                String stringNumber = "0";
                if (parts[1].contains("A")){
                    stringNumber = parts[1].substring(0, parts[1].indexOf("A")).trim();
                    stringNumber = stringNumber.substring(stringNumber.indexOf(" ") + 1);
                }else if (parts[1].contains("B")){
                    stringNumber = parts[1].substring(0, parts[1].indexOf("B")).trim();
                    stringNumber = stringNumber.substring(stringNumber.indexOf(" ") + 1);
                }else if (parts[1].contains("C")){
                    stringNumber = parts[1].substring(0, parts[1].indexOf("C")).trim();
                    stringNumber = stringNumber.substring(stringNumber.indexOf(" ") + 1);
                }else if (parts[1].contains("D")){
                    stringNumber = parts[1].substring(0, parts[1].indexOf("D")).trim();
                    stringNumber = stringNumber.substring(stringNumber.indexOf(" ") + 1);
                }else if (parts[1].contains("E")){
                    stringNumber = parts[1].substring(0, parts[1].indexOf("E")).trim();
                    stringNumber = stringNumber.substring(stringNumber.indexOf(" ") + 1);
                }else if (parts[1].contains("F")){
                    stringNumber = parts[1].substring(0, parts[1].indexOf("F")).trim();
                    stringNumber = stringNumber.substring(stringNumber.indexOf(" ") + 1);
                }else if (parts[1].contains("G")){
                    stringNumber = parts[1].substring(0, parts[1].indexOf("G")).trim();
                    stringNumber = stringNumber.substring(stringNumber.indexOf(" ") + 1);
                }

                BigDecimal cost = new BigDecimal(Double.parseDouble(stringNumber.replace(",", ".")))
                        .setScale(2, RoundingMode.HALF_UP);

                ExpensesDto2 expenses = new ExpensesDto2();
                expenses.setName(product);
                expenses.setCost(cost);
                expensesArrayList.add(expenses);

            }
        });
        return expensesArrayList;
    }

    public LocalDate getDateFromLidlReceipt(ArrayList<String> lines){
        // GET DATE FROM RECEIPT
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        LocalDate date = null;

        for(int i = 0; i < lines.size(); i++){
            Matcher matcher = pattern.matcher(lines.get(i));
            if(matcher.find()){
                date = LocalDate.parse(lines.get(i));
            }
        }
        return date;
    }

    public ExpensesGetDataDto getDataByMonth(String token, int month, int year){

        String userId = this.userService.findUserIdByUsername(jwt.extractUsername(token.substring(7)));
        LocalDate start = LocalDate.of(year, month + 1, 1).withDayOfMonth(1);
        LocalDate end = start.with(TemporalAdjusters.lastDayOfMonth());
        List<Expenses> list = expensesRepository.findByUserIdAndDateBetween(userId, start, end.plusDays(2));
        list.forEach(e -> e.setDate(e.getDate().minusDays(1)));

        return getData(list, userId);
    }

    public ExpensesGetDataDto getDataByYear(String token, int year){

        String userId = this.userService.findUserIdByUsername(jwt.extractUsername(token.substring(7)));
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        List<Expenses> list = expensesRepository.findByUserIdAndDateBetween(userId, start, end.plusDays(2));
        list.forEach(e -> e.setDate(e.getDate().minusDays(1)));

        return getData(list, userId);
    }

    public ExpensesGetDataDto getDataByDay(String token, int day, int month, int year){
        String userId = this.userService.findUserIdByUsername(jwt.extractUsername(token.substring(7)));
        LocalDate date = LocalDate.of(year, month + 1, day);
        List<Expenses> list = expensesRepository.findByUserIdAndDate(userId, date.plusDays(1));
        list.forEach(e -> e.setDate(e.getDate().minusDays(1)));

        return getData(list, userId);
    }

    public ExpensesGetDataDto getDataByWeek(String token, LocalDate start, LocalDate end){
        String userId = this.userService.findUserIdByUsername(jwt.extractUsername(token.substring(7)));
        List<Expenses> list = expensesRepository.findByUserIdAndDateBetween(userId, start, end.plusDays(2));
        list.forEach(e -> e.setDate(e.getDate().minusDays(1)));

        return getData(list, userId);
    }

    public ExpensesGetDataDto getData(List<Expenses> list, String userId){
        Map<String, BigDecimal> map = new HashMap<>();
        ExpensesGetDataDto result = new ExpensesGetDataDto();
        list.forEach(object -> {
            String categoryName;

            if(object.getCategoryId().equals("0")){
                categoryName = this.categoryService.findPrivateCategoryNameById(object.getPrivateCategoryId());
            }else {
                if(this.settingsService.isEnglishLang(userId)){
                    categoryName = this.categoryService.findCategoryNameById(object.getCategoryId());
                }else{
                    categoryName = this.categoryService.findCategoryNamePlById(object.getCategoryId());
                }
            }

            if(map.containsKey(categoryName)){
                map.put(categoryName,  map.get(categoryName).add(object.getCost()));
            }else{
                map.put(categoryName, object.getCost());
            }
        });

        String[] categories = new String[map.size()];
        BigDecimal[] prices = new BigDecimal[map.size()];
        int index = 0;
        for(Map.Entry<String, BigDecimal> entry : map.entrySet()){
            categories[index] = entry.getKey();
            prices[index] = entry.getValue();
            index++;
        }

        result.setCategories(categories);
        result.setPrices(prices);

        return result;
    }

    public ArrayList<ExpensesDto3> getAllUserExpenses(String token){

        String bearer = token.substring(7);
        String username = jwt.extractUsername(bearer);
        String userId = this.userService.findUserIdByUsername(username);
        ArrayList<ExpensesDto3> expenses = new ArrayList<>();

        this.expensesRepository.findByUserId(userId).forEach(exp ->{

            String category;

            if(exp.getPrivateCategoryId().equals("0")){
                if(this.settingsService.isEnglishLang(userId)){
                    category = this.categoryService.findCategoryNameById(exp.getCategoryId());
                }else{
                    category = this.categoryService.findCategoryNamePlById(exp.getCategoryId());
                }
            }else{
                category = this.categoryService.findPrivateCategoryNameById(exp.getPrivateCategoryId());
            }

            expenses.add(new ExpensesDto3(exp.getId(), exp.getName(), exp.getCost().toString(), category,
                    exp.getDate().minusDays(1).toString()));
        });

        return expenses;
    }

    public boolean deleteExpense(String expenseId, String token){

        String bearer = token.substring(7);
        String username = jwt.extractUsername(bearer);
        String userId = this.userService.findUserIdByUsername(username);

        Optional<Expenses> expenses = this.expensesRepository.findByIdAndUserId(expenseId, userId);

        if(expenses.isPresent()){
            this.expensesRepository.deleteById(expenseId);
            return true;
        }
            return false;
    }

    public ExpensesDto3 getExpenseById(String expenseId, String token){
        String bearer = token.substring(7);
        String username = jwt.extractUsername(bearer);
        String userId = this.userService.findUserIdByUsername(username);
        Optional<Expenses> expensesOptional = this.expensesRepository.findById(expenseId);

        if(expensesOptional.isPresent()){
            String category;

            if(expensesOptional.get().getPrivateCategoryId().equals("0")){
                if(this.settingsService.isEnglishLang(userId)){
                    category = this.categoryService.findCategoryNameById(expensesOptional.get().getCategoryId());
                }else{
                    category = this.categoryService.findCategoryNamePlById(expensesOptional.get().getCategoryId());
                }
            }else{
                category = this.categoryService.findPrivateCategoryNameById(expensesOptional.get().getPrivateCategoryId());
            }

            ExpensesDto3 expensesDto3 = new ExpensesDto3(
                    expensesOptional.get().getId(),
                    expensesOptional.get().getName(),
                    expensesOptional.get().getCost().toString(),
                    category,
                    expensesOptional.get().getDate().toString()
            );

            return expensesDto3;
        }else{
            return null;
        }
    }

    public boolean editExpense(ExpensesDto3 expensesDto3, String token){
        String bearer = token.substring(7);
        String username = jwt.extractUsername(bearer);
        String userId = this.userService.findUserIdByUsername(username);

        Optional<Expenses> expenses = this.expensesRepository.findByIdAndUserId(expensesDto3.getId(), userId);
        Expenses finalExpense = new Expenses();

        if(expenses.isPresent()){
            finalExpense.setId(expenses.get().getId());
            finalExpense.setUserId(userId);
            finalExpense.setCost(new BigDecimal(expensesDto3.getCost()));
            finalExpense.setName(expensesDto3.getName());


            String categoryName = expensesDto3.getCategories();

            String category = categoryService.findPrivateCategoryIdByPrivateCategoryName(categoryName);

            if(category.equals("0")){
                if(this.settingsService.isEnglishLang(userId)){
                    category = this.categoryService.findCategoryIdByCategoryName(categoryName);
                }else{
                    category = this.categoryService.findCategoryIdByCategoryNamePl(categoryName);
                }
                finalExpense.setCategoryId(category);
                finalExpense.setPrivateCategoryId("0");
            }else{
                finalExpense.setCategoryId("0");
                finalExpense.setPrivateCategoryId(category);
            }


            finalExpense.setDate(LocalDate.parse(expensesDto3.getDate().substring(0,10)));

            if(expenses.get().getDate().minusDays(1).toString().equals(LocalDate.parse(expensesDto3.getDate().substring(0, 10)).toString())){
                finalExpense.setDate(LocalDate.parse(expensesDto3.getDate().substring(0, 10)).plusDays(1));
            }else{
                finalExpense.setDate(LocalDate.parse(expensesDto3.getDate().substring(0, 10)).plusDays(2));
            }

            this.expensesRepository.save(finalExpense);
            return true;
        }
        return false;
    }
}