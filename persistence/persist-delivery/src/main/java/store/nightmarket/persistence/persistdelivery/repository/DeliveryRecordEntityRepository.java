package store.nightmarket.persistence.persistdelivery.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import store.nightmarket.persistence.persistdelivery.entity.model.DeliveryRecordEntity;

public interface DeliveryRecordEntityRepository extends JpaRepository<DeliveryRecordEntity, UUID> {
}
