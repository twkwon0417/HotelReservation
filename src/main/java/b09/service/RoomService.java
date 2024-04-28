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
    Reservation reservation;

    public static boolean[][] hotelrooms = {
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true}
    };

    public List<String> getRoomOfCondition(ReservedDate reservedDate, Integer roomType) {
        List<String> availableRooms = new ArrayList<>();
        LocalDate userCheckInDate = reservedDate.getStartDate(); // 사용자가 입력한 체크인 날짜
        LocalDate userCheckOutDate = reservedDate.getEndDate(); // 사용자가 입력한 체크아웃 날짜

        List<Reservation> reservedlist = repository.findAll();

        for (Reservation reservation : reservedlist) {
            LocalDate checkInDate = reservation.getCheckInDate();
            LocalDate checkOutDate = reservation.getCheckOutDate();

            if (!userCheckOutDate.isBefore(checkInDate) && !userCheckInDate.isAfter(checkOutDate)) {
                int roomNumber = reservation.getRoomNumber().ofInt();
                int floor = roomNumber / 100 - 1;
                int roomNum = roomNumber % 100 - 1;
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