package store.nightmarket.persistence.persistorder.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import store.nightmarket.persistence.persistorder.entity.model.ProductVariantEntity;

public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, UUID> {
}
