package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.UUID;

import store.nightmarket.itemweb.model.ImageManager;

public interface ReadImageManagerPort {

	List<ImageManager> read(UUID id);

}
