package lk.ijse.springpos.entity.impl;

import jakarta.persistence.*;
import lk.ijse.springpos.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "item")
public class ItemEntity implements SuperEntity {
    @Id
    private String code;
    private String name;
    private Double price;
    private int qty;
    /*@ManyToOne
    @JoinColumn(name = "id",nullable = false)
    private CustomerEntity customer;*/
}
