package sn.kane.springshop.request;

import lombok.Data;
import sn.kane.springshop.model.Category;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private int inventory;
    private String brand;

    private Category category;
}
