package lk.ijse.springpos.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.springpos.customObj.impl.OrderErrorResponse;
import lk.ijse.springpos.customObj.OrderResponse;
import lk.ijse.springpos.dto.impl.ItemDTO;
import lk.ijse.springpos.dto.impl.OrderDTO;
import lk.ijse.springpos.embedded.OrderDetailPK;
import lk.ijse.springpos.entity.impl.CustomerEntity;
import lk.ijse.springpos.entity.impl.ItemEntity;
import lk.ijse.springpos.entity.impl.OrderDetailEntity;
import lk.ijse.springpos.entity.impl.OrderEntity;
import lk.ijse.springpos.exception.CustomerNotFoundException;
import lk.ijse.springpos.exception.ItemNotFoundException;
import lk.ijse.springpos.repository.CustomerRepository;
import lk.ijse.springpos.repository.ItemRepository;
import lk.ijse.springpos.repository.OrderDetailRepository;
import lk.ijse.springpos.repository.OrderRepository;
import lk.ijse.springpos.service.OrderService;
import lk.ijse.springpos.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceIMPL implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final Mapping mapping;

    @Override
    @Transactional
    public void saveOrder(OrderDTO orderDTO) {
        OrderEntity order = mapping.convertToEntity(orderDTO, OrderEntity.class);

        CustomerEntity customer = customerRepository.findById(orderDTO.getCustomerId()).orElse(null);
        if (customer == null) throw new CustomerNotFoundException("Customer not found with ID: " + orderDTO.getCustomerId());
        order.setCustomer(customer);
        // Save order
        orderRepository.save(order);

        for (ItemDTO itemDTO : orderDTO.getItems()) {
            // Save order detail
            orderDetailRepository.save(new OrderDetailEntity(
                    new OrderDetailPK(order.getId(),itemDTO.getCode()),
                    (itemDTO.getQty()),
                    mapping.convertToEntity(itemDTO,ItemEntity.class),
                    order
            ));
            // Update item quantity
            ItemEntity item = itemRepository.findById(itemDTO.getCode()).orElseThrow(() -> new ItemNotFoundException("Item not found " + itemDTO.getCode()));
            item.setQty(item.getQty() - (itemDTO.getQty()));
            itemRepository.save(item);
        }
    }

    @Override
    public OrderResponse getSelectedOrder(int id) {
        Optional<OrderEntity> byId = orderRepository.findById(id);
        if (byId.isEmpty()) return new OrderErrorResponse(0, "Order not found");
        OrderDTO orderDTO = mapping.convertToDTO(byId.get(), OrderDTO.class);
        // Get all order details of the selected order
        List<OrderDetailEntity> orderDetailsByOrder = orderDetailRepository.getAllByOrder(byId.get());
        List<ItemDTO> list = orderDetailsByOrder.stream().map(od -> new ItemDTO(
                od.getItem().getCode(),
                od.getItem().getName(),
                (od.getItem().getPrice()),
                (od.getQty())
        )).toList();
        orderDTO.setItems(list);
        return orderDTO;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> allOrders = orderRepository.findAll();
        return mapping.convertToDTOList(allOrders);
    }
}
