package store.nightmarket.application.appitem.in.dto;

import lombok.Getter;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;

@Getter
public class LastLayerDto extends OptionLayerDto {

	private final ProductVariantId productVariantId;

	public LastLayerDto(
		OptionValueId optionValueId,
		Name optionValueName,
		ProductVariantId productVariantId
	) {
		super(
			optionValueId,
			optionValueName
		);
		this.productVariantId = productVariantId;
	}
}
