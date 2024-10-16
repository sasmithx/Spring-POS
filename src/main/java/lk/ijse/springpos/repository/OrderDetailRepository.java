package lk.ijse.springpos.repository;


import lk.ijse.springpos.embedded.OrderDetailPK;
import lk.ijse.springpos.entity.impl.OrderDetailEntity;
import lk.ijse.springpos.entity.impl.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, OrderDetailPK> {
    @Query("SELECT od FROM OrderDetailEntity od WHERE od.order=:order")
    List<OrderDetailEntity> getAllByOrder(@Param("order") OrderEntity order);
    boolean existsByItemCode(String itemId);
}
