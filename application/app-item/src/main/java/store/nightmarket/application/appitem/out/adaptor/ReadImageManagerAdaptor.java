package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.mapper.ImageManagerMapper;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.persistence.persistitem.entity.repository.ImageManagerRepository;

@Component
@RequiredArgsConstructor
public class ReadImageManagerAdaptor implements ReadImageManagerPort {

	private final ImageManagerRepository imageManagerRepository;

	@Override
	public List<ImageManager> readThumbnailList(List<ProductPostId> idList) {
		return imageManagerRepository.findThumbnailImageListBy(
				idList.stream()
					.map(ImageOwnerId::getId)
					.toList()).stream()
			.map(ImageManagerMapper::toDomain)
			.toList();
	}

}
