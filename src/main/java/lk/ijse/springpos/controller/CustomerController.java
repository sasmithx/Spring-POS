package lk.ijse.springpos.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lk.ijse.springpos.customObj.CustomerResponse;
import lk.ijse.springpos.dto.impl.CustomerDTO;
import lk.ijse.springpos.exception.CustomerNotFoundException;
import lk.ijse.springpos.exception.DataPersistFailedException;
import lk.ijse.springpos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    //Save Customer
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCustomer(
        @RequestPart("name") @Valid @NotBlank(message = "Name is required and cannot be null")
        @Size(min=3, max = 50, message = "Name must be less than 50 characters") String name,
        @RequestPart("address") @Valid @NotBlank(message = "Address is required and cannot be null")
        @Size(min = 4, max = 30, message = "Address must be between 4 and 30 characters") String address,
        @RequestPart("contact") @Valid @NotBlank(message = "Contact is required and cannot be null")
        @Size(min = 10, max = 10, message = "Contact mus be 10 characters") String contact) {
        logger.info("Received request to save customer: name={}, address={} ,contact={}",name,address,contact);
        try {
            //build the customer object
            CustomerDTO buildCustomerDTO = new CustomerDTO();
            buildCustomerDTO.setName(name);
            buildCustomerDTO.setAddress(address);
            buildCustomerDTO.setContact(contact);
            //send to the service layer
            customerService.saveCustomer(buildCustomerDTO);
            logger.info("Customer saved  successfully: name={}, address={} ,contact={}",name,address,contact);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            logger.error("Data persist failed for customer: name={}, address={} ,contact={}",name,address,contact);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("Error saving Customer: name={}, address={} ,contact={}",name,address,contact);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") String id) {
        logger.info("Received request to delete customer with ID: {}", id);
        try{
            customerService.deleteCustomer(id);
            logger.info("Customer deleted successfully with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CustomerNotFoundException e){
            logger.error("Customer not found with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Error deleting customer with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResponse getSelectedCustomer(@PathVariable("id") String id) {
        logger.info("Received request to get customer with ID: {}", id);
        try {
            CustomerResponse customerResponse = customerService.getSelectedCustomer(id);
            logger.info("Successfully retrieved customer response with ID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(customerResponse).getBody();
        } catch (CustomerNotFoundException e){
            logger.error("Customer not found with ID: {}", id, e);
            return (CustomerResponse) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error retrieving customer with ID: {}", id, e);
            return (CustomerResponse) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        logger.info("Received request to get all customers");
        try {
            List<CustomerDTO> customers = customerService.getAllCustomers();
            logger.info("Successfully retrieved all customers");
            return ResponseEntity.status(HttpStatus.OK).body(customers);
        } catch (CustomerNotFoundException e) {
            logger.error("No customers found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error retrieving all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCustomer(
            @PathVariable("id") String customerId,
            @RequestPart("updateName") @Valid @NotBlank(message = "Name is required and cannot be null")
            @Size(min=3, max = 50, message = "Name must be less than 50 characters") String updateName,
            @RequestPart("updateAddress") @Valid @NotBlank(message = "Address is required and cannot be null")
            @Size(min = 4, max = 30, message = "Address must be between 4 and 30 characters") String updateAddress,
            @RequestPart("updateContact") @Valid @NotBlank(message = "Contact is required and cannot be null")
            @Size(min = 10, max = 10, message = "Contact mus be 10 characters") String updateContact
    ){
        logger.info("Received request to update customer with ID: {}", customerId);
      try {
          CustomerDTO buildCustomerDTO = new CustomerDTO();
          buildCustomerDTO.setId(customerId);
          buildCustomerDTO.setName(updateName);
          buildCustomerDTO.setAddress(updateAddress);
          buildCustomerDTO.setContact(updateContact);
          customerService.updateCustomer(buildCustomerDTO);
          logger.info("Customer updated successfully with ID: {}", customerId);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }catch (CustomerNotFoundException e){
          logger.error("Customer not found with ID: {}", customerId, e);
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }catch (Exception e){
          logger.error("Error updating customer with ID: {}", customerId, e);
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
}
