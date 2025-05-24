package store.nightmarket.domain.delivery.valueobject;

import lombok.Getter;

@Getter
public class Location {

	private String name;

	public Location(String name) {
		this.name = name;
	}

}
