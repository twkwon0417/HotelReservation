package b09.model;

import b09.model.member.PhoneNumber;
import b09.model.member.Rank;
import java.util.List;

public class Member { // 아마도 완성
    private Long id;
    private PhoneNumber phoneNumber;
    private int totalMoneySpent;
    private List<Integer> reservations;

    public Member (PhoneNumber phoneNumber, int totalMoneySpent, List<Integer> reservations) {
        this.phoneNumber = phoneNumber;
        this.totalMoneySpent = totalMoneySpent;
        this.reservations = reservations;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setReservations(List<Integer> reservations){
        if(reservations.size() != 0){
            this.reservations.clear();
            for (int reservation : reservations){
                this.reservations.add(reservation); // 기존의 리스트 내용을 비우고 파라미터로 받아온 리스트 내용을 채움.
            }
        }
    }


    public Rank getRank() {
        return Rank.setRank(this.totalMoneySpent);
    }

    public Long getId() {
        return id;
    }

    public void setTotalMoneySpent(int totalMoneySpent) {
        this.totalMoneySpent = totalMoneySpent;
    }

    public int getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }


    public List<Integer> getReservations() {
        return reservations;
    }
}
