package b09.view;

import b09.model.Member;
import b09.model.Reservation;
import b09.model.Room;
import b09.model.member.Rank;
import java.util.List;

public class OutputView {
    public void printAvailableRooms(List<String> rooms) {     // 예약 가능한 방들의 리스트 줄테니까 이거 기반으로 예쁘게 출력해
        if (rooms == null || rooms.isEmpty()) {
            System.out.println("예약 가능한 객실이 존재하지 않습니다.");
            return;
        }

        System.out.println("-------------- 객실 목록 --------------");
        int count = 0;
        for (String room : rooms) {
            System.out.print(room + " ");
            count++;
            if (count == 10) {
                System.out.println();       //10개 출력 후 줄바꿈
                count = 0;
            }
        }
        if (count > 0) {
            System.out.println();
        }
        System.out.println("-------------------------------------");
    }

    public void printReceipt(Reservation reservation, Rank rank, Member member) {
        // member 파라미터로 추가함!! 확인해야 함
        // 예약 내역이랑 토탈 금액 출력해 마지막 예약 완료때 띄어주는 문구 입니다. //
        double totalAmount = member.getTotalMoneySpent();
        double discountRate = 0.0; // 할인율 초기값 설정

        //할인율 적용
        switch (rank) {
            case BRONZE:
                discountRate = 0.0;
                break;
            case SILVER:
                discountRate = 0.1;
                break;
            case GOLD:
                discountRate = 0.2;
                break;
            case PLATINUM:
                discountRate = 0.3;
                break;
            case VIP:
                discountRate = 0.4;
                break;
        }

        double discountAmount = totalAmount * discountRate; // 할인 금액 계산
        double finalAmount = totalAmount - discountAmount; // 최종 금액 계산

        System.out.println("--------------- 예약 내역 ---------------");
        System.out.println("예약자 ID: " + reservation.getMemberId());
        System.out.println("객실 번호: " + reservation.getRoomNumber());
        System.out.println("예약 날짜: " + reservation.getReservedDate());
        System.out.println("인원 수: " + reservation.getNumberOfPeople());
        System.out.println("추가 상품: " + reservation.getAdditionalProduct().toString());
        System.out.println("총 금액: " + totalAmount + "원");
        System.out.println("할인율: " + (int)(discountRate * 100) + "%");
        System.out.println("할인 금액: " + discountAmount + "원");
        System.out.println("결제 금액: " + finalAmount + "원");
        System.out.println("---------------------------------------");
    }

    public void printNoReservation() {  // 예약 조회를 했는데 아무 예약도 없을때 출력되는 무언가;
        System.out.println("예약된 객실이 존재하지 않습니다.");
    }

    public void printReservation(List<Reservation> reservations) { // 회원이 조회 하고 예약이 있으면 보여줄 화면
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("고객님의 예약 내역이 없습니다.");
            return;
        }

        System.out.println("--------------- 예약 내역 ---------------");
        int index = 1; // 예약 내역 번호 인덱스
        for (Reservation reservation : reservations) {
            System.out.printf("%d. 예약자 ID: %s, 객실 호수: %s, 예약 날짜: %s\n",
                    index++, reservation.getMemberId(), reservation.getRoomNumber(), reservation.getReservedDate());
        }

    }

    public void printNoMatchingRoomNumberOfDate() { // 관리자모드에서 객실을 찾는데 해당 날짜에 해당 번호의 객실이 없는 경우 나온는 화면
        System.out.println("해당 날짜에 일치하는 객실 번호가 없습니다.");
    }

    public void printRoomCanceled() {
        System.out.println("예약이 취소되었습니다.");
    }

    public void printRoomChanged() {
        System.out.println("교체가 완료되었습니다.");
    }

    public void printRoomRestricted() {
        System.out.println("객실이 제한되었습니다.");
    }

    public void printRoomDateExtended() {
        System.out.println("투숙 기간이 연장되었습니다.");
    }
}
