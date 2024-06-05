package b09.service;

public class CouponService {
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

    public boolean checkCoupon(int total){ // 호출할 때 total amount 타입 int 맞는지 확인하기
        //TODO total이 500,000 이상인 경우에는 return true
        // else return false
        if(total >= 500000){
            return true;
        }
        return false;
    }
}
