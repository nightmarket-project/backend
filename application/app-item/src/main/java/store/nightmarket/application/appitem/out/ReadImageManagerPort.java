package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;

public interface ReadImageManagerPort {

	List<ImageManager> readIdList(List<ImageOwnerId> idList);

}
