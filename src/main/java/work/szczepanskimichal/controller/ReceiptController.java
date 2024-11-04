package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.szczepanskimichal.service.ReceiptService;

@RestController
@RequestMapping("/v1/present/receipt")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping("")
    public ResponseEntity<String> createReceipt(@RequestParam("file") MultipartFile file) {
        var receipt = receiptService.createReceipt(file);
        return ResponseEntity.ok("File uploaded successfully: " + receipt);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<String> getReceipt(@PathVariable String fileName) {
        var imageAwsLink = receiptService.getReceipt(fileName);
        return ResponseEntity.ok(imageAwsLink);

    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<String> deleteReceipt(@PathVariable String fileName) {
        receiptService.deleteReceipt(fileName);
        return ResponseEntity.ok("successfully deleted Receipt: " + fileName);
    }

}
