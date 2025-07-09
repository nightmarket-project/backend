package store.nightmarket.persistence.persistdelivery.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.nightmarket.persistence.persistdelivery.entity.model.DeliveryRecordEntity;

@Repository
public interface DeliveryRecordEntityRepository extends JpaRepository<DeliveryRecordEntity, UUID> {
}
