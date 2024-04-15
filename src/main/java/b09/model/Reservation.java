package b09.model;

import b09.model.reservation.AdditionalProduct;
import b09.model.reservation.NumberOfPeople;
import b09.model.reservation.ReservedDate;
import b09.model.reservation.RoomNumber;

public class Reservation {
    private Long id;
    private final Long memberId;
    private final RoomNumber roomNumber;
    private final ReservedDate reservedDate;
    private final NumberOfPeople numberOfPeople;
    private final AdditionalProduct additionalProduct;

    public Reservation(Long memberId, RoomNumber roomNumber, ReservedDate reservedDate, NumberOfPeople numberOfPeople,
                       AdditionalProduct additionalProduct) {
        this.memberId = memberId;
        this.roomNumber = roomNumber;
        this.reservedDate = reservedDate;
        this.numberOfPeople = numberOfPeople;
        this.additionalProduct = additionalProduct;
    }

    public int calculateTotalBeforeDiscount() {
        return 0;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public RoomNumber getRoomNumber() {
        return roomNumber;
    }

    public ReservedDate getReservedDate() {
        return reservedDate;
    }

    public NumberOfPeople getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdditionalProduct getAdditionalProduct() {
        return additionalProduct;
    }
}

