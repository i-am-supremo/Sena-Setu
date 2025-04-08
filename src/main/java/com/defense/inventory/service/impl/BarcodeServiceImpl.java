package com.defense.inventory.service.impl;

import com.defense.inventory.service.BarcodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class BarcodeServiceImpl implements BarcodeService {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 100;

    @Override
    public byte[] generateBarcodeImage(String barcodeText) {
        if (barcodeText == null || barcodeText.trim().isEmpty()) {
            throw new IllegalArgumentException("Barcode text cannot be null or empty.");
        }

        try {
            // Generate barcode
            BitMatrix bitMatrix = new Code128Writer().encode(barcodeText, BarcodeFormat.CODE_128, WIDTH, HEIGHT);
            BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix, new MatrixToImageConfig());

            // Create new image with white background
            int textHeight = 30; // Space for text
            BufferedImage combinedImage = new BufferedImage(WIDTH, HEIGHT + textHeight, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = combinedImage.createGraphics();

            // Set white background
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, WIDTH, HEIGHT + textHeight);

            // Draw barcode
            g2d.drawImage(barcodeImage, 0, 0, null);

            // Set black color for text
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));

            // Center text below barcode
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(barcodeText);
            int x = (WIDTH - textWidth) / 2;
            int y = HEIGHT + textHeight - 5;

            g2d.drawString(barcodeText, x, y);
            g2d.dispose();

            // Convert image to byte array
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(combinedImage, "png", baos);
                return baos.toByteArray();
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid barcode input", e);
        } catch (IOException e) {
            throw new RuntimeException("Error generating barcode image", e);
        }
    }


}
