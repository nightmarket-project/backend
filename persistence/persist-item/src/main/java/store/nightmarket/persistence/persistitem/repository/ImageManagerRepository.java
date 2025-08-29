package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.ImageManagerEntity;
import store.nightmarket.persistence.persistitem.entity.state.EntityImageType;

@Repository
public interface ImageManagerRepository extends JpaRepository<ImageManagerEntity, UUID> {

	@Query("SELECT imageManagerEntity "
		+ "FROM ImageManagerEntity imageManagerEntity "
		+ "Where imageManagerEntity.imageOwnerModelEntity.id = :imageOwnerModelEntityId "
		+ "AND imageManagerEntity.entityImageType IN (:imageTypeList)")
	List<ImageManagerEntity> findByImageOwnerModelEntityIdAndEntityImageTypeList(
		UUID imageOwnerModelEntityId,
		List<EntityImageType> imageTypeList
	);

	@Query("SELECT imageManagerEntity "
		+ "FROM ImageManagerEntity imageManagerEntity "
		+ "WHERE imageManagerEntity.imageOwnerModelEntity.id IN (:imageOwnerModelEntityIdList)")
	List<ImageManagerEntity> findByImageOwnerModelEntityIdList(List<UUID> imageOwnerModelEntityIdList);

}
