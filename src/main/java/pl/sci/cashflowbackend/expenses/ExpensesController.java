package pl.sci.cashflowbackend.expenses;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto;
import pl.sci.cashflowbackend.jwt.Jwt;

import java.util.ArrayList;

@RestController
public class ExpensesController {

    private Jwt jwt;
    private ExpensesService expensesService;

    public ExpensesController(Jwt jwt, ExpensesService expensesService) {
        this.expensesService = expensesService;
        this.jwt = jwt;
    }

    @PostMapping("/api/expenses/add")
    public ResponseEntity<?> addExpenses(@RequestHeader("Authorization") String token,
                                                @RequestBody ArrayList<ExpensesDto> dtoList){
        String bearer = token.substring(7);
        String username = jwt.extractUsername(bearer);

        if(expensesService.addExpenses(username, dtoList)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/api/expenses/upload-image")
    public ResponseEntity<?> uploadImage(@RequestHeader("Authorization") String token,
                                         @RequestParam("image") MultipartFile file) throws Exception {

        String ocrApi = "https://ocr.asprise.com/api/v1/receipt";

        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpPost post = new HttpPost(ocrApi);
            post.setEntity(MultipartEntityBuilder.create()
                    .addTextBody("api_key", "TEST")
                    .addTextBody("recognizer", "auto")
                    .addTextBody("ref_no", "ocr_java_123'")
                    .addPart("file",
                            new InputStreamBody(file.getInputStream(),
                            ContentType.DEFAULT_BINARY,
                            file.getOriginalFilename()))
                    .build());

            try (CloseableHttpResponse response = client.execute(post)){
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        }

        //TODO: Logika zapisywania cen kategori daty i odsyłania do frontu;
        //      Przeniesc do serwisu;

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}