package b09.repository;

import b09.model.Reservation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

    public void registerReservation(Reservation reservation) {
        reservation.setId(++sequence);
        reservations.add(reservation);
        updateFile();
    }

    public void fileReader(String filename) {
        try (Scanner file = new Scanner(new File(filename))) {
            while (file.hasNextLine()) {
                String line = file.nextLine();
                String[] parts = line.split("\t");
                Reservation reservation = new Reservation();
                reservation.setId(Long.parseLong(parts[0]));
                reservation.setCustomerIndex(Long.parseLong(parts[1]));
                reservation.setRoomNumber(parts[2]);
                reservation.setCheckInDate(parts[3]);
                reservation.setCheckOutDate(parts[4]);
                reservation.setNumberOfGuests(Integer.parseInt(parts[5]));
                reservation.setNumberOfBreakfast(Integer.parseInt(parts[6]));
                reservation.setNumberOfCasino(Integer.parseInt(parts[7]));
                reservation.setNumberOfSpa(Integer.parseInt(parts[8]));
                reservations.add(reservation);
            }
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다.");
        }
    }

    public void delete(Reservation reservation) {
        reservations.removeIf(r -> r.getId().equals(reservation.getId()));
        updateFile();
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    private void updateFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("reservations.txt"))) {
            for (Reservation reservation : reservations) {
                out.write(reservation.getId() + "\t" +
                                reservation.getCustomerIndex() + "\t" +
                                reservation.getRoomNumber() + "\t" +
                                reservation.getCheckInDate() + "\t" +
                                reservation.getCheckOutDate() + "\t" +,
                        reservation.getNumberOfGuests() + "\t" +
                                reservation.getNumberOfBreakfast() + "\t" +
                                reservation.getNumberOfCasino() + "\t" +
                                reservation.getNumberOfSpa());
                out.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일을 업데이트하는데 실패했습니다.");
        }
    }
}
