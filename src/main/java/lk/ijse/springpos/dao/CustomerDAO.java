package lk.ijse.springpos.dao;

import lk.ijse.springpos.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDAO extends JpaRepository<CustomerEntity,String> {
    CustomerEntity getCustomerEntityByCustomerId(String customerId);
}
