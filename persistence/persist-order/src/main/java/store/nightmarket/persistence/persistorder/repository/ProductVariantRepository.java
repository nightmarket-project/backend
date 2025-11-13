package store.nightmarket.persistence.persistorder.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import store.nightmarket.persistence.persistorder.entity.model.ProductVariantEntity;

public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, UUID> {

	@Query("SELECT productVariantEntity FROM ProductVariantEntity productVariantEntity " +
		"WHERE productVariantEntity.id IN (:idList)")
	List<ProductVariantEntity> findByIdList(@Param("idList") List<UUID> idList);

}
