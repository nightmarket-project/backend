package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.VariantOptionValueEntity;

@Repository
public interface VariantOptionValueRepository extends JpaRepository<VariantOptionValueEntity, UUID> {

	@Query("SELECT DISTINCT variantOptionValueEntity.productVariantEntity.id " +
		"FROM VariantOptionValueEntity variantOptionValueEntity " +
		"WHERE variantOptionValueEntity.optionGroupEntity.id = :optionGroupId")
	List<UUID> findProductVariantIdsByOptionGroupId(@Param("optionGroupId") UUID optionGroupId);

	@Query("SELECT DISTINCT variantOptionValueEntity.productVariantEntity.id " +
		"FROM VariantOptionValueEntity variantOptionValueEntity " +
		"WHERE variantOptionValueEntity.optionValueEntity.id = :optionValueId")
	List<UUID> findProductVariantIdsByOptionValueId(@Param("optionValueId") UUID optionValueId);

	@Query("SELECT variantOptionValueEntity FROM VariantOptionValueEntity variantOptionValueEntity " +
		"WHERE variantOptionValueEntity.productVariantEntity.id = :productVariantId")
	List<VariantOptionValueEntity> findByIdProductVariantId(@Param("productVariantId") UUID productVariantId);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("DELETE FROM VariantOptionValueEntity variantOptionValueEntity " +
		"WHERE variantOptionValueEntity.optionGroupEntity.id = :optionGroupId")
	void deleteAllByOptionGroupId(@Param("optionGroupId") UUID optionGroupId);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("DELETE FROM VariantOptionValueEntity variantOptionValueEntity " +
		"WHERE variantOptionValueEntity.optionValueEntity.id = :optionValueId")
	void deleteAllByOptionValueId(@Param("optionValueId") UUID optionValueId);

}
