package com.defense.inventory.service.impl;

import com.defense.inventory.dto.BarcodeResponseDto;
import com.defense.inventory.entity.Company;
import com.defense.inventory.entity.Product;
import com.defense.inventory.entity.SubProduct;
import com.defense.inventory.entity.Unit;
import com.defense.inventory.exception.ResourceNotFoundException;
import com.defense.inventory.repository.CompanyRepository;
import com.defense.inventory.repository.ProductRepository;
import com.defense.inventory.repository.SubProductRepository;
import com.defense.inventory.repository.UnitRepository;
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
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BarcodeServiceImpl implements BarcodeService {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 100;
    private final SubProductRepository subProductRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final UnitRepository unitRepository;

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

    @Override
    public BarcodeResponseDto getUnitDetailsByBarcode(String barcode) {

        SubProduct subProduct = subProductRepository.findByBarcode(barcode).orElseThrow(() -> new ResourceNotFoundException("No Sub-Product ", "id ", 404L));
        Product product = productRepository.findById(subProduct.getProduct().getId()).orElseThrow(() -> new ResourceNotFoundException("No Product ", "id ", 404L));
        product.setSubProductList(List.of(subProduct));
        Company company = companyRepository.findById(product.getCompany().getId()).orElseThrow(() -> new ResourceNotFoundException("No Company ", "id ", 404L));
        company.setProductList(List.of(product));
        Unit unit = unitRepository.findById(company.getUnit().getId()).orElseThrow(() -> new ResourceNotFoundException("No Unit ", "id ", 404L));

        BarcodeResponseDto barcodeResponseDto = new BarcodeResponseDto();
        barcodeResponseDto.setId(unit.getId());
        barcodeResponseDto.setName(unit.getName());
        barcodeResponseDto.setDescription(unit.getDescription());
        barcodeResponseDto.setCompanyList(List.of(company));

        return barcodeResponseDto;
    }


}
