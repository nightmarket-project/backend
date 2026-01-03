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

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("DELETE FROM VariantOptionValueEntity variantOptionValueEntity " +
		"WHERE variantOptionValueEntity.optionGroupEntity.id = :optionGroupId")
	void deleteAllByOptionGroupId(@Param("optionGroupId") UUID optionGroupId);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("DELETE FROM VariantOptionValueEntity variantOptionValueEntity " +
		"WHERE variantOptionValueEntity.optionValueEntity.id = :optionValueIdList")
	void deleteByOptionValueId(@Param("optionValueId") UUID optionValueId);

}
