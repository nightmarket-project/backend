package store.nightmarket.application.appitem.out.post;

import java.util.List;

import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.state.ImageType;
import store.nightmarket.itemweb.model.id.ImageOwnerId;
import store.nightmarket.itemweb.model.id.ProductPostId;

public interface ReadImageManagerPort {

	List<ImageManager> readThumbnailList(List<ProductPostId> idList);

	List<ImageManager> readImageTypeList(ProductPostId id, List<ImageType> imageTypeList);

	List<ImageManager> readIdList(List<ImageOwnerId> idList);

}
