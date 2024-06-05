import b09.model.Coupon;
import b09.repository.CouponRepository;
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
}
