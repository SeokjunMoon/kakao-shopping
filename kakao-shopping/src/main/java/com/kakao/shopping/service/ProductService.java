package com.kakao.shopping.service;

import com.kakao.shopping.domain.ProductOption;
import com.kakao.shopping.domain.Product;
import com.kakao.shopping.dto.product.ProductDTO;
import com.kakao.shopping.dto.product.ProductListItemDTO;
import com.kakao.shopping.dto.product.ProductRequest;
import com.kakao.shopping.dto.product.option.OptionDTO;
import com.kakao.shopping.dto.product.option.OptionRequest;
import com.kakao.shopping.repository.OptionRepository;
import com.kakao.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public List<ProductListItemDTO> findAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest)
                .getContent()
                .stream()
                .map(ProductService::toProductListItemDTO)
                .toList();
    }

    public ProductDTO findProductById(Long id) {
        List<ProductOption> productOptions = optionRepository.findAllByProductId(id);
        Product product = productOptions.get(0).getProduct();
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getPrice(),
                product.getStarCount(),
                productOptions
        );
    }

    public void saveProduct(ProductRequest.Insert request) {
        productRepository.save(
                Product.of(
                        request.name(),
                        request.description(),
                        request.image(),
                        request.price()
                )
        );
    }

    public List<Product> saveProducts(List<ProductRequest.Insert> requests) {
        return productRepository.saveAll(
                requests
                        .stream()
                        .map(request -> Product.of(
                                request.name(),
                                request.description(),
                                request.image(),
                                request.price()
                        ))
                        .toList()
        );
    }

    public List<OptionDTO> findAllByProductId(Long id) {
        List<ProductOption> productOptions = optionRepository.findAllByProductId(id);
        return productOptions
                .stream()
                .map(option -> {
                    Product product = option.getProduct();
                    ProductListItemDTO productDTO = toProductListItemDTO(product);
                    return new OptionDTO(productDTO, option.getName(), option.getPrice());
                })
                .toList();
    }

    public OptionDTO findAllOptionsByProductId(Long id) {
        ProductOption productOption = optionRepository.findById(id).orElseThrow();
        Product product = productOption.getProduct();
        ProductListItemDTO productDTO = toProductListItemDTO(product);
        return new OptionDTO(productDTO, productOption.getName(), productOption.getPrice());
    }

    public ProductOption saveOption(OptionRequest.Insert request) {
        return optionRepository.save(
                ProductOption.of(
                        request.product(),
                        request.name(),
                        request.price()
                )
        );
    }

    public List<ProductOption> saveOptions(List<OptionRequest.Insert> requests) {
        return optionRepository.saveAll(
                requests
                        .stream()
                        .map(request -> ProductOption.of(
                                request.product(),
                                request.name(),
                                request.price()
                        ))
                        .toList()
        );
    }

    private static ProductListItemDTO toProductListItemDTO(Product product) {
        return new ProductListItemDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getPrice(),
                product.getStarCount()
        );
    }
}
