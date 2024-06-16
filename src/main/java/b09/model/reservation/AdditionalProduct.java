package b09.model.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdditionalProduct {

    private ReservedDate reservedDate; // getStartDate()를 이용하기 위해 reservedDate 객체 생성해줌

    public AdditionalProduct(ReservedDate reservedDate) {
        this.reservedDate = reservedDate;
        this.breakfast = 0;
        this.casino = 0;
        this.spa = 0;
    }
    private int NumberOfPeople;     // 예약한 사람수 (eg. 최대 6명 방인데 "4"명)
    private int breakfast;
    private int casino;
    private int spa;

    public AdditionalProduct(int breakfast, int casino, int spa) {
        this.breakfast = breakfast;
        this.casino = casino;
        this.spa = spa;
    }

    public int getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(int breakfast, LocalTime localTime) throws Exception {
        validateBreakfastTime(localTime);
        this.breakfast = breakfast;
    }

    public int getCasino() {
        return casino;
    }

    public void setCasino(int casino) throws Exception {
        validateOverMax(casino);
        if(doubleChecked()) {
            this.casino = casino;
        }
    }

    public int getSpa(){
        return spa;
    }

    public void setSpa(int spa) throws Exception {
        validateOverMax(spa);
        if (doubleChecked()) {
            this.spa = spa;
        }
    }

    private void validateOverMax(int numberOfPerson) throws Exception {
        if (numberOfPerson < 1 || numberOfPerson > getNumberOfPeople()) { // numberOfPerson은 부가서비스 인원, getNumberOfPeople()은 객실 예약 인원
            throw new Exception("예약 인원은 1 이상 " + getNumberOfPeople() + " 이하입니다.");
        }
    }

    public void validateBreakfastTime(LocalTime localTime){
        LocalDate previousDate = reservedDate.getStartDate().minusDays(1); // 예약 시작 날짜의 전날
        LocalTime limitTime = LocalTime.of(20, 0); // 오후 8시 이전까지만

        if (previousDate.equals(reservedDate.getTodaysDate())) {
            if (localTime.isAfter(limitTime)) {
                throw new IllegalArgumentException("오후 8시 이전까지만 예약이 가능합니다.");
            }
        }
    }

    public int getNumberOfPeople() {
        return NumberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        NumberOfPeople = numberOfPeople;
    }

    private boolean doubleChecked() {
        Scanner scan = new Scanner(System.in);
        int userInput;
        try {
            System.out.println("예약하시겠습니까?");
            System.out.println("1.yes 2.no");
            userInput = Integer.parseInt(scan.nextLine());
        } catch (Exception e) {
            System.out.println("숫자를 입력해주세요.");
            return doubleChecked();
        }
        if (userInput == 1) return true;
        else if (userInput == 2) return false;
        else {
            System.out.println("다시 선택해주세요.");
            return doubleChecked();
        }
    }
    @Override
    public String toString() {
        return "breakfast=" + breakfast +
                ", casino=" + casino +
                ", spa=" + spa;
    }
}