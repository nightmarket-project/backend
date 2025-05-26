package store.nightmarket.common.out.persistence.jpa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import store.nightmarket.common.out.persistence.jpa.entity.delivery.model.DeliveryRecordEntity;

public interface DeliveryRecordEntityRepository extends JpaRepository<DeliveryRecordEntity, UUID> {
}
