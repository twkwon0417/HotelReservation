package b09.controller;

import b09.model.Member;
import b09.model.Reservation;
import b09.model.reservation.AdditionalProduct;
import b09.model.reservation.NumberOfPeople;
import b09.model.reservation.ReservedDate;
import b09.model.reservation.RoomNumber;
import b09.repository.MemberRepository;
import b09.repository.ReservationRepository;
import b09.service.ReservationService;
import b09.service.RoomService;
import b09.view.InputView;
import b09.view.OutputView;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserController {
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();
    RoomService roomService = new RoomService();
    ReservationService reservationService = new ReservationService(new ReservationRepository(), new MemberRepository());
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
        Integer roomType = inputView.inputRoomType();
        if(roomType == null) return;

        List<String> rooms = roomService.getRoomOfCondition(reservedDate, roomType);
        outputView.printAvailableRooms(rooms);
        RoomNumber roomNumber = inputView.inputSpecificRoomNumber(rooms);
        if(roomNumber == null) return;

        NumberOfPeople numberOfPeople = inputView.inputNumberOfPeople();

        AdditionalProduct additionalProduct = assembleAdditionalProduct(new AdditionalProduct(reservedDate), numberOfPeople);
        if(additionalProduct == null) return;

        while(true) {
            int willYouPay = inputView.inputWillYouPay();
            if(willYouPay == 1) {
                System.out.println(member.getId());
                Reservation reservation = new Reservation(member.getId(), roomNumber, reservedDate, numberOfPeople, additionalProduct);
                reservationService.registerReservation(reservation);    // reservation에 memberId있어서 member 따로 안넘겨줘도 됨
                outputView.printReceipt(reservation, member.getRank(), member, roomType);
                break;
            } else if (willYouPay == 2) {
                assembleAdditionalProduct(additionalProduct, numberOfPeople);
            } else {
                System.out.println("유효 x input");
            }
        }
    }

    private AdditionalProduct assembleAdditionalProduct(AdditionalProduct additionalProduct, NumberOfPeople numberOfPeople) {

        additionalProduct.setNumberOfPeople(numberOfPeople.getInt());

        while(true) {
            int selectedInt = inputView.inputAdditionalProductMenu();

            if(selectedInt == 4) {
                System.out.println("예약이 완료 (아직 틀)");
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
                System.out.println("잘못된 입력입ㄴ디ㅏ.(아직 틀)");
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
        System.out.print("취소할 객실 목록의 번호 입력");
        try {
            int idx = scan.nextInt() - 1;
            Reservation reservationToBeDeleted = reservations.get(idx);
            if(inputView.inputYesOrNo() == 2) return;
            reservationService.deleteReservation(reservationToBeDeleted);
        } catch (InputMismatchException e) {
            System.out.println("숫자를 입력해주세요");
            deleteReservation(reservations);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("객실 목록 번호중에 입력해주세요");
            deleteReservation(reservations);
        }
        System.out.println("취소가 완료되었습니다.");
    }
}
