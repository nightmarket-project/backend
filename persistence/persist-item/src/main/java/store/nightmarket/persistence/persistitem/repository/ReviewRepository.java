package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {

	@Query("SELECT reviewEntity FROM ReviewEntity reviewEntity " +
		"JOIN FETCH ReplyEntity " +
		"JOIN FETCH UserEntity " +
		"WHERE reviewEntity.productPostEntity.id = :productPostId")
	List<ReviewEntity> findByProductPostId(UUID productPostId);

}
