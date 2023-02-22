package pl.sci.cashflowbackend.expenses;

import net.sourceforge.tess4j.Tesseract;
import java.awt.image.BufferedImage;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.sci.cashflowbackend.categories.CategoryService;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto;
import pl.sci.cashflowbackend.shops.ShopsService;
import pl.sci.cashflowbackend.user.UserService;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExpensesService {
    private UserService userService;
    private CategoryService categoryService;
    private ExpensesRepository expensesRepository;
    private ShopsService shopsService;

    public ExpensesService(UserService userService,
                           CategoryService categoryService,
                           ExpensesRepository expensesRepository,
                           ShopsService shopsService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.expensesRepository = expensesRepository;
        this.shopsService = shopsService;
    }

    boolean addExpenses(String username, ArrayList<ExpensesDto> listDto){
        String userId = userService.findUserIdByUsername(username);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        AtomicBoolean conflict = new AtomicBoolean(false);
        ArrayList<Expenses> listExpenses = new ArrayList<>();

        listDto.forEach(element -> {
            String categoryId = categoryService.findCategoryIdByCategoryName(element.getCategories());
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

    public void uploadImage(MultipartFile file, String username) throws IOException {

        // GET RECEIPT LINES
        ArrayList<String> lines = doOCR(file);
        // GET SHOP
        String shop = checkShop(lines);


        if(shop.equals("lidl")){
            lidl(lines);
        }else if(shop.equals("auchan")){
            System.out.println("auchan");
        }else{
            System.out.println("Return Error");
        }
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
                if(lines.get(i).toLowerCase().contains(shops.get(j))){
                    return shops.get(j);
                }
            }
        }

        return null;
    }

    public void lidl(ArrayList<String> lines){

        // GET DATE FROM RECEIPT
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        LocalDate date = null;

        for(int i = 0; i < lines.size(); i++){
            Matcher matcher = pattern.matcher(lines.get(i));
            if(matcher.find()){
                date = LocalDate.parse(lines.get(i));
            }
        }

        // GET PRODUCTS
        lines.forEach( line -> {
            if (line.contains("*")) {
                System.out.println(line);
                //TODO: cena i nazwa
            }
        });
    }
}