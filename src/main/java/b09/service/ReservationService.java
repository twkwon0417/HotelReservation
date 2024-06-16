package b09.service;

import b09.model.Member;
import b09.model.Reservation;
import b09.model.reservation.ReservedDate;
import b09.model.reservation.RoomNumber;
import b09.repository.MemberRepository;
import b09.repository.ReservationRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    //생성자 메서드
    public ReservationService(ReservationRepository reservationRepository, MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
    }
    public MemberRepository getMemberRepository(){
        return this.memberRepository;
    }
    //한 멤버가 갖고 있는 예약 리스트 반환
    public List<Reservation> getMembersReservation(Member member) {
        return reservationRepository.getReservationByMemberId(member.getId());
    }
    //새로운 예약이 들어왔을때
    public void registerReservation(Reservation reservation) {
        reservationRepository.registerReservation(reservation);
        Member memberToBeEdited = memberRepository.getMemberById(reservation.getMemberId());
        memberToBeEdited.getReservations().add(Math.toIntExact(reservation.getId()));

        // 변경 사항을 파일에 저장
        memberRepository.updateFile();
    }
    //예약 삭제
    public void deleteReservation(Reservation reservation) {
        Member member = memberRepository.getMemberById(reservation.getMemberId());
        member.getReservations().remove(Math.toIntExact(reservation.getId())-1);

        // 변경 사항을 파일에 저장
        memberRepository.updateFile();

        // 예약을 삭제
        reservationRepository.delete(reservation);
    }

    public List<Reservation> findAllReservationOfDate(LocalDate date) {
        List<Reservation> all = reservationRepository.findAll();    // 인자의 date가 Reservation의 StartDate와 EndDate사이에 있는 친구들을 찾아 반환
        List<Reservation> find = new ArrayList<>();
        for(Reservation reservation : all){
            LocalDate startDate = reservation.getReservedDate().getStartDate();
            LocalDate endDate = reservation.getReservedDate().getEndDate();
            if((startDate.isBefore(date) && endDate.isBefore(date)) || startDate.isEqual(date) || endDate.isEqual(date)){
                find.add(reservation);
            }
        }
        return find;
    }

    public void exchangeRoom(Reservation reservation, RoomNumber roomNumber, LocalDate todaysDate) throws Exception{
        // roomnumber가 reservation에서 새로 바꿀 방의 번호입니다. todaysdate에 roomNumber의 방이 사용가능 한지 확인하고, reservation을 수정 해주세요. 사용 가능하지 않다면 꼭 예외 던져주세여
        // 아마 reservation이 쪼개 지는 식으로 구현 될거 같은데, 주석 남겨 주세요ㅕ.
        // MemberRepository, Reservation Repository에 새로운 메서들을 추가하셔야 할껍니당.
        // ReservedDate에 public method로 LocalDate를 입력 받으면 LocalDate가 ReservedDate사이인지 알려주는 메서드 만들어서 활용하면 더 깔끔해질거 같은데, 편하신데로 하세욤
        // 절대 txt 파일이 수정 되어야 합니다.
        // 얘가 가장 복잡해 보이네요 화이팅~
        //의미 규칙 : 이미 해당날짜의 호수에 예약이 있는 경우
        //문법 규칙 : 객실 목록에 없는 문자열 입력, 해당 날짜의 호수에 예약이 있는 경우
        for(Reservation findReserve : reservationRepository.findAll()){
            if (findReserve.getRoomNumber().equals(roomNumber)){
                LocalDate startDate = reservation.getReservedDate().getStartDate();
                LocalDate endDate = reservation.getReservedDate().getEndDate();
                if((startDate.isBefore(todaysDate) && endDate.isBefore(todaysDate)) || startDate.isEqual(todaysDate) || endDate.isEqual(todaysDate)){
                    throw new Exception("이 객실은 이미 예약된 상태입니다. 다른 객실 호수를 입력해주세요.");
                }else{
                    LocalDate reservStart = reservation.getReservedDate().getStartDate();
                    LocalDate reservEnd = reservation.getReservedDate().getEndDate();
                    //해당 날짜의 객실이 빌경우
                    if(todaysDate.isBefore(reservStart)){  //현재 날짜가 예약 전인지 체크
                        registerReservation(new Reservation(reservation.getMemberId(), roomNumber, reservation.getReservedDate(), reservation.getNumberOfPeople(), reservation.getAdditionalProduct()));
                        reservationRepository.delete(reservation);
                    }else{ //이미 사용자가 이용중일 경우
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
                        String str = reservStart.format(formatter);
                        str += " ";
                        str += todaysDate.minusDays(1).format(formatter);
                        registerReservation(new Reservation(reservation.getMemberId(), reservation.getRoomNumber(), new ReservedDate(str, todaysDate), reservation.getNumberOfPeople(), reservation.getAdditionalProduct()));
                        str = "";
                        str += todaysDate.format(formatter);
                        str += " ";
                        str += reservEnd.format(formatter);
                        registerReservation(new Reservation(reservation.getMemberId(), roomNumber, new ReservedDate(str, todaysDate), reservation.getNumberOfPeople(), reservation.getAdditionalProduct()));
                        reservationRepository.delete(reservation);
                    }
                }
            }
        }


    }

    public void extendCheckoutDate(Reservation reservation, LocalDate extendDate) throws Exception {
        // 의미규칙이 만족하지 않을 경우 예외를 던져야ㅑ 합니다. 에러의 예외 메세지 사용장에게 무엇떄문에 불가한지 출력이 되는데 쓰임으로 예쁘게(형식에 맞게)
        // 절대 txt 파일이 수정 되어야 합니다.
        //기존의 체크 아웃 날짜보다 연장할 날짜가 더 과거인가? 검사


        LocalDate endDate = reservation.getReservedDate().getEndDate();
        if(extendDate.isBefore(endDate) || extendDate.isEqual(endDate)){
            throw new Exception("잘못된 날짜 입력입니다. 다시 입력해주세요");
        }

        //호텔 이용기간이 일주일 초과
        long days  = ChronoUnit.DAYS.between(reservation.getReservedDate().getStartDate(), extendDate);
        if(days > 7){
            throw new Exception("호텔 이용은 최대 일주일입니다. 다시 입력해 주세요");
        }

        //기존 체크아웃 날짜로부터 변경할 체크아웃 날짜에 예약이 있는경우

        LocalDate iteratorDate = reservation.getReservedDate().getEndDate();
        for(;iteratorDate.isBefore(extendDate) || iteratorDate.isEqual(extendDate);iteratorDate = iteratorDate.plusDays(1)){
            List<Reservation> reservations =  findAllReservationOfDate(iteratorDate);
            for(Reservation reserve : reservations){
                if(!reserve.equals(reservation)){ //연장되는 예약 제외하고 나머지 예약 확인
                    if(reserve.getRoomNumber().equals(reservation.getRoomNumber())){
                        throw new Exception("해당 날짜에는 예약이 존재합니다. 다른 날짜로 입력해주세요.");
                    }
                }

            }
        }
        //예약 종료 날짜 수정
        reservation.getReservedDate().setEndDate(extendDate);
        reservationRepository.updateFile();

    }


}