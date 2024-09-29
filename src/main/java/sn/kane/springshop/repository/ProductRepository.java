package sn.kane.springshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.kane.springshop.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String category);
    List<Product> findProductByBrand(String brandName);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);
}
