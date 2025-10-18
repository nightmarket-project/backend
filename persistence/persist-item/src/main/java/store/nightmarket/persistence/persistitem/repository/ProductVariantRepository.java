package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;
import store.nightmarket.persistence.persistitem.repository.dto.ProductVariantPostIdSummary;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, UUID> {

	@Query("SELECT productVariantEntity FROM ProductVariantEntity productVariantEntity " +
		"JOIN FETCH productVariantEntity.variantOptionValueEntityList variantOptionValueEntityList " +
		"JOIN FETCH variantOptionValueEntityList.optionGroupEntity optionGroupEntity " +
		"JOIN FETCH variantOptionValueEntityList.optionValueEntity optionValueEntity " +
		"WHERE productVariantEntity.productId = :productId")
	List<ProductVariantEntity> findByProductId(@Param("productId") UUID productId);

	@Query("SELECT new store.nightmarket.persistence.persistitem.repository.dto.ProductVariantPostIdSummary(" +
		"productPostEntity.id, productVariantEntity.id)  " +
		"FROM ProductVariantEntity productVariantEntity " +
		"JOIN ProductEntity productEntity ON productEntity.id = productVariantEntity.productId " +
		"JOIN ProductPostEntity productPostEntity ON productPostEntity.productEntity.id = productEntity.id " +
		"WHERE productVariantEntity.id IN :productVariantIds")
	List<ProductVariantPostIdSummary> findProductPostIdsByProductVariantIds(@Param("productVariantIds") List<UUID> productVariantIds);

	@Query("SELECT productVariantEntity FROM ProductVariantEntity productVariantEntity " +
		"WHERE productVariantEntity.id IN (:idList)")
	List<ProductVariantEntity> findByIdList(@Param("idList") List<UUID> idList);

}
