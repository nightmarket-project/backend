package store.nightmarket.persistence.persistitem.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.nightmarket.domain.itemweb.model.state.SchedulePostState;
import store.nightmarket.persistence.persistitem.entity.model.SchedulePostEntity;

@Repository
public interface SchedulePostRepository extends JpaRepository<SchedulePostEntity, UUID> {

	@Query("""
			SELECT schedulePostEntity FROM SchedulePostEntity schedulePostEntity
			WHERE schedulePostEntity.state = :state
			AND schedulePostEntity.scheduledAt > :from
			AND schedulePostEntity.scheduledAt <= :to
			ORDER BY schedulePostEntity.scheduledAt ASC
		""")
	List<SchedulePostEntity> findReadyChunk(
		@Param("state") SchedulePostState state,
		@Param("from") LocalDateTime from,
		@Param("to") LocalDateTime to,
		Pageable pageable
	);

	@Query("""
			SELECT schedulePostEntity FROM SchedulePostEntity schedulePostEntity
			WHERE schedulePostEntity.state = :state
			AND schedulePostEntity.scheduledAt > :from
			AND schedulePostEntity.scheduledAt <= :to
			ORDER BY schedulePostEntity.scheduledAt ASC
		""")
	List<SchedulePostEntity> findAllByStateAndScheduledAtBetween(
		@Param("state") SchedulePostState state,
		@Param("from") LocalDateTime from,
		@Param("to") LocalDateTime to
	);

}
