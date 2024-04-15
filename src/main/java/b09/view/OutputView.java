package b09.view;

import b09.model.Reservation;
import b09.model.Room;
import b09.model.member.Rank;
import java.util.List;

public class OutputView {
    public void printAvailableRooms(List<Room> rooms) {     // 예약 가능한 방들의 리스트 줄테니까 이거 기반으로 예쁘게 출력해

    }

    public void printReceipt(Reservation reservation, Rank rank) {    // 예약 내역이랑 토탈 금액 출력해 마지막 예약 완료때 띄어주는 문구 입니다. //
        // 여기서 할인 먹여주세요 ㅜ
    }

    public void printNoReservation() {  // 예약 조회를 했는데 아무 예약도 없을때 출력되는 무언가;

    }

    public void printReservation(List<Reservation> reservations) { // 회원이 조회 하고 예약이 있으면 보여줄 화면

    }

    public void printNoMatchingRoomNumberOfDate() { // 관리자모드에서 객실을 찾는데 해당 날짜에 해당 번호의 객실이 없는 경우 나온는 화면

    }

    public void printRoomCanceled() {

    }

    public void printRoomChanged() {

    }

    public void printRoomRestricted() {

    }

    public void printRoomDateExtended() {

    }
}
