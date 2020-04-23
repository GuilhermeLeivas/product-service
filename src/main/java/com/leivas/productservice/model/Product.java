package com.leivas.productservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Document("product")
@Getter
@Setter
public class Product {

    @Id
    private String id;

    @NotEmpty
    private String productName;

    @NotEmpty
    private String cod;

    @NotEmpty
    private BigDecimal price;

    @NotEmpty
    private LocalDate productEntryDate;

    private String description;

    private String storeId;

    private ProductCategory category;

    private Optional<String> photoPath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", cod='" + cod + '\'' +
                ", price=" + price +
                ", productEntryDate=" + productEntryDate +
                ", description='" + description + '\'' +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }
}
