package com.kakao.shopping.service;

import com.kakao.shopping._core.errors.exception.BadRequestException;
import com.kakao.shopping._core.errors.exception.PermissionDeniedException;
import com.kakao.shopping.domain.ProductOption;
import com.kakao.shopping.domain.Product;
import com.kakao.shopping.domain.UserAccount;
import com.kakao.shopping.dto.product.ProductDTO;
import com.kakao.shopping.dto.product.ProductListItemDTO;
import com.kakao.shopping.dto.product.ProductRequest;
import com.kakao.shopping.dto.product.option.OptionRequest;
import com.kakao.shopping.dto.product.option.ProductOptionDTO;
import com.kakao.shopping.dto.product.request.OptionStockUpdateRequest;
import com.kakao.shopping.dto.product.request.ProductUpdateRequest;
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
        List<ProductOptionDTO> options = productOptions
                .stream()
                .map(productOption -> new ProductOptionDTO(productOption.getId(), productOption.getName(), productOption.getPrice(), productOption.getStock()))
                .toList();
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

    public void saveProduct(UserAccount userAccount, ProductRequest.Insert request) {
        productRepository.save(
                Product.of(
                        request.name(),
                        request.description(),
                        request.image(),
                        request.price(),
                        userAccount
                )
        );
    }

    public List<Product> saveProducts(UserAccount userAccount, List<ProductRequest.Insert> requests) {
        return productRepository.saveAll(
                requests
                        .stream()
                        .map(request -> Product.of(
                                request.name(),
                                request.description(),
                                request.image(),
                                request.price(),
                                userAccount
                        ))
                        .toList()
        );
    }

    public ProductOption saveOption(UserAccount userAccount, OptionRequest.Insert request) {
        return optionRepository.save(
                ProductOption.of(
                        request.product(),
                        request.name(),
                        request.price(),
                        userAccount
                )
        );
    }

    public List<ProductOption> saveOptions(UserAccount userAccount, List<OptionRequest.Insert> requests) {
        return optionRepository.saveAll(
                requests
                        .stream()
                        .map(request -> ProductOption.of(
                                request.product(),
                                request.name(),
                                request.price(),
                                userAccount
                        ))
                        .toList()
        );
    }

    public void updateStockById(UserAccount userAccount, OptionStockUpdateRequest request) {
        ProductOption option = optionRepository.findById(request.optionId()).orElseThrow(
                () -> new BadRequestException("존재하지 않는 옵션입니다.")
        );

        if (!option.getCreatedBy().equals(userAccount)) {
            throw new PermissionDeniedException("해당 상품에 대한 권한이 없습니다.");
        }

        option.updateStock(userAccount, request.stock());
        optionRepository.save(option);
    }

    public void updateProductById(UserAccount userAccount, Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new BadRequestException("존재하지 않는 상품입니다.")
        );

        if (!product.getCreatedBy().equals(userAccount)) {
            throw new PermissionDeniedException("해당 상품에 대한 권한이 없습니다.");
        }

        product
                .updateName(userAccount, request.name())
                .updateDescription(userAccount, request.description())
                .updateImage(userAccount, request.image())
                .updatePrice(userAccount, request.price());
        productRepository.save(product);
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
