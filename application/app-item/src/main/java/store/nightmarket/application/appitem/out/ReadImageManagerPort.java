package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.domain.itemweb.model.ImageManager;
import store.nightmarket.domain.itemweb.model.id.ImageOwnerId;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.ImageType;

public interface ReadImageManagerPort {

	List<ImageManager> readThumbnailList(List<ProductPostId> idList);

	List<ImageManager> readImageTypeList(ProductPostId id, List<ImageType> imageTypeList);

	List<ImageManager> readIdList(List<ImageOwnerId> idList);

}
