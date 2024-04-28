package b09.model;

import b09.model.reservation.AdditionalProduct;
import b09.model.reservation.NumberOfPeople;
import b09.model.reservation.ReservedDate;
import b09.model.reservation.RoomNumber;
import java.time.LocalDate;

public class Reservation {
    private Long id;
    private Long memberId;
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
    public Reservation(long id, long memberId, RoomNumber roomNumber, ReservedDate reservedDate,
                       NumberOfPeople numberOfPeople, AdditionalProduct additionalProduct)
    {
        this.id = id;
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
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }
    public AdditionalProduct getAdditionalProduct() {
        return additionalProduct;
    }

    public LocalDate getCheckInDate() {
        return reservedDate.getStartDate();
    }
    public LocalDate getCheckOutDate() {
        return reservedDate.getEndDate();
    }
    public int getBreakfast() {
        return additionalProduct.getBreakfast();
    }

    public int getCasino() {
        return additionalProduct.getCasino();
    }

    public int getSpa() {
        return additionalProduct.getSpa();

    }
}

