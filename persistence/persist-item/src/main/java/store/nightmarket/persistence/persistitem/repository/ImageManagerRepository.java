package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.ImageManagerEntity;

@Repository
public interface ImageManagerRepository extends JpaRepository<ImageManagerEntity, UUID> {

	@Query("SELECT imageManagerEntity " +
		"FROM ImageManagerEntity imageManagerEntity " +
		"WHERE imageManagerEntity.imageOwnerModelEntity.id IN :imageOwnerModelEntityIdList " +
		"AND imageManagerEntity.entityImageType = store.nightmarket.persistence.persistitem.entity.state.EntityImageType.THUMBNAIL")
	List<ImageManagerEntity> findThumbnailImageListBy(List<UUID> imageOwnerModelEntityIdList);

}
