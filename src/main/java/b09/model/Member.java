package b09.model;

import b09.model.member.PhoneNumber;
import b09.model.member.Rank;
import java.util.List;

public class Member { // 아마도 완성
    private Long id;
    private final PhoneNumber phoneNumber;
    private int totalMoneySpent;
    private List<Integer> reservations;

    public Member (PhoneNumber phoneNumber, int totalMoneySpent, List<Integer> reservations) {
        this.phoneNumber = phoneNumber;
        this.totalMoneySpent = totalMoneySpent;
        this.reservations = reservations;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTotalMoneySpent(int totalMoneySpent){
        this.totalMoneySpent = totalMoneySpent;
    }
    public Rank getRank() {
        return Rank.setRank(this.totalMoneySpent);
    }

    public Long getId() {
        return id;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public int getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public List<Integer> getReservations() {
        return reservations;
    }
}
