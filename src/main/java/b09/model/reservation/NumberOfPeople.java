package b09.model.reservation;

import b09.model.room.Constants;

public class NumberOfPeople {
    private final int numberOfPeople;

    public NumberOfPeople(int numberOfPeople) throws Exception {
        this.numberOfPeople = numberOfPeople;
    }

    private void validate() throws Exception {   // 기본적인 예외처리 + 한 방의 max인원보다 많은 경우
        // Constants.MAX_PERSON_PER_ROOM 활용
    }

    public int getInt() {
        return this.numberOfPeople;
    }
}
