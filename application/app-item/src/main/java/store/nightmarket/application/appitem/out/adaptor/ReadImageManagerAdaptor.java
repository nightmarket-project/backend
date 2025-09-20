package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.mapper.ImageManagerMapper;
import store.nightmarket.application.appitem.mapper.ImageTypeMapper;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.state.DomainImageType;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.persistence.persistitem.entity.state.EntityImageType;
import store.nightmarket.persistence.persistitem.repository.ImageManagerRepository;

@Component
@RequiredArgsConstructor
public class ReadImageManagerAdaptor implements ReadImageManagerPort {

	private final ImageManagerRepository imageManagerRepository;

	@Override
	public List<ImageManager> readImageTypeList(ProductPostId productPostId, List<DomainImageType> imageTypeList) {
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
