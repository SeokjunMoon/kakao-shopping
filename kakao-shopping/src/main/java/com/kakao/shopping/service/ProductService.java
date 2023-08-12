package com.kakao.shopping.service;

import com.kakao.shopping._core.errors.exception.BadRequestException;
import com.kakao.shopping._core.errors.exception.PermissionDeniedException;
import com.kakao.shopping.domain.ProductOption;
import com.kakao.shopping.domain.Product;
import com.kakao.shopping.domain.UserAccount;
import com.kakao.shopping.dto.product.ProductDTO;
import com.kakao.shopping.dto.product.ProductListItemDTO;
import com.kakao.shopping.dto.product.option.ProductOptionDTO;
import com.kakao.shopping.dto.product.option.request.OptionInsertRequest;
import com.kakao.shopping.dto.product.request.OptionStockUpdateRequest;
import com.kakao.shopping.dto.product.request.OptionUpdateRequest;
import com.kakao.shopping.dto.product.request.ProductInsertRequest;
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
                .map(ProductService::toDto)
                .toList();
    }

    public ProductDTO findProductById(Long id) {
        List<ProductOption> productOptions = getProductOptionsById(id);
        Product product = productOptions.get(0).getProduct();
        List<ProductOptionDTO> options = toDto(productOptions);
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getImage(), product.getPrice(), product.getStarCount(), options);
    }

    public void saveProduct(UserAccount userAccount, ProductInsertRequest request) {
        productRepository.save(Product.of(request, userAccount));
    }

    public List<Product> saveProducts(UserAccount userAccount, List<ProductInsertRequest> requests) {
        return productRepository.saveAll(
                requests.stream()
                        .map(request -> Product.of(request, userAccount))
                        .toList()
        );
    }

    public ProductOption saveOption(UserAccount userAccount, OptionInsertRequest request) {
        return optionRepository.save(ProductOption.of(request, userAccount));
    }

    public List<ProductOption> saveOptions(UserAccount userAccount, List<OptionInsertRequest> requests) {
        return optionRepository.saveAll(
                requests.stream()
                        .map(request -> ProductOption.of(request, userAccount))
                        .toList()
        );
    }

    public void updateStockById(UserAccount userAccount, OptionStockUpdateRequest request) {
        ProductOption option = getProductOptionById(request.optionId(), userAccount);
        option.updateStock(userAccount, request.stock());
        optionRepository.save(option);
    }

    public void updateProductById(UserAccount userAccount, Long id, ProductUpdateRequest request) {
        Product product = getProductById(userAccount, id);
        update(userAccount, request, product);
        productRepository.save(product);
    }

    public void updateOptionById(UserAccount userAccount, Long id, OptionUpdateRequest request) {
        ProductOption option = getProductOptionById(id, userAccount);
        update(userAccount, request, option);
        optionRepository.save(option);
    }

    // ------------------------------------------------------------------------------------------

    private static ProductListItemDTO toDto(Product product) {
        return new ProductListItemDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getPrice(),
                product.getStarCount()
        );
    }

    private List<ProductOption> getProductOptionsById(Long id) {
        if (id <= 0) {
            throw new BadRequestException("id는 음수가 될 수 없습니다.");
        }

        List<ProductOption> productOptions = optionRepository.findAllByProductId(id);

        if (productOptions.isEmpty()) {
            throw new BadRequestException("상품이 존재하지 않습니다.");
        }
        return productOptions;
    }

    private static List<ProductOptionDTO> toDto(List<ProductOption> productOptions) {
        return productOptions.stream()
                .map(productOption -> new ProductOptionDTO(productOption.getId(), productOption.getName(), productOption.getPrice(), productOption.getStock()))
                .toList();
    }

    private ProductOption getProductOptionById(Long request, UserAccount userAccount) {
        ProductOption option = optionRepository.findById(request)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 상품입니다."));

        if (!option.getCreatedBy().equals(userAccount)) {
            throw new PermissionDeniedException("해당 상품에 대한 권한이 없습니다.");
        }
        return option;
    }

    private Product getProductById(UserAccount userAccount, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 상품입니다."));

        if (!product.getCreatedBy().equals(userAccount)) {
            throw new PermissionDeniedException("해당 상품에 대한 권한이 없습니다.");
        }
        return product;
    }

    private static void update(UserAccount userAccount, ProductUpdateRequest request, Product product) {
        product.updateName(userAccount, request.name())
                .updateDescription(userAccount, request.description())
                .updateImage(userAccount, request.image())
                .updatePrice(userAccount, request.price());
    }

    private static void update(UserAccount userAccount, OptionUpdateRequest request, ProductOption option) {
        option.updateName(userAccount, request.name())
                .updatePrice(userAccount, request.price());
    }
}
