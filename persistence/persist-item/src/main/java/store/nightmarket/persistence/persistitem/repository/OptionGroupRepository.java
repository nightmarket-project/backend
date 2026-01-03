package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroupEntity, UUID> {

	@Query("SELECT optionGroupEntity FROM OptionGroupEntity optionGroupEntity "
		+ "JOIN FETCH optionGroupEntity.optionValueEntityList optionValueEntityList "
		+ "WHERE optionGroupEntity.productId = :productId")
	List<OptionGroupEntity> findByProductId(@Param("productId") UUID productId);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("DELETE FROM OptionGroupEntity optionGroupEntity " +
		"WHERE optionGroupEntity.id = :id")
	void deleteById(@Param("id") UUID id);

}
