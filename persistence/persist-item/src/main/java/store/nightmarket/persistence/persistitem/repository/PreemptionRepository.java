package store.nightmarket.persistence.persistitem.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import store.nightmarket.persistence.persistitem.entity.model.PreemptionEntity;

public interface PreemptionRepository extends JpaRepository<PreemptionEntity, Long> {

	@Query("SELECT coalesce(sum(p.quantity),0) FROM PreemptionEntity p" +
		" WHERE p.productVariantId = :productVariantId AND p.expiredAt > :now")
	Long getPreemptedQuantity(
		@Param("productVariantId") UUID productVariantId,
		@Param("now") LocalDateTime now
	);

}
