package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.UUID;

import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.state.DomainImageType;

public interface ReadImageManagerPort {

	List<ImageManager> readImageTypeList(UUID id, List<DomainImageType> imageTypeList);

	List<ImageManager> readIdList(List<UUID> idList);

}
