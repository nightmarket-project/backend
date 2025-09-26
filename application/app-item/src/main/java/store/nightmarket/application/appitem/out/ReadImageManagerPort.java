package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.state.DomainImageType;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;
import store.nightmarket.itemweb.valueobject.ProductPostId;

public interface ReadImageManagerPort {

	List<ImageManager> readThumbnailList(List<ProductPostId> idList);

	List<ImageManager> readImageTypeList(ProductPostId id, List<DomainImageType> imageTypeList);

	List<ImageManager> readIdList(List<ImageOwnerId> idList);

}
