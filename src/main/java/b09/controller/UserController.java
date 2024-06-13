package b09.controller;

import b09.model.Coupon;
import b09.model.Member;
import b09.model.Reservation;
import b09.model.reservation.AdditionalProduct;
import b09.model.reservation.NumberOfPeople;
import b09.model.reservation.ReservedDate;
import b09.model.reservation.RoomNumber;
import b09.repository.CouponRepository;
import b09.repository.MemberRepository;
import b09.repository.ReservationRepository;
import b09.service.CouponService;
import b09.service.ReservationService;
import b09.service.RoomService;
import b09.view.InputView;
import b09.view.OutputView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();
    RoomService roomService = new RoomService();
    CouponService couponService = new CouponService(new CouponRepository());
    ReservationService reservationService = new ReservationService(new ReservationRepository(), new MemberRepository());
    CouponRepository couponRepository = new CouponRepository();
    List<Coupon> available = new ArrayList<>();
    Scanner scan = new Scanner(System.in);


    public void initMain(Member member) {
        int userInput = inputView.inputUserPage();

        if(userInput == 1) {
            reservation(member);
            initMain(member);
        } else if (userInput == 2) {
            lookUpReservation(member);
            initMain(member);
        } else if (userInput == 3) {
            if(inputView.inputLogoutConfirm() == 2) {
                initMain(member);
            }
            return;
        }
    }

    private void reservation(Member member) {
        LocalDate todaysDate = inputView.inputTodaysDate();
        if(todaysDate == null) return;
        ReservedDate reservedDate = inputView.inputReservedDate(todaysDate);
        if(reservedDate == null) return;
        reservedDate.setTodaysDate(todaysDate);
        Integer roomType = inputView.inputRoomType(reservedDate);
        if(roomType == null) return;

        List<String> rooms = roomService.getRoomOfCondition(reservedDate, roomType);
        outputView.printAvailableRooms(rooms);
        RoomNumber roomNumber = inputView.inputSpecificRoomNumber(rooms);
        if(roomNumber == null) return;

        NumberOfPeople numberOfPeople = inputView.inputNumberOfPeople();
        if(numberOfPeople == null) return;

        AdditionalProduct additionalProduct = assembleAdditionalProduct(new AdditionalProduct(reservedDate), numberOfPeople);
        if(additionalProduct == null) return;

        Reservation reservation = new Reservation(member.getId(), roomNumber, reservedDate, numberOfPeople, additionalProduct);
        reservationService.registerReservation(reservation);    // reservation에 memberId있어서 member 따로 안넘겨줘도 됨

        double totalMoney = outputView.calculateTotalAmount(reservation, roomType, member.getRank()); // 등급할인 까지만 적용된 총 금액

        // 쿠폰 등록
        int checkNthTime = couponService.nthTimeCheckCoupon(reservation.getId());
        if(checkNthTime == 30 || checkNthTime == 50){
            couponService.registerCoupon(member.getId(), todaysDate, checkNthTime);
        }
        if(couponService.checkCoupon(totalMoney)){
            couponService.registerCoupon(member.getId(), todaysDate,30);
        }
        // 쿠폰 적용
        available = couponRepository.getCouponOfUserId(member.getId());
        if (!available.isEmpty()) {  // 쿠폰을 갖고 있으면
            couponService.printCoupon(available);
            int couponIndex = inputView.inputUseCoupon(available) - 1;
            if (couponIndex !=0) {
                Coupon selectedCoupon = available.get(couponIndex);
                int couponNum = Integer.parseInt(selectedCoupon.getCouponNumber());
                if(couponNum == 30)
                    totalMoney = totalMoney * 0.7;
                else if(couponNum == 50)
                    totalMoney = totalMoney * 0.5;
                else
                    totalMoney = totalMoney * 1;
            }
        }
        while(true) {
            int willYouPay = inputView.inputWillYouPay();
            if(willYouPay == 1) {
                outputView.printReceipt(reservation, member.getRank(), member, roomType, totalMoney);
                break;
            } else if (willYouPay == 2) {
                assembleAdditionalProduct(additionalProduct, numberOfPeople);
            } else {
                inputView.inputWillYouPay();
            }
        }
    }

    private AdditionalProduct assembleAdditionalProduct(AdditionalProduct additionalProduct, NumberOfPeople numberOfPeople) {

        additionalProduct.setNumberOfPeople(numberOfPeople.getInt());

        while(true) {
            int selectedInt = inputView.inputAdditionalProductMenu();

            if(selectedInt == 4) {
                System.out.println("예약을 마무리합니다.");
                break;
            } else if (selectedInt == 1) {
                inputView.inputCasino(additionalProduct);
            } else if (selectedInt == 2) {
                inputView.inputSpa(additionalProduct);
            } else if (selectedInt == 3) {
                inputView.inputBreakfast(additionalProduct);
            } else if (selectedInt == -1) { // -1은 q를 의미합니다. InputVIew의 Line 41 참고
                return null;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
        return additionalProduct;
    }

    private void lookUpReservation(Member member) {
        List<Reservation> reservations = reservationService.getMembersReservation(member);

        if(reservations.isEmpty()) {
            outputView.printNoReservation();
            return;
        }
        outputView.printReservation(reservations);
        int userInput = inputView.inputYesOrNo();
        if(userInput == 2) return;

        deleteReservation(reservations);
    }

    private void deleteReservation(List<Reservation> reservations) {
        System.out.println("취소할 객실의 방번호를 입력해주세요.");
        System.out.print("> ");
        String roomNumberInput = scan.nextLine();

        Reservation reservationToBeDeleted = null;
        for(Reservation checkroomnum : reservations) {
            if(checkroomnum.getRoomNumber().getNumber().equals(roomNumberInput)) {
                reservationToBeDeleted = checkroomnum;
                break;
            }
        }

        if (reservationToBeDeleted == null) {
            System.out.println("해당 방번호의 예약이 없습니다.");
            return;
        }

        reservations.remove(reservationToBeDeleted);
        reservationService.deleteReservation(reservationToBeDeleted);
        System.out.println("취소가 완료되었습니다.");
    }
}