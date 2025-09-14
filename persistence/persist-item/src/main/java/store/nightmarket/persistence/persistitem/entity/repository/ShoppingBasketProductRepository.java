package store.nightmarket.persistence.persistitem.entity.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.ShoppingBasketProductEntity;

@Repository
public interface ShoppingBasketProductRepository extends JpaRepository<ShoppingBasketProductEntity, UUID> {
}
