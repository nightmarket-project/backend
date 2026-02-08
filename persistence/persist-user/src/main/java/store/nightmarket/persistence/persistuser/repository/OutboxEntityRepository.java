package store.nightmarket.persistence.persistuser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import store.nightmarket.persistence.persistuser.entity.model.OutboxEntity;

public interface OutboxEntityRepository extends JpaRepository<OutboxEntity, Long> {

	@Query("""
			SELECT outboxEntity
			FROM OutboxEntity outboxEntity
			WHERE outboxEntity.state IN ('READY', 'FAILED')
			ORDER BY outboxEntity.id
		""")
	List<OutboxEntity> findPublishTarget();

}
