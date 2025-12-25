package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.domain.item.model.Preemption;

public interface SavePreemptionPort {

	void save(Preemption preemption);

	void saveAll(List<Preemption> preemptionList);

}
