package store.nightmarket.persistence.persistorder.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistorder.entity.model.OrderRecordEntity;

@Repository
public interface OrderRecordRepository extends JpaRepository<OrderRecordEntity, UUID> {

	@Query("SELECT orderRecordEntity FROM OrderRecordEntity orderRecordEntity " +
		"JOIN FETCH orderRecordEntity.detailOrderRecordList detailOrderRecordList " +
		"WHERE OrderRecordEntity.id = :orderRecordId")
	Optional<OrderRecordEntity> findByOrderRecordId(@Param("orderRecordId") UUID orderRecordId);
}
