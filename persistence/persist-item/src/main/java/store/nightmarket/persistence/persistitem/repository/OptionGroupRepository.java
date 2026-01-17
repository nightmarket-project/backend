package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroupEntity, UUID> {

	@Query("SELECT DISTINCT optionGroupEntity FROM OptionGroupEntity optionGroupEntity "
		+ "LEFT JOIN FETCH optionGroupEntity.optionValueEntityList optionValueEntityList "
		+ "WHERE optionGroupEntity.productId = :productId")
	List<OptionGroupEntity> findByProductId(@Param("productId") UUID productId);

}
