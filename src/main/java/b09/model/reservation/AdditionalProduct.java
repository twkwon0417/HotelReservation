package b09.model.reservation;

import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdditionalProduct {
    private int NumberOfPeople;     // 예약한 사람수 (eg. 최대 6명 방인데 "4"명)
    private int breakfast;
    private int casino;
    private int spa;

    public AdditionalProduct() {
        this.breakfast = 0;
        this.casino = 0;
        this.spa = 0;
    }

    public int getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(int breakfast) {
        this.breakfast = breakfast;
    }

    public int getCasino() {
        return casino;
    }

    public void setCasino(int casino) throws Exception{
        this.casino = casino;
    }

    public int getSpa() throws Exception {
        return spa;
    }

    public void setSpa(int spa) throws Exception {
        if(doubleChecked())
        this.spa = spa;
    }

    private void validateOverMax(int numberOfPerson) throws Exception{  // 1: set될 숫자, 2: 인원은 1 이상 6 이하의 자연수만 입력받지만 객실 예약 인원수를 초과할 수 없습니다
        // private int NumberOfPeople;     // 예약한 사람수 (eg. 최대 6명 방인데 "4"명) 쓰셈
    }

    private void validateBreakfastTime(LocalTime localTime) throws Exception {

    }

    public int getNumberOfPeople() {
        return NumberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        NumberOfPeople = numberOfPeople;
    }

    private boolean doubleChecked() {
        Scanner scan = new Scanner(System.in);  // 개쓰레기 코드;;;;; ㅈㅅ
        int userInput;
        try {
            System.out.println();       // 예약하시겠습니까? 1.yes 2.no
            userInput = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("숫자 넣어라고");
            return doubleChecked();
        }
        if(userInput == 1) return true;
        else if (userInput == 2) return false;
        else {
            System.out.println("1아니면 2 입력하라고");
            return doubleChecked();
        }
    }
}
