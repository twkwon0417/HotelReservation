import b09.model.Coupon;
import b09.repository.CouponRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CouponRepositoryTest {
    CouponRepository couponRepository = new CouponRepository();

    @Test
    void readFileTest() {
        List<Coupon> coupons = couponRepository.fileReader("couponInfo.txt");

        for (Coupon coupon : coupons) {
            System.out.println(coupon);
        }
    }

    @Test
    void registerTest() {
        couponRepository.registerCoupon(new Coupon(0L, 4L, LocalDate.of(2022, 12, 30),
                LocalDate.of(2023, 1, 1), "123"));
    }

    @Test
    void deleteTest() {
        couponRepository.deleteCoupon(3L);
    }
}
