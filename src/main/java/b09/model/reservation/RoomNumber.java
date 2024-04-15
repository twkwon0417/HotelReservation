package b09.model.reservation;

public class RoomNumber {
    private final int roomNumber;

    public RoomNumber(int roomNumber) throws Exception{
        this.roomNumber = roomNumber;
    }

    private void validate() throws Exception {  // Not a number || 211, 우리는 210호 까지 밖에 없는데... etc.... Validation on reserved room is held on Reservation Service

    }
}
