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

        while(true) {
            int willYouPay = inputView.inputWillYouPay();

            // 총금액을 계산한 값 (등급할인까지 적용된거임)
            // n번째 예약인지 확인
            // 맞으면 쿠폰 등록
            // 50만원 넘는지 확인
            // 맞으면 쿠폰 등록


            if(willYouPay == 1) {
                System.out.println(member.getId());
                Reservation reservation = new Reservation(member.getId(), roomNumber, reservedDate, numberOfPeople, additionalProduct);
                reservationService.registerReservation(reservation);    // reservation에 memberId있어서 member 따로 안넘겨줘도 됨

                //TODO ---------------------- 요기 사이에 채현 부분 호출
                int checkNthTime = couponService.nthTimeCheckCoupon(reservation.getId());
                if(checkNthTime == 30 || checkNthTime == 50){
                    couponService.registerCoupon(member.getId(), todaysDate, checkNthTime);
                }

                double totalMoney = outputView.calculateTotalAmount(reservation, roomType, member.getRank());
                if(couponService.checkCoupon(totalMoney)){
                    couponService.registerCoupon(member.getId(), todaysDate,30);
                }
                //TODO ---------------------- 요기 사이에 채현 부분 호출

                available = couponRepository.getCouponOfUserId(member.getId());
                if (!available.isEmpty()) {  // 쿠폰을 갖고 있으면
                    couponService.printCoupon(available);
                    int couponIndex = inputView.inputUseCoupon(available);
                    Coupon selectedCoupon = available.get(couponIndex);
                    int couponNum = Integer.parseInt(selectedCoupon.getCouponNumber());
                    if(couponNum == 30)
                        totalMoney = totalMoney * 0.7;
                    else if(couponNum == 50)
                        totalMoney = totalMoney * 0.5;
                    outputView.printReceipt(reservation, member.getRank(), member, roomType, totalMoney);
                } else {
                    outputView.printReceipt(reservation, member.getRank(), member, roomType, totalMoney);
                }


                // TODO 사용할 쿠폰 선택
                //  couponList는 3.6 getCouponOfUserID에서 받아오기


                // TODO 쿠폰사용 처리 함수(couponlist에서 사용쿠폰 삭제, 총 결제금액 수정 등등) 호출

                break;
            } else if (willYouPay == 2) {
                assembleAdditionalProduct(additionalProduct, numberOfPeople);
            } else {
                System.out.println("유효하지 않은 입력 값입니다.");
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