package b09.service;

public class CouponService {
    // TODO 얘네 받아서 쿠폰 파일에 등록시키는 함수가 있어야 함.
    public int nthTimeCheckCoupon(long reservationIndex){
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

    public boolean checkCoupon(double total){
        //TODO total이 500,000 이상인 경우에는 return true
        // else return false
        if(total >= 500000){
            return true;
        }
        return false;
    }
}
