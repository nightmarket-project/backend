package store.nightmarket.persistence.persistitem.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;

@Repository
public interface ProductPostRepository extends JpaRepository<ProductPostEntity, UUID> {

	@Query("SELECT productPostEntity FROM ProductPostEntity productPostEntity " +
		"JOIN FETCH ProductEntity " +
		"WHERE ProductEntity.nameEntity.name LIKE %:keyword%")
	Page<ProductPostEntity> findByKeywordContaining(@Param("keyword") String keyword, Pageable pageable);

  @Query("SELECT productPostEntity FROM ProductPostEntity productPostEntity " +
		"JOIN FETCH ProductEntity " +
		"WHERE productPostEntity.id = :postId")
	Optional<ProductPostEntity> findByPostId(@Param("postId") UUID postId);

}
