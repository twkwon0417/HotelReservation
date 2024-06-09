package b09.repository;

import b09.model.Coupon;
import b09.model.Reservation;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CouponRepository {

//    public void registerCoupon(Coupon coupon) {
//        reservation.setId(++sequence);
//        reservations.add(reservation);
//        updateFile();
//    }
    public List<Coupon> getCouponOfUserId(Long userId) {
        List<Coupon> allCoupons = fileReader("couponInfo.txt");     //파일 읽어옴
        List<Coupon> userCoupons = new ArrayList<>();
        for (Coupon coupon : allCoupons) {
            if (coupon.getMemberId().equals(userId)) {      //Id 비교
                userCoupons.add(coupon);        //리스트 추가
            }
        }
        return userCoupons;     //사용자 쿠폰 리스트 반환
    }

    public List<Coupon> fileReader(String filename) {
        List<Coupon> coupons = new ArrayList<>();
        try (Scanner file = new Scanner(new File(filename))) {
            while (file.hasNextLine()) {
                String str = file.nextLine();
                String[] result = str.split("\t"); // 탭을 기준으로 분리
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                Coupon coupon = new Coupon();

                Long couponId = Long.parseLong(result[0]);
                coupon.setId(couponId);

                Long memberId = Long.parseLong(result[1]);
                coupon.setMemberId(memberId);

                LocalDate startDate = LocalDate.parse("20"+result[2], formatter);
                coupon.setStartDate(startDate);

                LocalDate expireDate = LocalDate.parse("20"+result[3], formatter);
                coupon.setExpireDate(expireDate);

                String couponNumber = result[4];
                coupon.setCouponNumber(couponNumber);

                coupons.add(coupon);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("쿠폰 파일관련 에러가 있습니다.");
//            System.exit(0);
        }
        return coupons;
    }
}
