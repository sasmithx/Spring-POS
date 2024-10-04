package lk.ijse.springpos.service;

import jakarta.transaction.Transactional;
import lk.ijse.springpos.customObj.CustomerResponse;
import lk.ijse.springpos.dao.CustomerDAO;
import lk.ijse.springpos.dto.impl.CustomerDTO;
import lk.ijse.springpos.entity.CustomerEntity;
import lk.ijse.springpos.exception.DataPersistFailedException;
import lk.ijse.springpos.util.AppUtil;
import lk.ijse.springpos.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceIMPL implements CustomerService{
    @Autowired
    private final CustomerDAO customerDAO;
    @Autowired
    private final Mapping mapping;
    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        customerDTO.setId(AppUtil.createCustomerId());
        CustomerEntity savedCustomer =
                customerDAO.save(mapping.convertToCustomerEntity(customerDTO));
        if(savedCustomer == null){
            throw new DataPersistFailedException("Customer not saved");
        }

    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {

    }

    @Override
    public void deleteCustomer(String customerId) {

    }

    @Override
    public CustomerResponse getSelectedCustomer(String customerId) {
        return null;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return List.of();
    }
}
