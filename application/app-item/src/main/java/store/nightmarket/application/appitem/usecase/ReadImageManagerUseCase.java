package store.nightmarket.application.appitem.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.itemweb.model.ImageManager;

@Service
@RequiredArgsConstructor
public class ReadImageManagerUseCase implements BaseUseCase<UUID, List<ImageManager>> {

	private final ReadImageManagerPort readImageManagerPort;

	@Override
	public List<ImageManager> execute(UUID uuid) {
		return readImageManagerPort.read(uuid);
	}

}
