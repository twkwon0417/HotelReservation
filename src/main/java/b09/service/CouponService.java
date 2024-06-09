package b09.service;

import b09.model.Coupon;
import b09.model.reservation.ReservedDate;
import b09.repository.CouponRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CouponService {

    private ReservedDate reservedDate;
//    private final CouponRepository couponRepository;
    public int nthTimeCheckCoupon(long reservationIndex){ // 호출 부분 작성해야 함
        // TODO 예약 index가 25,50,75일 경우에는 return 30
        //  예약 index가 1,100일 경우에는 return 50
        //  else return 0

        if(reservationIndex == 25 || reservationIndex == 50 || reservationIndex == 75) {
            return 30;
        } else if (reservationIndex == 1 || reservationIndex == 100) {
            return 50;
        }

        return 0;
    }

    public boolean checkCoupon(double total){ // 호출할 때 total amount 타입 int 맞는지 확인하기
        //TODO total이 500,000 이상인 경우에는 return true
        // else return false
        if(total >= 500000){
            return true;
        }
        return false;
    }

    public List<String> deleteCouponExpired(List<String> couponList) {
        // TODO couponList는 3.6 getCouponOfUserID에서 받아오기
        // plus3MonthsDate: 현재에서 3개월이 지난 날짜
        LocalDate plus3MonthsDate = reservedDate.getStartDate().plusMonths(3);
        List<String> validCoupons = new ArrayList<>();

        // yyMMdd 형식의 string을 파싱하려고 DateTimeFormatter 사용
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        for (String coupon : couponList) {
            String[] parts = coupon.split(" ");
            if (parts.length == 5) { // couponList가 5개 부분으로 나누어졌는지 확인
                try {
                    String dateString = parts[3]; // 유효기간이 txt파일에서 4번째 부분에 위치하므로
                    LocalDate couponDateExpired = LocalDate.parse(dateString, formatter);
                    if (!couponDateExpired.isBefore(plus3MonthsDate)) {
                        validCoupons.add(coupon);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return validCoupons;
    }

    public void printCoupon(List<String> couponList) {
        // TODO 쿠폰 발급일, 만료일, 할인율을 print
        int index = 1;
        for (String coupon : couponList) {
            String[] parts = coupon.split(" ");
            if (parts.length == 5) {
                String date = parts[2];
                String dateExpired = parts[3];
                String discountRate = parts[4];
                System.out.println(index + ". 발급일: " + date + ", 만료일: " + dateExpired + ", 할인율: " + discountRate + "%");
                index++;
            }
        }
    }

//    public void registerCoupon(Coupon coupon) {
//        couponRepository.registerCoupon(reservation);
//        Member memberToBeEdited = memberRepository.getMemberById(reservation.getMemberId());
//        memberToBeEdited.getReservations().add(Math.toIntExact(reservation.getId()));
//        Member newMember = new Member(memberToBeEdited.getPhoneNumber(),
//                memberToBeEdited.getTotalMoneySpent(),
//                memberToBeEdited.getReservations());
//        memberRepository.editMember(memberRepository.getMemberById(reservation.getMemberId()), newMember);
//    }
}
