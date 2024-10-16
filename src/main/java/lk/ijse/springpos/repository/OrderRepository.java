package lk.ijse.springpos.repository;


import lk.ijse.springpos.entity.impl.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    boolean existsById(int customerId);
}
