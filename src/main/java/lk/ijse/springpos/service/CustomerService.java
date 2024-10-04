package lk.ijse.springpos.service;

import lk.ijse.springpos.customObj.CustomerResponse;
import lk.ijse.springpos.dto.impl.CustomerDTO;

import java.util.List;

public interface CustomerService {
    void saveCustomer(CustomerDTO customerDTO);
    void updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(String customerId);
    CustomerResponse getSelectedCustomer(String customerId);
    List<CustomerDTO> getAllCustomers();
}
