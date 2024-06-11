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
    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

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


    public List<Coupon> deleteCouponExpired(List<Coupon> couponList) {
        List<Coupon> validCoupons = new ArrayList<>();

        for (Coupon coupon : couponList) {
            LocalDate couponDateExpired = coupon.getExpireDate();
            if (!couponDateExpired.isBefore(reservedDate.getTodaysDate())) {
                validCoupons.add(coupon);
            }
        }
        return validCoupons;
    }

    public void printCoupon(List<Coupon> couponList) {
        List<Coupon> validCoupons = deleteCouponExpired(couponList);

        // 쿠폰 발급일, 만료일, 할인율을 출력
        int index = 1;
        for (Coupon coupon : validCoupons) {
            LocalDate startDate = coupon.getStartDate();
            LocalDate expireDate = coupon.getExpireDate();
            String couponNumber = coupon.getCouponNumber();

            String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("yyMMdd"));
            String formattedExpireDate = expireDate.format(DateTimeFormatter.ofPattern("yyMMdd"));

            System.out.println(index + ". 발급일: " + formattedStartDate + ", 만료일: " + formattedExpireDate + ", 쿠폰번호: " + couponNumber);

            index++;
        }
    }


    public void registerCoupon(Coupon coupon) {
//        couponRepository.registerCoupon(reservation);
//        Member memberToBeEdited = memberRepository.getMemberById(reservation.getMemberId());
//        memberToBeEdited.getReservations().add(Math.toIntExact(reservation.getId()));
//        Member newMember = new Member(memberToBeEdited.getPhoneNumber(),
//                memberToBeEdited.getTotalMoneySpent(),
//                memberToBeEdited.getReservations());
//        memberRepository.editMember(memberRepository.getMemberById(reservation.getMemberId()), newMember);
    }
}
