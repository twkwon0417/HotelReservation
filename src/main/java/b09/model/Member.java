package b09.model;

import b09.model.member.PhoneNumber;
import b09.model.member.Rank;
import java.util.List;

public class Member {
    private Long id;
    private final PhoneNumber phoneNumber;
    private final int totalMoneySpent;
    private final List<Integer> reservations;

    public Member (PhoneNumber phoneNumber, int totalMoneySpent, List<Integer> reservations) {
        this.phoneNumber = phoneNumber;
        this.totalMoneySpent = totalMoneySpent;
        this.reservations = reservations;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rank getRank() {    // Enum 썼는데 잘 하실거라 미ㅏ
        return null;
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
