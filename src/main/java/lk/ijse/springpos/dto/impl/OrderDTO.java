package lk.ijse.springpos.dto.impl;

import lk.ijse.springpos.customObj.OrderResponse;
import lk.ijse.springpos.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO implements SuperDTO, OrderResponse {
    private String id;
    private String date;
    private String customerId;
    private List<ItemDTO> items = new ArrayList<>();
    private String total;
    private String discount;
    private String subTotal;
    private String cash;
    private String balance;
}
