package b09.repository;

import b09.model.Reservation;
import java.util.List;

public class ReservationRepository {
    private static long sequence = 0L; //static
    public Reservation getReservationById(Long id) {
        return null;
    }

    public void registerReservation(Reservation reservation) {  // reservation을 등록 해주세요. txt에
        reservation.setId(++sequence);
    }

    public void delete(Reservation reservation) {   // txt 파일이 수정 되어야 함
    }

    public List<Reservation> findAll() {    // 데이터 베이스에서 모든 예약들을 가져와 주세요
        return null;
    }
}
