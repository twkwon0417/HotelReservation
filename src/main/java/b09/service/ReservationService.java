package b09.service;

import b09.model.Member;
import b09.model.Reservation;
import b09.repository.MemberRepository;
import b09.repository.ReservationRepository;
import java.time.LocalDate;
import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    public ReservationService(ReservationRepository reservationRepository, MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
    }


    public List<Reservation> getMembersReservation(Member member) {
        List<Integer> reservations = member.getReservations();
        return reservations.stream().map(x -> reservationRepository.getReservationById(Long.valueOf(x))).toList();
    }

    public void registerReservation(Reservation reservation) {
        reservationRepository.registerReservation(reservation);
        Member memberToBeEdited = memberRepository.getMemberById(reservation.getMemberId());
        memberToBeEdited.getReservations().add(Math.toIntExact(reservation.getId()));
        Member newMember = new Member(memberToBeEdited.getPhoneNumber(),
                memberToBeEdited.getTotalMoneySpent(),
                memberToBeEdited.getReservations());
        memberRepository.editMember(memberRepository.getMemberById(reservation.getMemberId()), newMember);
    }

    public void deleteReservation(Reservation reservation) {
        Member member = memberRepository.getMemberById(reservation.getMemberId());  // 멤버가 가지고 있는 예약들의 리스트를 수정
        member.getReservations().remove(Math.toIntExact(reservation.getId()));
        memberRepository.editMember(
                member, new Member(
                        member.getPhoneNumber(),
                        member.getTotalMoneySpent(),
                        member.getReservations()
                )
        );

        reservationRepository.delete(reservation);
    }

    public List<Reservation> findAllReservationOfDate(LocalDate date) {
        List<Reservation> all = reservationRepository.findAll();    // 인자의 date가 Reservation의 StartDate와 EndDate사이에 있는 친구들을 찾아 반환
    }
}
