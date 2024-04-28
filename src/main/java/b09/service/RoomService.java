package b09.service;

import b09.model.Reservation;
import b09.model.reservation.ReservedDate;
import b09.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomService{

    public final Integer STANDARD = 1;
    public final Integer PREMIER = 2;
    public final Integer SUITE = 3;
    ReservationRepository repository;

    public boolean[][] hotelrooms = {
            // 내가 제한을 걸고 싶은 방은 수작업으로 해당 인덱스 부분 false라고 수정한다
            // standard
            {true, true, true, true, true, true, true, true, true, true},
            // premier
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            // suite
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true}
    };

    public List<String> getRoomOfCondition(ReservedDate reservedDate, Integer roomType) {
        List<String> availableRooms = new ArrayList<>();
        LocalDate today = reservedDate.getTodaysDate(); // 요 메소드 뭔가 추가하면 좋을거같아요
        LocalDate oneMonthLater = today.plusMonths(1); // 한 달 후의 날짜

        List<Reservation> hasReservations = repository.findAll();

        // 한 달 이내의 예약이 있는 방을 찾아서 해당 방을 사용할 수 없도록 설정
        for (Reservation reservation : hasReservations) {
            LocalDate checkInDate = reservation.getCheckInDate(); // 요 메소드 뭔가 추가하면 좋을거같아요
            LocalDate checkOutDate = reservation.getCheckOutDate(); // 요 메소드 뭔가 추가하면 좋을거같아요

            // 예약 날짜가 사용자가 입력한 날짜로부터 한 달 이내인지 확인
            if (!(checkOutDate.isBefore(today) || checkInDate.isAfter(oneMonthLater))) {
                int floor = reservation.getRoomNumber() / 100 - 2;
                int roomNum = reservation.getRoomNumber() % 100;
                hotelrooms[floor][roomNum] = false;
            }
        }

        int startFloor = 0, endFloor = 0;
        switch (roomType) {
            case 1:
                startFloor = 0; // standard 시작 층
                endFloor = 4; // standard 종료 층
                break;
            case 2:
                startFloor = 5; // premier 시작 층
                endFloor = 7; // premier 종료 층
                break;
            case 3:
                startFloor = 8; // suite 시작 층
                endFloor = 8; // suite 종료 층
                break;
        }

        for (int floor = startFloor; floor <= endFloor; floor++) {
            for (int roomNum = 0; roomNum < hotelrooms[floor].length; roomNum++) {
                if (hotelrooms[floor][roomNum]) {
                    availableRooms.add((floor+2) + "0" + (roomNum+1)+"호"); // 아 근데 10호면 2010호 이렇게 되네요...
                }
            }
        }

        return availableRooms;
    }
}