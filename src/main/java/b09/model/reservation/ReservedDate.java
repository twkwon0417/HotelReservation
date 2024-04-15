package b09.model.reservation;

import java.time.LocalDate;

public class ReservedDate {
    LocalDate todaysDate;
    LocalDate startDate;
    LocalDate endDate;

    public ReservedDate(String twoDates) throws Exception {
        // String을 가공해서 startDate, endDate member변수에 LocalDate class 객체로 넣어주세o요
        this.startDate = null;
        this.endDate = null;
    }

    private void validate() throws Exception{   // 기획서 순대로
        validateCheckoutBeforeCheckin();
        validateCheckinIsBeforeToday();
        validateStayMoreThanWeek();
        validateCheckinAfterMonth();
    }

    private void validateCheckoutBeforeCheckin() throws Exception {     // 각각의 문법오류마다 다른 "~~~~ 다시입력해주시요" 구문을 출력합니다 .따하서 Exception에 그에 따라 exceoption message 잘 설정해주세요.

    }

    private void validateCheckinIsBeforeToday() throws Exception {  // todatsDate써야 겠죠

    }

    private void validateStayMoreThanWeek() throws Exception {

    }

    private void validateCheckinAfterMonth() throws Exception {

    }

    public void setTodaysDate(LocalDate todaysDate) {
        this.todaysDate = todaysDate;
    }
}
