package lk.ijse.springpos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "item")
public class ItemEntity implements SuperEntity {
    @Id
    private String code;
    private String name;
    private BigDecimal price;
    private int qty;
    /*@ManyToOne
    @JoinColumn(name = "id",nullable = false)
    private CustomerEntity customer;*/
}
