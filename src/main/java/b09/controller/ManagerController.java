package b09.controller;

import b09.model.Member;
import b09.model.Reservation;
import b09.model.member.PhoneNumber;
import b09.model.reservation.RoomNumber;
import b09.repository.MemberRepository;
import b09.repository.ReservationRepository;
import b09.service.MemberService;
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
    MemberService memberService = new MemberService(new MemberRepository());

    public void initMain() {
        int userInput = inputView.inputManagerPage();

        if(userInput == 1) {
            initMemberManagement();
            initMain();
        } else if (userInput == 2) {
            initRoomManagement();
            initMain();
        } else if (userInput == 3) {
            int userLogoutInput = inputView.inputLogoutConfirm();
            if(userLogoutInput == 1) {
                return;
            } else if (userLogoutInput == 2) {
                initMain();
            }
        }
    }

    private void initMemberManagement() {
        PhoneNumber phoneNumber = inputView.inputUserPhoneNumber();
        Member member = null;
        try{
            member = memberService.getByPhoneNumber(phoneNumber);
            if(member == null) throw new Exception("회원을 찾을수 없습니다.");    // try-catch로 반복 시킬려구 만든 어거지 코드입니다. 신경쓰지 않으ㅕ도 됩니당.
        } catch (Exception e) {
            System.out.println(e.getMessage());
            initMemberManagement();
        }

        changeMemberRank(member);   // command를 입력 받는 메서드 이 메서드 안에서 입력을 박는다.
    }

    private void changeMemberRank(Member member) {
        String command = inputView.inputChangeRankCommand();
        try {
            memberService.changeMemberRank(member, command);    // 받은 입력은 memberService에서 처리, 38개 단어들 중 하나가 나올떄 까지 계속 반복됨
        } catch (Exception e) {
            System.out.println(e.getMessage());
            changeMemberRank(member);
        }
    }
    private void initRoomManagement() {
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

        roomManagement(matchingReservation.get(), todaysDate);
    }

    private void roomManagement(Reservation reservation, LocalDate todaysDate) {
        int userInput = inputView.inputRoomManagement();
        if(userInput == 1) {
            cancelRoom(reservation, todaysDate);
            roomManagement(reservation, todaysDate);
        } else if (userInput == 2) {
            changeRoom(reservation, todaysDate);
            roomManagement(reservation, todaysDate);
        } else if (userInput == 3) {
            restrictRoom(reservation, todaysDate);
            roomManagement(reservation, todaysDate);
        } else if (userInput == 4) {
            extendStayingDate(reservation, todaysDate);
            roomManagement(reservation, todaysDate);
        } else if(userInput == 5) {
            int userReturnInput = inputView.inputReturnToManagerMenu();
            if(userReturnInput == 1) {
                return;
            }
            initRoomManagement();
        }
    }

    private void cancelRoom(Reservation reservation, LocalDate todaysDate) { // roomManagement의 1번   // db update, input, output
        int userInput = inputView.inputRoomCanceled();
        if(userInput == 1) {
            reservationService.deleteReservation(reservation);
            outputView.printRoomCanceled();
        } else if (userInput == 2) {
            roomManagement(reservation, todaysDate);    // 다시화면으로
        }
    }

    private void changeRoom(Reservation reservation, LocalDate todaysDate) {  // roomManagement의 2번
        int userInput = inputView.inputRoomChanged();
        if(userInput == 1) {
            RoomNumber roomNumber = inputView.inputRoomNumber();
            try {
                reservationService.exchangeRoom(reservation, roomNumber, todaysDate);   // 꼭 exception을 던지게 해주세요. 만약, 해당 roomNumber가
            } catch (Exception e) {
                System.out.println(e.getMessage());
                changeRoom(reservation, todaysDate);
            }
            outputView.printRoomChanged();
        } else if (userInput == 2) {
            roomManagement(reservation, todaysDate);
        }
    }

    private void restrictRoom(Reservation reservation, LocalDate todaysDate) {  // TODO:roomManagement의 3번 보류
        int userInput = inputView.inputRoomRestricted();
        if(userInput == 1) {
//            reservationService.       // 일단 보류
            outputView.printRoomRestricted();
        } else if (userInput == 2) {
            roomManagement(reservation, todaysDate);
        }
    }

    private void extendStayingDate(Reservation reservation, LocalDate todaysDate) {  // roomManagement의 4번- Exception  2와 같이
        int userInput = inputView.inputRoomDateExtended();
        if(userInput == 1) {
            LocalDate newCheckoutDate = inputView.inputNewCheckoutDate();
            try {
                reservationService.extendCheckoutDate(reservation, todaysDate);     // 요 친구 error메세지!!!! 각 validation마다(한달 넘어, 날짜가 원래 체크아웃보다 작아 등등)
            } catch (Exception e) {
                System.out.println(e.getMessage());
                extendStayingDate(reservation, todaysDate);
            }
            outputView.printRoomDateExtended();
        } else if (userInput == 2) {
            roomManagement(reservation, todaysDate);
        }
    }
}
