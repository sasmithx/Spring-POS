package lk.ijse.springpos.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lk.ijse.springpos.embedded.OrderDetailPK;
import lk.ijse.springpos.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_detail")
public class OrderDetailEntity implements SuperEntity {
    @EmbeddedId
    private OrderDetailPK orderDetailPK;
    @Column(name = "order_quantity", nullable = false, length = 10)
    private int qty;
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "code", insertable = false, updatable = false)
    private ItemEntity item;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private OrderEntity order;
}
