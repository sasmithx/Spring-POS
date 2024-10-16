package lk.ijse.springpos.controller;

import lk.ijse.springpos.customObj.ItemResponse;
import lk.ijse.springpos.dto.impl.ItemDTO;
import lk.ijse.springpos.exception.DataPersistFailedException;
import lk.ijse.springpos.exception.ItemNotFoundException;
import lk.ijse.springpos.service.ItemService;
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
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ItemController {
    @Autowired
    private final ItemService itemService;
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
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
            logger.info("Item saved successfully");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            logger.error("Data persist failed", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            System.out.println(e.getMessage());
            logger.error("Error saving item", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") String id){
        logger.info("Received request to delete item with ID: {}", id);
        try {
            itemService.deleteItem(id);
            logger.info("Item deleted successfully with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            logger.error("Item not found with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Error deleting item with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemResponse getSelectedItem(@PathVariable("id") String id){
        logger.info("Received request to get item with ID: {}", id);
        try {
            ItemResponse itemResponse = itemService.getSelectedItem(id);
            logger.info("Successfully retrieved item response with ID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(itemResponse).getBody();
        } catch (ItemNotFoundException e) {
            logger.error("Item not found with ID: {}", id, e);
            return (ItemResponse) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error retrieving item with ID: {}", id, e);
            return (ItemResponse) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDTO>> getAllItems(){
        logger.info("Received request to get all items");
        try {
            List<ItemDTO> items = itemService.getAllItems();
            logger.info("Successfully retrieved all items");
            return ResponseEntity.status(HttpStatus.OK).body(items);
        } catch (ItemNotFoundException e) {
            logger.error("No items found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error retrieving all items", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateItem(
            @PathVariable("id") String itemCode,
            @RequestPart("updateName") String updateName,
            @RequestPart("updatePrice") String updatePrice,
            @RequestPart("updateQty") String updateQty
    ){
        logger.info("Received request to update item");
        try{
            ItemDTO buildItemDTO = new ItemDTO();
            buildItemDTO.setCode(itemCode);
            buildItemDTO.setName(updateName);
            buildItemDTO.setPrice(Double.parseDouble(updatePrice));
            buildItemDTO.setQty(Integer.parseInt(updateQty));
            itemService.updateItem(buildItemDTO);
            System.out.println("Updated Item");
            logger.info("Item updated successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            logger.error("Item not found", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Error updating item", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
