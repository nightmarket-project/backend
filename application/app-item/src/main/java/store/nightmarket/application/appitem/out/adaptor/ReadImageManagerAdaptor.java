package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.mapper.ImageManagerMapper;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.persistence.persistitem.repository.ImageManagerRepository;

@Component
@RequiredArgsConstructor
public class ReadImageManagerAdaptor implements ReadImageManagerPort {

	private final ImageManagerRepository imageManagerRepository;

	@Override
	public List<ImageManager> read(UUID id) {
		return imageManagerRepository.findByImageOwnerModelEntity_Id(id).stream()
			.map(ImageManagerMapper::toDomain)
			.toList();
	}
	
}
