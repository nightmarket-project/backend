package store.nightmarket.persistence.persistitem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.SchedulePostEntity;

@Repository
public interface SchedulePostRepository extends JpaRepository<SchedulePostEntity, UUID> {

	@Query("""
			SELECT schedulePostEntity FROM SchedulePostEntity schedulePostEntity
			WHERE schedulePostEntity.state = 'READY'
		""")
	List<SchedulePostEntity> findAllByReady();

}
