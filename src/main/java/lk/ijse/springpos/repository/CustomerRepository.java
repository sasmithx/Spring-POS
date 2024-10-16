package lk.ijse.springpos.repository;

import lk.ijse.springpos.entity.impl.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity,String> {
    CustomerEntity getCustomerEntityById(String customerId);
}
