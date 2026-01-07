package store.nightmarket.application.appitem.out.post.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.post.ReadImageManagerPort;
import store.nightmarket.application.appitem.out.post.mapper.ImageManagerMapper;
import store.nightmarket.application.appitem.out.post.mapper.ImageTypeMapper;
import store.nightmarket.domain.itemweb.model.ImageManager;
import store.nightmarket.domain.itemweb.model.id.ImageOwnerId;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.ImageType;
import store.nightmarket.persistence.persistitem.entity.state.EntityImageType;
import store.nightmarket.persistence.persistitem.repository.ImageManagerRepository;

@Component
@RequiredArgsConstructor
public class ReadImageManagerAdapter implements ReadImageManagerPort {

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

	@Override
	public List<ImageManager> readImageTypeList(ProductPostId productPostId, List<ImageType> imageTypeList) {
		List<EntityImageType> entityImageTypeList = imageTypeList.stream()
			.map(ImageTypeMapper::toEntity)
			.toList();

		return imageManagerRepository.findByImageOwnerModelEntityIdAndEntityImageTypeList(
				productPostId.getId(), entityImageTypeList)
			.stream()
			.map(ImageManagerMapper::toDomain)
			.toList();
	}

	@Override
	public List<ImageManager> readIdList(List<ImageOwnerId> idList) {
		return imageManagerRepository.findByImageOwnerModelEntityIdList(
				idList.stream()
					.map(ImageOwnerId::getId)
					.toList()).stream()
			.map(ImageManagerMapper::toDomain)
			.toList();
	}

}
