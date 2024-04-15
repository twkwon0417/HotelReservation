package b09.controller;

import b09.model.Reservation;
import b09.model.reservation.RoomNumber;
import b09.repository.MemberRepository;
import b09.repository.ReservationRepository;
import b09.service.ReservationService;
import b09.view.InputView;
import b09.view.OutputView;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ManagerController {
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();
    ReservationService reservationService = new ReservationService(new ReservationRepository(), new MemberRepository());
    public void initMain() {
        int userInput = inputView.inputManagerPage();

        if(userInput == 1) {
            initRoomManagement();
            initMain();
        } else if (userInput == 2) {

        } else if (userInput == 3) {

        }
    }

    public void initMemberManagement() {

    }
    public void initRoomManagement() {
        LocalDate todaysDate= inputView.inputTodaysDate();

        List<Reservation> reservations = reservationService.findAllReservationOfDate(todaysDate);

        if(reservations.isEmpty()) {
            outputView.printNoReservation();
            return;
        }
        RoomNumber roomNumber = inputView.inputRoomNumber();

        // 해당 날짜에 예약된 방들중 해당 방번호인 예약을 찾는 코드 입니다.
        Optional<Reservation> matchingReservation = reservations.stream().filter(x -> x.getRoomNumber() == roomNumber).findAny();
        if(matchingReservation.isEmpty()) {
            outputView.printNoMatchingRoomNumberOfDate();
            return;
        }


    }

    private void roomManagement(Reservation reservation) {
        int userInput = inputView.inputRoomManagement();
        if(userInput == 1) {

        } else if (userInput == 2) {

        } else if (userInput == 3) {

        } else if (userInput == 4) {

        } else if(userInput == 5) {

        }
    }

    private void cancelRoom(Reservation reservation) { // roomManagement의 1번   // db update, input, output
        int userInput = inputView.inputRoomCanceled();
        if(userInput == 1) {
            reservationService.deleteReservation(reservation);
            outputView.printRoomCanceled();
        } else if (userInput == 2) {
            roomManagement(reservation);    // 다시화면으로
        }
    }

    private void changeRoom(Reservation reservation) {  // roomManagement의 2번
        int userInput = inputView.inputRoomChanged();
        if(userInput == 1) {
            reservationService.
            outputView.printRoomChanged();
        } else if (userInput == 2) {
            roomManagement(reservation);
        }
    }

    private void restrictRoom(Reservation reservation) {  // roomManagement의 3번
        int userInput = inputView.inputRoomRestricted();
        if(userInput == 1) {
            outputView.printRoomRestricted();
        } else if (userInput == 2) {
            roomManagement(reservation);
        }
    }

    private void extendStayingDate(Reservation reservation) {  // roomManagement의 4번
        int userInput = inputView.inputRoomDateExtended();
        if(userInput == 1) {
            outputView.printRoomDateExtended();
        } else if (userInput == 2) {
            roomManagement(reservation);
        }
    }
}
