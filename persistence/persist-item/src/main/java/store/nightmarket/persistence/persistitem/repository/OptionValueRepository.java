package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.OptionValueEntity;

@Repository
public interface OptionValueRepository extends JpaRepository<OptionValueEntity, UUID> {

	@Query("SELECT optionValueEntity FROM OptionValueEntity optionValueEntity " +
		"JOIN FETCH optionValueEntity.optionGroupEntity optionGroupEntity " +
		"WHERE optionGroupEntity.id = :optionGroupId")
	List<OptionValueEntity> findByOptionGroupId(@Param("optionGroupId") UUID optionGroupId);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("DELETE FROM OptionValueEntity optionValueEntity " +
		"WHERE optionValueEntity.optionGroupEntity.id = :optionGroupId")
	void deleteByOptionGroupId(@Param("optionGroupId") UUID optionGroupId);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("DELETE FROM OptionValueEntity optionValueEntity " +
		"WHERE optionValueEntity.optionGroupEntity.id IN (:optionGroupIdList)")
	void deleteAllByOptionGroupId(@Param("optionGroupIdList") List<UUID> optionGroupIdList);

}
