package b09.model.reservation;

public class RoomNumber {
    private final int roomNumber;

    public RoomNumber(int roomNumber) throws Exception{
        this.roomNumber = roomNumber;
    }

    private void validate() throws Exception {
        int floor = roomNumber / 100;
        int rooms = roomNumber % 100;

        if (floor < 1 || floor > 10 || rooms < 1 || rooms > 10) {
            throw new Exception("올바른 객실 번호가 아닙니다.");
        }
    }

    public int ofInt() {
        return this.roomNumber;
    }
}
