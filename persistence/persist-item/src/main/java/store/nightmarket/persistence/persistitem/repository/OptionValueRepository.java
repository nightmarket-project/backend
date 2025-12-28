package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.model.OptionValueEntity;

@Repository
public interface OptionValueRepository extends JpaRepository<OptionValueEntity, UUID> {

	List<OptionValueEntity> findByOptionGroupEntity(OptionGroupEntity optionGroupEntity);

}
