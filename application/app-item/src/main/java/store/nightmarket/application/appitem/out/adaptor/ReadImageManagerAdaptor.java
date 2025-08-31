package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.mapper.ImageManagerMapper;
import store.nightmarket.application.appitem.mapper.ImageTypeMapper;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.state.DomainImageType;
import store.nightmarket.persistence.persistitem.entity.state.EntityImageType;
import store.nightmarket.persistence.persistitem.repository.ImageManagerRepository;

@Component
@RequiredArgsConstructor
public class ReadImageManagerAdaptor implements ReadImageManagerPort {

	private final ImageManagerRepository imageManagerRepository;

	@Override
	public List<ImageManager> readImageTypeList(UUID id, List<DomainImageType> imageTypeList) {
		List<EntityImageType> entityImageTypeList = imageTypeList.stream()
			.map(ImageTypeMapper::toEntity)
			.toList();

		return imageManagerRepository.findByImageOwnerModelEntityIdAndEntityImageTypeList(id, entityImageTypeList)
			.stream()
			.map(ImageManagerMapper::toDomain)
			.toList();
	}

	@Override
	public List<ImageManager> readIdList(List<UUID> idList) {
		return imageManagerRepository.findByImageOwnerModelEntityIdList(idList).stream()
			.map(ImageManagerMapper::toDomain)
			.toList();
	}

}
