package b09.repository;

import b09.model.Coupon;
import b09.model.Reservation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CouponRepository {
    static List<Coupon> coupons;
    static Long sequence = 0L;

    public CouponRepository() {
        coupons = fileReader("couponInfo.txt");
    }

    public void registerCoupon(Coupon coupon) {
        coupon.setId(++sequence);
        coupons.add(coupon);
        updateFile();
    }
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

    public void deleteCoupon(Long couponId) {
        Coupon toBeDeleted = null;
        for(Coupon coupon : coupons) {
            if(Objects.equals(coupon.getId(), couponId)) {
                toBeDeleted = coupon;
            }
        }
        coupons.remove(toBeDeleted);
        updateFile();
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
                if(couponId > sequence) {
                    sequence = couponId;
                }
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
            System.exit(0);
        }
        return coupons;
    }

    public void updateFile() {
        File file = new File("couponInfo.txt");
        file.delete();
        try (BufferedWriter out = new BufferedWriter(new FileWriter("couponInfo.txt"))) {
            for (Coupon coupon : coupons) {
                out.write(
                        coupon.getId() + "\t" +
                                coupon.getMemberId() + "\t" +
                                parseLocalDate(coupon.getStartDate()) + "\t" +
                                parseLocalDate(coupon.getExpireDate()) + "\t" +
                                coupon.getCouponNumber());
                out.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일을 업데이트하는데 실패했습니다.");
        }
    }

    private String parseLocalDate(LocalDate localDate) {
        String localDateString = localDate.toString();
        return localDateString.substring(2, 4) + localDateString.substring(5, 7) + localDateString.substring(8, 10);
    }
}
