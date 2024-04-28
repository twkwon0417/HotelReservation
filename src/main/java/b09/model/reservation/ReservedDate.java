package b09.model.reservation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ReservedDate {
    private LocalDate todaysDate;
    private LocalDate startDate;
    private LocalDate endDate;

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
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new Exception("날짜 형식이 올바르지 않습니다.");
        }
        validate();
    }

    public ReservedDate(LocalDate startDate, LocalDate endDate) {
        this.startDate=startDate;
        this.endDate=endDate;
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
        int daysBetween = (endDate.getDayOfMonth() - startDate.getDayOfMonth()) + 1;
        if (daysBetween < 1 || daysBetween > 7) {
            throw new Exception("최소 1일에서 최대 7일까지의 숙박만 가능합니다.");
        }
    }

    private void validateCheckinAfterMonth() throws Exception {
        LocalDate oneMonthAfter = todaysDate.withDayOfMonth(1).plusMonths(1).minusDays(1); // 지금 월에서 다음 달로 이동 + 일 수는 하루 뺌
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
}
