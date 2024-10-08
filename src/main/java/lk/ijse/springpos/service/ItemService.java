package lk.ijse.springpos.service;


import lk.ijse.springpos.customObj.ItemResponse;
import lk.ijse.springpos.dto.impl.ItemDTO;

import java.util.List;

public interface ItemService {
    void saveItem(ItemDTO itemDTO);
    void updateItem(ItemDTO itemDTO);
    void deleteItem(String itemId);
    ItemResponse getSelectedItem(String itemId);
    List<ItemDTO> getAllItems();
}