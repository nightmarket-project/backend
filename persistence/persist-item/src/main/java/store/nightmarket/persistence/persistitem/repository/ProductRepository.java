package store.nightmarket.persistence.persistitem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}
