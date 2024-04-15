package b09.service;

import b09.model.Member;
import b09.model.Reservation;
import b09.model.reservation.RoomNumber;
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
        return null;
    }

    public void exchangeRoom(Reservation reservation, RoomNumber roomNumber, LocalDate todaysDate) throws Exception{
        // roomnumber가 reservation에서 새로 바꿀 방의 번호입니다. todaysdate에 roomNumber의 방이 사용가능 한지 확인하고, reservation을 수정 해주세요. 사용 가능하지 않다면 꼭 예외 던져주세여
        // 아마 reservation이 쪼개 지는 식으로 구현 될거 같은데, 주석 남겨 주세요ㅕ.
        // MemberRepository, Reservation Repository에 새로운 메서들을 추가하셔야 할껍니당.
        // ReservedDate에 public method로 LocalDate를 입력 받으면 LocalDate가 ReservedDate사이인지 알려주는 메서드 만들어서 활용하면 더 깔끔해질거 같은데, 편하신데로 하세욤
        // 절대 txt 파일이 수정 되어야 합니다.
        // 얘가 가장 복잡해 보이네요 화이팅~
    }

    public void extendCheckoutDate(Reservation reservation, LocalDate todaysDate) throws Exception {
        // 의미규칙이 만족하지 않을 경우 예외를 던져야ㅑ 합니다. 에러의 예외 메세지 사용장에게 무엇떄문에 불가한지 출력이 되는데 쓰임으로 예쁘게(형식에 맞게)
        // 절대 txt 파일이 수정 되어야 합니다.
    }
}
