package store.nightmarket.persistence.persistoutbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import store.nightmarket.persistence.persistoutbox.model.OutboxEntity;

public interface OutboxEntityRepository extends JpaRepository<OutboxEntity, Long> {

	@Query("""
		    SELECT outboxEntity
		    FROM OutboxEntity outboxEntity
		    WHERE outboxEntity.state IN ('READY', 'FAILED')
		    AND outboxEntity.eventType IN :eventTypes
		    ORDER BY outboxEntity.id
		    LIMIT :limit
		""")
	List<OutboxEntity> findPublishTarget(int limit, List<String> eventTypes);

}
