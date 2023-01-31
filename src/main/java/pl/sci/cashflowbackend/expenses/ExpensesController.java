package pl.sci.cashflowbackend.expenses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import pl.sci.cashflowbackend.expenses.dto.ExpensesDto;
import pl.sci.cashflowbackend.jwt.Jwt;

import java.io.IOException;
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
                                         @RequestParam("image") MultipartFile file) throws IOException {

        System.out.println(token);
        System.out.println(file.getName());
        System.out.println(file.getSize());
        System.out.println(file.getContentType());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}



//    @PostMapping("/upload/image")
//    public ResponseEntity<ImageUploadResponse> uplaodImage(@RequestParam("image") MultipartFile file)
//            throws IOException {
//
//        imageRepository.save(Image.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .image(ImageUtility.compressImage(file.getBytes())).build());
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ImageUploadResponse("Image uploaded successfully: " +
//                        file.getOriginalFilename()));
//    }
