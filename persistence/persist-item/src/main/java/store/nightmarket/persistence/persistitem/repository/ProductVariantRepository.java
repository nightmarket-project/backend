package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, UUID> {

	@Query("SELECT productVariantEntity FROM ProductVariantEntity productVariantEntity "
		+ "JOIN FETCH VariantOptionValueEntity "
		+ "JOIN FETCH OptionGroupEntity "
		+ "JOIN FETCH OptionValueEntity "
		+ "WHERE productVariantEntity.productId = :productId")
	List<ProductVariantEntity> findByProductId(@Param("productId") UUID productId);

}
