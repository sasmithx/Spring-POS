package lk.ijse.springpos.dto.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lk.ijse.springpos.customObj.CustomerResponse;
import lk.ijse.springpos.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO implements SuperDTO, CustomerResponse {
    @Id
    private String id;
    private String name;
    private String address;
    private String contact;
}
