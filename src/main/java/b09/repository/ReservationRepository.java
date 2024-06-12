package b09.repository;

import b09.model.Reservation;
import b09.model.reservation.AdditionalProduct;
import b09.model.reservation.NumberOfPeople;
import b09.model.reservation.ReservedDate;
import b09.model.reservation.RoomNumber;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationRepository {
    private static long sequence = 0L;
    private List<Reservation> reservations = new ArrayList<>();
    public Reservation getReservationById(Long id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                return reservation;
            }
        }
        return null;
    }

    public ReservationRepository() {
        fileReader2("reservationInfo.txt");
    }

    public void registerReservation(Reservation reservation) {
        reservation.setId(++sequence);
        reservations.add(reservation);
        updateFile();
    }

    public void fileReader2(String filename) {

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\t");
                if(parts[0].isEmpty()){
                    return;
                }
                long id = Long.parseLong(parts[0]);
                long memberId = Long.parseLong(parts[1]);
                RoomNumber roomNumber = new RoomNumber(parts[2]);
                ReservedDate reservedDate = new ReservedDate(LocalDate.parse(parts[3]), LocalDate.parse(parts[4]));
                NumberOfPeople numberOfPeople = new NumberOfPeople(Integer.parseInt(parts[5]));
                AdditionalProduct additionalProduct = new AdditionalProduct(Integer.parseInt(parts[6]), Integer.parseInt(parts[7]), Integer.parseInt(parts[8]));

                Reservation reservation = new Reservation(id, memberId, roomNumber, reservedDate, numberOfPeople, additionalProduct);

                if (!reservations.stream().anyMatch(r -> r.getId() == id)) {
                    reservations.add(reservation);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("파일을 찾을 수 없습니다: " + filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(Reservation reservation) {
        reservations.removeIf(r -> r.getId().equals(reservation.getId()));
        updateFile();
    }

    public List<Reservation> findAll() {
        fileReader2("reservationInfo.txt");
        return new ArrayList<>(reservations);
    }
    public List<RoomNumber> findAllRoomNumbers() {
        fileReader2("reservationInfo.txt");
        List<RoomNumber> roomNumbers = new ArrayList<>();
        for (Reservation reservation : reservations) {
            roomNumbers.add(reservation.getRoomNumber());
        }
        return roomNumbers;
    }

    public void updateFile() {  //public으로 수정해야 될듯
        try (BufferedWriter out = new BufferedWriter(new FileWriter("reservationInfo.txt"))) {
            for (Reservation reservation : reservations) {
                out.write(
                        reservation.getId() + "\t" +
                                reservation.getMemberId() + "\t" +
                                reservation.getRoomNumber() + "\t" +
                                reservation.getCheckInDate() + "\t" +
                                reservation.getCheckOutDate() + "\t" +
                                reservation.getNumberOfPeople() + "\t" +
                                reservation.getBreakfast() + "\t" +
                                reservation.getCasino() + "\t" +
                                reservation.getSpa());
                out.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일을 업데이트하는데 실패했습니다.");
        }
    }

    public List<Reservation> getReservationByMemberId(Long memberId) {
        List<Reservation> ret = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getMemberId().equals(memberId)) {
                ret.add(reservation);
            }
        }
        return ret;
    }
}