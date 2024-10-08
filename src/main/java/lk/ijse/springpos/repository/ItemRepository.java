package lk.ijse.springpos.repository;

import lk.ijse.springpos.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity,String> {
    ItemEntity getItemEntityByCode(String code);
}
