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
@Table(name = "customer")
public class CustomerEntity implements SuperEntity {
    @Id
    private String id;
    private String name;
    private String address;
    @Column(unique = true)
    private String contact;
   /* @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ItemEntity> items;*/
}
