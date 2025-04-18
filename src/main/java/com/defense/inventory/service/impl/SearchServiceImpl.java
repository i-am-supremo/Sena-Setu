package com.defense.inventory.service.impl;

import com.defense.inventory.dto.SearchResponseDto;
import com.defense.inventory.entity.Company;
import com.defense.inventory.entity.Product;
import com.defense.inventory.entity.SubProduct;
import com.defense.inventory.entity.Unit;
import com.defense.inventory.entity.enums.Type;
import com.defense.inventory.repository.CompanyRepository;
import com.defense.inventory.repository.ProductRepository;
import com.defense.inventory.repository.SubProductRepository;
import com.defense.inventory.repository.UnitRepository;
import com.defense.inventory.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final UnitRepository unitRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;
    private final SubProductRepository subProductRepository;

    @Override
    public List<SearchResponseDto> search(String name) {

        List<SearchResponseDto> searchResponseDtos = new ArrayList<>();
        List<Unit> units = unitRepository.findByNameContainingIgnoreCase(name);
        List<Company> companies = companyRepository.findByNameContainingIgnoreCase(name);
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        List<SubProduct> subProducts = subProductRepository.findByNameContainingIgnoreCase(name);
        List<SubProduct> subProductsList = subProductRepository.findByBarcodeContainingIgnoreCase(name);


        for (Unit unit : units) {
            searchResponseDtos.add(mapUnitToSearchResponseDto(unit));
        }
        for (Company company : companies) {
            searchResponseDtos.add(mapCompanyToSearchResponseDto(company));
        }
        for (Product product : products) {
            searchResponseDtos.add(mapProductToSearchResponseDto(product));
        }
        for (SubProduct subProduct : subProducts) {
            searchResponseDtos.add(mapSubProductToSearchResponseDto(subProduct));
        }
        for (SubProduct subProduct : subProductsList) {
            searchResponseDtos.add(mapSubProductToSearchResponseDto(subProduct));
        }

        return searchResponseDtos;
    }

    private SearchResponseDto mapUnitToSearchResponseDto(Unit unit) {
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        searchResponseDto.setId(unit.getId());
        searchResponseDto.setName(unit.getName());
        searchResponseDto.setType(Type.UNIT);
        return searchResponseDto;
    }

    private SearchResponseDto mapCompanyToSearchResponseDto(Company company) {
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        searchResponseDto.setId(company.getId());
        searchResponseDto.setName(company.getName());
        searchResponseDto.setType(Type.COMPANY);
        searchResponseDto.setUnitName(company.getUnit().getName());
        searchResponseDto.setUnitId(company.getUnit().getId());
        return searchResponseDto;
    }

    private SearchResponseDto mapProductToSearchResponseDto(Product product) {
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        searchResponseDto.setId(product.getId());
        searchResponseDto.setName(product.getName());
        searchResponseDto.setType(Type.PRODUCT);
        searchResponseDto.setCompanyName(product.getCompany().getName());
        searchResponseDto.setCompanyId(product.getCompany().getId());
        searchResponseDto.setUnitName(product.getCompany().getUnit().getName());
        searchResponseDto.setUnitId(product.getCompany().getUnit().getId());
        return searchResponseDto;
    }

    private SearchResponseDto mapSubProductToSearchResponseDto(SubProduct subProduct) {
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        searchResponseDto.setId(subProduct.getId());
        searchResponseDto.setName(subProduct.getName());
        searchResponseDto.setType(Type.SUBPRODUCT);
        searchResponseDto.setQuantity(subProduct.getQuantity());
        searchResponseDto.setBarcode(subProduct.getBarcode());
        searchResponseDto.setProductName(subProduct.getProduct().getName());
        searchResponseDto.setProductId(subProduct.getProduct().getId());
        searchResponseDto.setCompanyName(subProduct.getProduct().getCompany().getName());
        searchResponseDto.setCompanyId(subProduct.getProduct().getCompany().getId());
        searchResponseDto.setUnitName(subProduct.getProduct().getCompany().getUnit().getName());
        searchResponseDto.setUnitId(subProduct.getProduct().getCompany().getUnit().getId());
        return searchResponseDto;
    }
}
