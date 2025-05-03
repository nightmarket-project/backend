package store.nightmarket.domain.delivery.valueobject;

public class Address {

    private String zipCode;
    private String roadAddress;
    private String detailAddress;

    public Address(String zipCode, String roadAddress, String detailAddress) {
        this.zipCode = zipCode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }

}
