package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.valueobject.ProductPostId;

public interface ReadImageManagerPort {

	List<ImageManager> readThumbnailList(List<ProductPostId> idList);

}
