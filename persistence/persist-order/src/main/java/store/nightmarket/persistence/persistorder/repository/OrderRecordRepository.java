package store.nightmarket.persistence.persistorder.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import store.nightmarket.persistence.persistorder.entity.model.OrderRecordEntity;

public interface OrderRecordRepository extends JpaRepository<OrderRecordEntity, UUID> {
}
