package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ReadImageManagerUseCaseDto.*;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.itemweb.model.ImageManager;

@Service
@RequiredArgsConstructor
public class ReadImageManagerUseCase implements BaseUseCase<Input, List<ImageManager>> {

	private final ReadImageManagerPort readImageManagerPort;

	@Override
	public List<ImageManager> execute(Input input) {
		return readImageManagerPort.readImageTypeList(
			input.id(),
			input.imageTypeList()
		);
	}

}
