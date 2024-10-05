package lk.ijse.springpos.repository;

import lk.ijse.springpos.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity,String> {
    CustomerEntity getCustomerEntityByCustomerId(String customerId);
}
