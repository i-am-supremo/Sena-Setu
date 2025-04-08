package com.defense.inventory.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface BarcodeService {
    byte[] generateBarcodeImage(String barcodeText) throws WriterException, IOException;
}
