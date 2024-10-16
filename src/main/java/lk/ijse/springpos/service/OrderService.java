package lk.ijse.springpos.service;


import lk.ijse.springpos.customObj.OrderResponse;
import lk.ijse.springpos.dto.impl.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    void saveOrder(OrderDTO orderDTO);
    OrderResponse getSelectedOrder(int id);
    List<OrderDTO> getAllOrders();
}
