package lk.ijse.springpos.util;

import lk.ijse.springpos.dto.impl.CustomerDTO;
import lk.ijse.springpos.entity.CustomerEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    //Customer matters mapping
    public CustomerEntity convertToCustomerEntity(CustomerDTO customerDTO) {return modelMapper.map(customerDTO, CustomerEntity.class);}
    public CustomerDTO convertToCustomerDTO(CustomerEntity customerEntity) {return modelMapper.map(customerEntity, CustomerDTO.class);}
    public List<CustomerDTO> convertCustomerToDTOList(List<CustomerEntity> customerEntities) {
        return modelMapper.map(customerEntities, new TypeToken<List<CustomerDTO>>() {}.getType());
    }
}
