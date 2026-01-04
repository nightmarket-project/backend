package store.nightmarket.application.appitem.out.preempt;

import java.util.List;

import store.nightmarket.domain.item.model.PreemptedProductVariant;

public interface SavePreemptedProductVariantPort {

	void save(PreemptedProductVariant preemptedProductVariant);

	void saveAll(List<PreemptedProductVariant> preemptedProductVariantList);

}
