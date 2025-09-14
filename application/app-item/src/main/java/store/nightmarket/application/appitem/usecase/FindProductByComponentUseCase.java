package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.FindProductByComponentUseCaseDto.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindProductByComponentUseCase implements BaseUseCase<Input, Output> {

	private final ReadProductPostPort readProductPostPort;
	private final ReadImageManagerPort readImageManagerPort;

	@Override
	public Output execute(Input input) {
		Pageable pageable = PageRequest.of(input.page(), 25);
		Page<ProductPostAdapterDto> dtoPage = readProductPostPort
			.findProductPostListByName(input.component(), pageable);

		List<ImageOwnerId> imageOwnerIdList = dtoPage.getContent().stream()
			.map(productPostAdapterDto -> new ImageOwnerId(
				productPostAdapterDto.getProductPost().getProductPostId().getId()))
			.toList();

		List<ImageManager> imageManagerList = readImageManagerPort.readIdList(imageOwnerIdList);

		return Output.builder()
			.dtoPage(dtoPage)
			.imageManagerList(imageManagerList)
			.build();
	}

}
