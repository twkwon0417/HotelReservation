package b09.model.reservation;

import b09.model.room.Constants;

public class NumberOfPeople {
    private final int numberOfPeople;

    public NumberOfPeople(int numberOfPeople) throws Exception {
        this.numberOfPeople = numberOfPeople;
        validate();
    }

    private void validate() throws Exception {
        if (numberOfPeople < 1 || numberOfPeople > Constants.MAX_PERSON_PER_ROOM) {
            throw new Exception("인원 수는 1에서 " + Constants.MAX_PERSON_PER_ROOM + " 사이여야 합니다.");
        }
    }

    public int getInt() {
        return this.numberOfPeople;
    }

    @Override
    public String toString() {
        return String.valueOf(numberOfPeople);
    }
}
