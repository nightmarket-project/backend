package store.nightmarket.application.appitem.out;

import store.nightmarket.domain.item.model.Preemption;

public interface SavePreemptionPort {

	void save(Preemption preemption);

}
