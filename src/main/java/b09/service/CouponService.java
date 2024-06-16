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
    private CouponRepository couponRepository;

    public CouponService(ReservedDate reservedDate) {
        this.reservedDate = reservedDate;
    }
    public void setReservedDate(ReservedDate reservedDate) {
        this.reservedDate = reservedDate;
    }

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
        // 쿠폰 발급일, 만료일, 할인율을 출력
        int index = 1;
        for (Coupon coupon : couponList) {
            LocalDate startDate = coupon.getStartDate();
            LocalDate expireDate = coupon.getExpireDate();
            String couponNumber = coupon.getCouponNumber();

            String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("yyMMdd"));
            String formattedExpireDate = expireDate.format(DateTimeFormatter.ofPattern("yyMMdd"));

            System.out.println(index + ". 발급일: " + formattedStartDate + ", 만료일: " + formattedExpireDate + ", 쿠폰번호: " + couponNumber);

            index++;
        }
    }

    public void registerCoupon(Long memberId, LocalDate startDate, int couponNumber) {
        // TODO 쿠폰 객체 만들어서 넣어주기
        //  public Coupon(Long id, Long memberId, LocalDate startDate, LocalDate expireDate, String couponNumber)
        //   id는 걍 0으로 넣기(어차피 레포에 있는 함수가 setID 다시 해줌) => ok
        //   expireDate는 여기서 계산해서 넣기 => ok
        //   couponNumber 스트링으로 파싱해서 넣기 => ok
        LocalDate expireDate = startDate.plusMonths(2).minusDays(1);
        String strCouponNumber = String.valueOf(couponNumber);
        Coupon coupon = new Coupon(0L, memberId, startDate, expireDate, strCouponNumber);
        couponRepository.registerCoupon(coupon);
    }
}