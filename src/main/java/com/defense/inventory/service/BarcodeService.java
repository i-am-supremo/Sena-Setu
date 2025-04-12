package com.defense.inventory.service;

import com.defense.inventory.dto.BarcodeResponseDto;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface BarcodeService {
    byte[] generateBarcodeImage(String barcodeText) throws WriterException, IOException;

    BarcodeResponseDto getUnitDetailsByBarcode(String barcode);
}
