package store.nightmarket.persistence.persistitem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistitem.entity.model.SchedulePostEntity;

@Repository
public interface SchedulePostRepository extends JpaRepository<SchedulePostEntity, UUID> {
}
