package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.present.receipt.ReceiptCreateDto;
import work.szczepanskimichal.model.present.receipt.ReceiptDto;
import work.szczepanskimichal.model.present.receipt.ReceiptImageUpdateDto;
import work.szczepanskimichal.model.present.receipt.ReceiptUpdateDto;
import work.szczepanskimichal.service.ReceiptService;

import java.util.UUID;

@RestController
@RequestMapping("/v1/present/receipt")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping("")
    public ResponseEntity<ReceiptDto> createReceipt(@ModelAttribute ReceiptCreateDto receiptCreateDto) {
        var receiptDto = receiptService.createReceipt(receiptCreateDto);
        return ResponseEntity.ok(receiptDto);
    }

    @GetMapping("/{receiptId}")
    public ResponseEntity<ReceiptDto> getReceipt(@PathVariable UUID receiptId) {
        var receiptDto = receiptService.getReceipt(receiptId);
        return ResponseEntity.ok(receiptDto);
    }

    @PutMapping("")
    public ResponseEntity<ReceiptDto> updateReceipt(@ModelAttribute ReceiptUpdateDto receiptUpdateDto) {
        var receiptDto = receiptService.updateReceipt(receiptUpdateDto);
        return ResponseEntity.ok(receiptDto);
    }

    @PatchMapping("/{receiptId}")
    public ResponseEntity<ReceiptDto> updateReceiptImage(@ModelAttribute ReceiptImageUpdateDto receiptImageUpdateDto) {
        var receipt = receiptService.updateReceiptImage(receiptImageUpdateDto);
        return ResponseEntity.ok(receipt);
    }


    @DeleteMapping("/{receiptId}")
    public ResponseEntity<String> deleteReceipt(@PathVariable UUID receiptId) {
        receiptService.deleteReceipt(receiptId);
        return ResponseEntity.ok("successfully deleted receipt: " + receiptId);
    }

}
