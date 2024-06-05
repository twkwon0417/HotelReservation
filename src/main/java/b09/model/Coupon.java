package b09.model;

import java.time.LocalDate;
import java.util.Objects;

public class Coupon {
    private Long id;
    private Long memberId;
    private LocalDate startDate;
    private LocalDate expireDate;
    private String couponNumber;

    public Coupon(Long id, Long memberId, LocalDate startDate, LocalDate expireDate, String couponNumber) {
        this.id = id;
        this.memberId = memberId;
        this.startDate = startDate;
        this.expireDate = expireDate;
        this.couponNumber = couponNumber;
    }

    public Coupon() {
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public String getCouponNumber() {
        return couponNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public void setCouponNumber(String couponNumber) {
        this.couponNumber = couponNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coupon coupon = (Coupon) o;
        return Objects.equals(id, coupon.id) && Objects.equals(memberId, coupon.memberId)
                && Objects.equals(startDate, coupon.startDate) && Objects.equals(expireDate,
                coupon.expireDate) && Objects.equals(couponNumber, coupon.couponNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, startDate, expireDate, couponNumber);
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", startDate=" + startDate +
                ", expireDate=" + expireDate +
                ", couponNumber='" + couponNumber + '\'' +
                '}';
    }
}
