package b09.repository;

import b09.model.Reservation;
import b09.model.reservation.AdditionalProduct;
import b09.model.reservation.NumberOfPeople;
import b09.model.reservation.ReservedDate;
import b09.model.reservation.RoomNumber;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationRepository {
    private static long sequence = 0L;
    private static final List<Reservation> reservations = new ArrayList<>();

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
    }

    public void delete(Reservation reservation) {
        reservations.removeIf(r -> r.getId().equals(reservation.getId()));
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }
}
