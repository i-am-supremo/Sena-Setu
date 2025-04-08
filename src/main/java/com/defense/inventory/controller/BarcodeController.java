package com.defense.inventory.controller;

import com.defense.inventory.service.BarcodeService;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Barcode Image Generator From the Barcode String",
            description = "This will return a Byte Array of Data having PNG image of the Barcode"
    )
    @GetMapping("/{barcodeString}")
    public ResponseEntity<byte[]> generateBarcode(@PathVariable String barcodeString) throws IOException, WriterException {
        log.info("Generating barcode for text: {}", barcodeString);

        byte[] barcodeImage = barcodeService.generateBarcodeImage(barcodeString);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/png");

        return new ResponseEntity<>(barcodeImage, headers, HttpStatus.OK);
    }
}
