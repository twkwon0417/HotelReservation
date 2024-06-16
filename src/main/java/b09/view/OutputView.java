package b09.view;

import b09.model.Coupon;
import b09.model.Member;
import b09.model.Reservation;
import b09.model.member.Rank;
import b09.model.room.Constants;
import b09.repository.CouponRepository;

import java.time.temporal.Temporal;
import java.util.List;

public class OutputView {
    public void printAvailableRooms(List<String> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("예약 가능한 객실이 존재하지 않습니다.");
        } else {
            System.out.println("-------------- 객실 목록 --------------");
            for (int i = 0; i < rooms.size(); i++) {
                System.out.print(rooms.get(i) + " ");
                // 10개의 객실을 출력한 후 다음 줄로 넘어가도록 합니다.
                if ((i + 1) % 10 == 0) {
                    System.out.println(); // 줄바꿈
                }
            }

            System.out.println();
            System.out.println("-------------------------------------");
        }
    }

    public double calculateTotalAmount(Reservation reservation, Integer roomType, Rank rank) {
        double STANDARD = 100000;
        double PREMIER = 150000;
        double SUITE = 200000;
        // 예약 내역이랑 토탈 금액 출력해 마지막 예약 완료때 띄어주는 문구 입니다. //
        double totalAmount = 0;
        double discountRate = 0.0; // 할인율 초기값 설정

        Temporal checkInDate = reservation.getCheckInDate();
        Temporal checkOutDate = reservation.getCheckOutDate();
        long numberOfNights = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        if(reservation.getReservedDate().peakSeasonCheck()){
            STANDARD = 130000;
            PREMIER = 195000;
            SUITE = 260000;
        }
        else if(reservation.getReservedDate().checkEventDay(reservation.getReservedDate().getThisYear())){
            STANDARD = 150000;
            PREMIER = 225000;
            SUITE = 300000;
        }
        if (roomType == 1) {
            totalAmount += STANDARD; // STANDARD
        } else if (roomType == 2) {
            totalAmount += PREMIER; // PREMIER
        } else if (roomType == 3) {
            totalAmount += SUITE; // SUITE
        }

        int numberOfPeople = reservation.getNumberOfPeople().getInt();
        if (numberOfPeople > Constants.DEFAULT_PER_ROOM) {
            int extraPeople = numberOfPeople - Constants.DEFAULT_PER_ROOM;
            totalAmount += extraPeople * Constants.EXTRA_FEE;
        }
        totalAmount *= numberOfNights;
        totalAmount += reservation.getAdditionalProduct().getCasino() * Constants.CASINO_PRICE;
        totalAmount += reservation.getAdditionalProduct().getSpa() * Constants.SPA_PRICE;

        // 할인율 적용
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



        return finalAmount;
    }

    public void printReceipt(Reservation reservation, Rank rank, Member member, Integer roomType, double finalAmount) {
        // 예약 내역이랑 토탈 금액 출력해 마지막 예약 완료때 띄어주는 문구 입니다. //
        System.out.println("--------------- 예약 내역 ---------------");
        System.out.println("전체 예약 비용은 " + finalAmount + "원 입니다.");
        System.out.println("현장에서 결제 부탁드립니다.");
        System.out.println("---------------------------------------");
    }

    public void printNoReservation() {  // 예약 조회를 했는데 아무 예약도 없을때 출력되는 무언가;
        System.out.println("예약된 객실이 존재하지 않습니다.");
    }

    public void printReservation(List<Reservation> reservations) { // 회원이 조회 하고 예약이 있으면 보여줄 화면
        System.out.println("--------------- 예약 목록 ---------------");
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            System.out.println((i + 1) + ". 객실 호수: " + reservation.getRoomNumber() + ", 예약 날짜: " + reservation.getReservedDate());
        }
        System.out.println("---------------------------------------");
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

    public void printAlreadyRestrictedRoom() {
        System.out.println("이미 제한된 객실입니다.");
    }
}