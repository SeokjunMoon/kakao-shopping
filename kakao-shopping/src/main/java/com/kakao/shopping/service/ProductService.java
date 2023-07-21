package com.kakao.shopping.service;

import com.kakao.shopping.domain.Option;
import com.kakao.shopping.domain.Product;
import com.kakao.shopping.dto.product.ProductDTO;
import com.kakao.shopping.dto.product.ProductListItemDTO;
import com.kakao.shopping.dto.product.ProductRequest;
import com.kakao.shopping.repository.OptionRepository;
import com.kakao.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public List<ProductListItemDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductListItemDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImage(),
                        product.getPrice(),
                        product.getStarCount()
                ))
                .toList();
    }

    public ProductDTO findById(Long id) {
        List<Option> options = optionRepository.findAllByProductId(id);
        Product product = productRepository.findById(id).orElseThrow();
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getPrice(),
                product.getStarCount(),
                options
        );
    }

    public void save(ProductRequest.Insert request) {
        productRepository.save(
                Product.of(
                        request.name(),
                        request.price(),
                        request.description(),
                        request.image(),
                        0L
                )
        );
    }

    public void saveAll(List<ProductRequest.Insert> requests) {
        productRepository.saveAll(
                requests
                        .stream()
                        .map(request -> Product.of(
                                request.name(),
                                request.price(),
                                request.description(),
                                request.image(),
                                0L
                        ))
                        .toList()
        );
    }
}
