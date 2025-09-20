package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.ShoppingBasketProductEntity;

@Repository
public interface ShoppingBasketProductRepository extends JpaRepository<ShoppingBasketProductEntity, UUID> {

	@Query("SELECT shoppingBasketProduct " +
		"FROM ShoppingBasketProductEntity shoppingBasketProduct " +
		"WHERE shoppingBasketProduct.userEntity.id = :userId")
	List<ShoppingBasketProductEntity> findByUserId(@Param("userId") UUID userId);

}
