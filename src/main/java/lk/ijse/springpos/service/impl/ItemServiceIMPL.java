package lk.ijse.springpos.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.springpos.customObj.impl.ItemErrorResponse;
import lk.ijse.springpos.customObj.ItemResponse;
import lk.ijse.springpos.dto.impl.ItemDTO;
import lk.ijse.springpos.entity.impl.ItemEntity;
import lk.ijse.springpos.exception.DataPersistFailedException;
import lk.ijse.springpos.exception.ItemNotFoundException;
import lk.ijse.springpos.repository.ItemRepository;
import lk.ijse.springpos.service.ItemService;
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
public class ItemServiceIMPL implements ItemService {
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final Mapping mapping;


    @Override
    public void saveItem(ItemDTO itemDTO) {
        itemDTO.setCode(AppUtil.createItemId());
        //System.out.println(itemDTO);
        //System.out.println(mapping.convertToItemEntity(itemDTO));
        ItemEntity savedItem =
                itemRepository.save(mapping.convertToItemEntity(itemDTO));
        if(savedItem == null){
            throw new DataPersistFailedException("Item not saved");
        }
    }

    @Override
    public void updateItem(ItemDTO itemDTO) {
        Optional<ItemEntity> tmpItem = itemRepository.findById(itemDTO.getCode());
        if(!tmpItem.isPresent()) throw new ItemNotFoundException("Item not found");
        else {
            ItemEntity itemEntity = tmpItem.get();
            itemEntity.setName(itemDTO.getName());
            itemEntity.setPrice(itemDTO.getPrice());
            itemEntity.setQty(itemDTO.getQty());
        }
    }

    @Override
    public void deleteItem(String itemId) {
        Optional<ItemEntity> selectedItem = itemRepository.findById(itemId);
        if(!selectedItem.isPresent()) {
            throw new ItemNotFoundException("Item not found");
        }else itemRepository.deleteById(itemId);
    }

    @Override
    public ItemResponse getSelectedItem(String itemId) {
        return (itemRepository.existsById(itemId))
                ?mapping.convertToItemDTO(itemRepository.getItemEntityByCode(itemId))
                : new ItemErrorResponse(0, "Item not found");
    }

    @Override
    public List<ItemDTO> getAllItems() {
        return mapping.convertItemToDTOList(itemRepository.findAll());
    }
}
