package lk.ijse.springpos.controller;

import lk.ijse.springpos.customObj.ItemResponse;
import lk.ijse.springpos.dto.impl.ItemDTO;
import lk.ijse.springpos.exception.DataPersistFailedException;
import lk.ijse.springpos.exception.ItemNotFoundException;
import lk.ijse.springpos.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    @Autowired
    private final ItemService itemService;

    //Save Item
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveItem(
            @RequestPart("name") String name,
            @RequestPart("price") String price,
            @RequestPart("qty") String qty){
        try{
            //Build the item object
            ItemDTO buildItemDTO = new ItemDTO();
            buildItemDTO.setName(name);
            buildItemDTO.setPrice(Double.parseDouble(price));
            buildItemDTO.setQty(Integer.parseInt(qty));
            //send to the service layer
            itemService.saveItem(buildItemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") String id){
        try {
            itemService.deleteItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemResponse getSelectedItem(@PathVariable("id") String id){
        return itemService.getSelectedItem(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDTO>> getAllItems(){
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateItem(
            @PathVariable("id") String itemCode,
            @RequestPart("updateName") String updateName,
            @RequestPart("updatePrice") BigDecimal updatePrice,
            @RequestPart("updateQty") int updateQty
    ){
        try{
            ItemDTO buildItemDTO = new ItemDTO();
            buildItemDTO.setCode(itemCode);
            buildItemDTO.setName(updateName);
            buildItemDTO.setPrice(Double.parseDouble(updatePrice.toString()));
            buildItemDTO.setQty(updateQty);
            itemService.updateItem(buildItemDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
