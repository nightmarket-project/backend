package store.nightmarket.persistence.persistpayment.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import store.nightmarket.persistence.persistpayment.entity.model.PaymentRecordEntity;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecordEntity, UUID> {
}
