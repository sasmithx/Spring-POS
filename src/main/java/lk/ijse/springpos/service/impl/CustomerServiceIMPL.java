package lk.ijse.springpos.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.springpos.customObj.impl.CustomerErrorResponse;
import lk.ijse.springpos.customObj.CustomerResponse;
import lk.ijse.springpos.exception.CustomerNotFoundException;
import lk.ijse.springpos.repository.CustomerRepository;
import lk.ijse.springpos.dto.impl.CustomerDTO;
import lk.ijse.springpos.entity.impl.CustomerEntity;
import lk.ijse.springpos.exception.DataPersistFailedException;
import lk.ijse.springpos.service.CustomerService;
import lk.ijse.springpos.util.AppUtil;
import lk.ijse.springpos.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceIMPL implements CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final Mapping mapping;

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        customerDTO.setId(AppUtil.createCustomerId());
        CustomerEntity savedCustomer =
                customerRepository.save(mapping.convertToCustomerEntity(customerDTO));
        if(savedCustomer == null){
            throw new DataPersistFailedException("Customer not saved");
        }
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        Optional<CustomerEntity> tmpCustomer = customerRepository.findById(customerDTO.getId());
        if(!tmpCustomer.isPresent()) throw new CustomerNotFoundException("Customer not saved");
        else {
            CustomerEntity customerEntity = tmpCustomer.get();
            customerEntity.setName(customerDTO.getName());
            customerEntity.setAddress(customerDTO.getAddress());
            customerEntity.setContact(customerDTO.getContact());
        }
    }

    @Override
    public void deleteCustomer(String customerId) {
        Optional<CustomerEntity> selectedCustomer = customerRepository.findById(customerId);
        if(!selectedCustomer.isPresent()) {
            throw new CustomerNotFoundException("Customer not found");
        }else customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerResponse getSelectedCustomer(String customerId) {
        return (customerRepository.existsById(customerId))
                ? mapping.convertToCustomerDTO(customerRepository.getCustomerEntityById(customerId))
                : new CustomerErrorResponse(0,"Customer not found");
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return mapping.convertCustomerToDTOList(customerRepository.findAll());
    }
}
