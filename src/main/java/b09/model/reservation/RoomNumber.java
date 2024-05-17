package b09.model.reservation;

import java.time.LocalDate;

public class RoomNumber {
    private int roomNumber;
    private ReservedDate reservedDate;


    public RoomNumber(int roomNumber) throws Exception{
        this.roomNumber = roomNumber;
    }

    public RoomNumber(String roomNum) {
        this.roomNumber = Integer.parseInt(roomNum);
    }

    private void validate() throws Exception {
        int floor = roomNumber / 100;
        int rooms = roomNumber % 100;

        if (floor < 1 || floor > 10 || rooms < 1 || rooms > 10) {
            throw new Exception("올바른 객실 번호가 아닙니다.");
        }
    }

    public String getNumber(){
        return String.valueOf(this.roomNumber);
    }

    public int ofInt() {
        return this.roomNumber;
    }

    @Override
    public String toString() {
        return String.format("%d", roomNumber); // 객실 번호를 문자열로 반환
    }
}