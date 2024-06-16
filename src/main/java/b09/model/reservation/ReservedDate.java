package b09.model.reservation;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservedDate {
    private LocalDate todaysDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int thisMonth;
    private int thisYear;

    public ReservedDate(String twoDates, LocalDate todaysDate) throws Exception {
        this.todaysDate = todaysDate;
        try {
            String[] dates = twoDates.split(" ");
            if (dates.length != 2) {
                throw new Exception("날짜 형식이 올바르지 않습니다.");
            }
            int startYear = Integer.parseInt(twoDates.substring(0, 2));
            int startMonth = Integer.parseInt(twoDates.substring(2, 4));
            int startDay = Integer.parseInt(twoDates.substring(4, 6));
            int endYear = Integer.parseInt(twoDates.substring(7, 9));
            int endMonth = Integer.parseInt(twoDates.substring(9, 11));
            int endDay = Integer.parseInt(twoDates.substring(11, 13));
            this.startDate = LocalDate.of(2000 + startYear, startMonth, startDay); // TODO: startyear: 20XX 로 찍게
            this.endDate = LocalDate.of(2000 + endYear, endMonth, endDay);
            this.thisYear=2000+startYear;
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new Exception("날짜 형식이 올바르지 않습니다.");
        }
        validate();
    }

    public ReservedDate(LocalDate startDate, LocalDate endDate) {
        this.startDate=startDate;
        this.endDate=endDate;
        this.thisYear = startDate.getDayOfYear();
    }
    public boolean checkEventDay(int thisYear){
        LocalDate theocracy = LocalDate.of(thisYear, 1, 1);
        LocalDate march1 = LocalDate.of(thisYear, 3, 1);
        LocalDate childrenDay = LocalDate.of(thisYear, 5, 5);
        LocalDate memorialDay = LocalDate.of(thisYear, 6, 6);
        LocalDate nationalLiberationDay = LocalDate.of(thisYear, 8, 15);
        LocalDate nationalFoundationDay = LocalDate.of(thisYear, 10, 3);
        LocalDate hangulDay = LocalDate.of(thisYear, 10, 9);
        LocalDate christmas = LocalDate.of(thisYear, 12, 25);
        LocalDate eventDays[] = {theocracy, march1, childrenDay, memorialDay, nationalLiberationDay, nationalFoundationDay,
                hangulDay, christmas};
        for(LocalDate eventDay : eventDays){
            if((eventDay.isEqual(this.startDate) || eventDay.isAfter(startDate)) && (eventDay.isEqual(endDate) || eventDay.isBefore(endDate))){
                return true;
            }
        }

        return false;

    }

    public void setEndDate(LocalDate date){
        this.endDate = date;
    }
    public boolean peakSeasonCheck(){
        this.thisMonth=startDate.getMonthValue();
        if(thisMonth==1||thisMonth==2||thisMonth==7||thisMonth==8||thisMonth==11||thisMonth==12)
            return true;
        else
            return false;
    }
    public int getThisMonth() {
        return this.thisMonth;
    }
    public int getThisYear(){
        return this.thisYear;
    }

    private void validate() throws Exception {
        System.out.println(startDate);
        System.out.println(endDate);
        validateCheckoutBeforeCheckin();
        validateCheckinIsBeforeToday();
        validateStayMoreThanWeek();
        validateCheckinAfterMonth();
    }

    private void validateCheckoutBeforeCheckin() throws Exception {
        if (endDate.compareTo(startDate) <= 0) {
            throw new Exception("체크아웃 날짜는 체크인 날짜보다 뒤에 있어야 합니다.");
        }
    }

    private void validateCheckinIsBeforeToday() throws Exception {
        if (!startDate.isAfter(todaysDate)) {
            throw new Exception("당일 예약은 불가합니다. + 과거 예약은 불가합니다.");
        }
    }

    private void validateStayMoreThanWeek() throws Exception {
        int startDay = startDate.getDayOfMonth();
        int endDay = endDate.getDayOfMonth();
        int startMonth = startDate.getMonthValue();
        int endMonth = endDate.getMonthValue();

        if (startMonth == endMonth) {
            int daysBetween = endDay - startDay + 1;
            if (daysBetween < 1 || daysBetween > 7) {
                throw new Exception("최소 1일에서 최대 7일까지의 숙박만 가능합니다.");
            }
        } else {
            int daysInStartMonth = startDate.lengthOfMonth() - startDay + 1;
            int daysInEndMonth = endDay;
            int totalDays = daysInStartMonth + daysInEndMonth;
            if (totalDays < 1 || totalDays > 7) {
                throw new Exception("최소 1일에서 최대 7일까지의 숙박만 가능합니다.");
            }
        }
    }


    private void validateCheckinAfterMonth() throws Exception {
        LocalDate oneMonthAfter = todaysDate.plusMonths(1).minusDays(1); // 지금 월에서 다음 달로 이동
        if (startDate.isAfter(oneMonthAfter)) {
            throw new Exception("체크인일은 오늘로부터 1달 이내여야 합니다.");
        }
    }
    public void setTodaysDate(LocalDate todaysDate) {
        this.todaysDate = todaysDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getTodaysDate() {
        return todaysDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "예약 시작일: " + startDate.format(formatter) + ", 예약 종료일: " + endDate.format(formatter);
    }


}