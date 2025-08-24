package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.ImageManagerEntity;

@Repository
public interface ImageManagerRepository extends JpaRepository<ImageManagerEntity, UUID> {

	List<ImageManagerEntity> findByImageOwnerModelEntity_Id(UUID imageOwnerModelEntityId);

}
