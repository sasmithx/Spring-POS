package lk.ijse.springpos.controller;

import lk.ijse.springpos.customObj.CustomerResponse;
import lk.ijse.springpos.dto.impl.CustomerDTO;
import lk.ijse.springpos.exception.CustomerNotFoundException;
import lk.ijse.springpos.exception.DataPersistFailedException;
import lk.ijse.springpos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private final CustomerService customerService;

    //Save Customer
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCustomer(
        @RequestPart("name") String name,
        @RequestPart("address") String address,
        @RequestPart("contact") String contact) {
        try {
            //build the customer object
            CustomerDTO buildCustomerDTO = new CustomerDTO();
            buildCustomerDTO.setName(name);
            buildCustomerDTO.setAddress(address);
            buildCustomerDTO.setContact(contact);
            //send to the service layer
            customerService.saveCustomer(buildCustomerDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") String id) {
        try{
            customerService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CustomerNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResponse getSelectedCustomer(@PathVariable("id") String id) {
        return customerService.getSelectedCustomer(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCustomer(
            @PathVariable("id") String customerId,
            @RequestPart("updateName") String updateName,
            @RequestPart("updateAddress") String updateAddress,
            @RequestPart("updateContact") String updateContact
    ){
      try {
          CustomerDTO buildCustomerDTO = new CustomerDTO();
          buildCustomerDTO.setId(customerId);
          buildCustomerDTO.setName(updateName);
          buildCustomerDTO.setAddress(updateAddress);
          buildCustomerDTO.setContact(updateContact);
          customerService.updateCustomer(buildCustomerDTO);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }catch (CustomerNotFoundException e){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }catch (Exception e){
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
}
