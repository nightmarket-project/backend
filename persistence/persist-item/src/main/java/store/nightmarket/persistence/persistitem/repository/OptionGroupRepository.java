package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroupEntity, UUID> {

	@Query("SELECT optionGroupEntity FROM OptionGroupEntity optionGroupEntity "
		+ "JOIN FETCH optionGroupEntity.optionValueEntityList optionValueEntityList "
		+ "WHERE optionGroupEntity.id = :optionGroupId")
	Optional<OptionGroupEntity> findByIdFetchOptionValue(@Param("optionGroupId") UUID optionGroupId);

	@Query("SELECT optionGroupEntity FROM OptionGroupEntity optionGroupEntity "
		+ "JOIN FETCH optionGroupEntity.optionValueEntityList optionValueEntityList "
		+ "WHERE optionGroupEntity.productId = :productId")
	List<OptionGroupEntity> findByProductPostId(@Param("productId") UUID productId);

}
