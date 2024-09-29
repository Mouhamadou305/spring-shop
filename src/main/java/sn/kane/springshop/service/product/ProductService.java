package sn.kane.springshop.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.kane.springshop.exception.ProductNotFoundException;
import sn.kane.springshop.model.Category;
import sn.kane.springshop.model.Image;
import sn.kane.springshop.model.Product;
import sn.kane.springshop.repository.CategoryRepository;
import sn.kane.springshop.repository.ProductRepository;
import sn.kane.springshop.request.AddProductRequest;
import sn.kane.springshop.request.UpdateProductRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //build a constructor implementing the final properties like productRepository
public class ProductService implements  IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    //Replaced by @RequiredArgsConstructor
//    public ProductService(ProductRepository productRepository){
//        this.productRepository =productRepository;
//    }

    @Override
    public Product addProduct(AddProductRequest productRequest) {
        Category category = Optional.ofNullable(categoryRepository.findByName(productRequest.getCategory().getName())).orElseGet(
                () -> {
                    Category newCategory = new Category(productRequest.getCategory().getName());
                    return categoryRepository.save(newCategory);
                }
        );
        productRequest.setCategory(category);
        return productRepository.save(createProduct(productRequest));
    }

    private Product createProduct(AddProductRequest productRequest){
        return new Product(
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getInventory(),
                productRequest.getBrand(),
                productRequest.getCategory()
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () ->{throw new ProductNotFoundException("Product not found");});
    }

    @Override
    public Product updateProduct(UpdateProductRequest product, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, product))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest req){
        existingProduct.setName(req.getName());
        existingProduct.setBrand(req.getBrand());
        existingProduct.setDescription(req.getDescription());
        existingProduct.setPrice(req.getPrice());
        existingProduct.setInventory(req.getInventory());

        Category category = categoryRepository.findByName(req.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findProductByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
