package com.defense.inventory.controller;

import com.defense.inventory.service.BarcodeService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/barcodes")
@RequiredArgsConstructor
public class BarcodeController {

    private final BarcodeService barcodeService;

    @GetMapping("/{text}")
    public ResponseEntity<byte[]> generateBarcode(@PathVariable String text) throws IOException, WriterException {
        log.info("Generating barcode for text: {}", text);

        byte[] barcodeImage = barcodeService.generateBarcodeImage(text);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/png");

        return new ResponseEntity<>(barcodeImage, headers, HttpStatus.OK);
    }
}
