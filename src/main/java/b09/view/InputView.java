package b09.view;

import b09.model.member.PhoneNumber;
import b09.model.reservation.AdditionalProduct;
import b09.model.reservation.NumberOfPeople;
import b09.model.reservation.ReservedDate;
import b09.model.reservation.RoomNumber;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
// TODO: q를 입력 받으면 종료되는 친구들은 q를 입력 받으면 null을 반환하게 해주세요.
public class InputView {
    Scanner scan = new Scanner(System.in);
    public LocalDate inputTodaysDate() {    // 요친구는 따로 객체를 안 만들어서 따로 입력 형식에 따른 예외처리 해줘야 합니다. 이 친구도 q들어오면 null 반환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        while (true) {
            System.out.println("날짜를 YYMMDD 형식으로 입력하세요. (메인메뉴: q)");
            System.out.print("> ");
            String input = scan.nextLine().trim();


            if ("q".equalsIgnoreCase(input)) {
                return null;
            }

            try {

                LocalDate todays = LocalDate.parse(input, formatter);
                return todays;
            } catch (DateTimeParseException e) {
                System.out.println("잘못된 날짜 형식입니다. 다시 입력해주세요.(YYMMDD)");
                System.out.print("> ");
            }
        }
    }

    public Integer inputRoomType(ReservedDate datecheck) {

        while (true) { // 올바른 입력을 받을 때까지 반복
            if(datecheck.checkEventDay(datecheck.getThisYear())){
                System.out.println("이벤트 데이가 포함되어있습니다. 예약은 인상된 가격으로 진행됩니다.");
                System.out.println("객실 등급을 선택해주세요. (메인메뉴 : q)");
                System.out.println("1. standard(150,000원)");
                System.out.println("2. premier(225,000원)");
                System.out.println("3. suite(300,000원)");
            }
            else if(datecheck.peakSeasonCheck()){
                System.out.println(datecheck.getThisMonth()+"월 예약은 인상된 가격으로 진행됩니다.");
                System.out.println("객실 등급을 선택해주세요. (메인메뉴 : q)");
                System.out.println("1. standard(130,000원)");
                System.out.println("2. premier(195,000원)");
                System.out.println("3. suite(260,000원)");
            }
            else {
                System.out.println("객실 등급을 선택해주세요. (메인메뉴 : q)");
                System.out.println("1. standard(100,000원)");
                System.out.println("2. premier(150,000원)");
                System.out.println("3. suite(200,000원)");
            }
            System.out.print("> ");
            String input = scan.nextLine(); // 사용자 입력 받기

            switch (input) { // 입력에 따라 처리
                case "1":
                    return 1; // STANDARD 반환
                case "2":
                    return 2; // PREMIER 반환
                case "3":
                    return 3; // SUITE 반환
                case "q":
                    return null; // q 입력 시 null 반환
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요."); // 잘못된 입력 처리
                    break;
            }
        }
    }

    public ReservedDate inputReservedDate(LocalDate todaysDate) {   // 이런 식으로 try-catch로 원하는 입력이 들어올떄까지 계속 돌릴슈 있음. 참고 하면 좋읅섯 같습니다.
        System.out.println("체크인, 체크아웃 날짜를 입력해주세요.(YYMMDD YYMMDD)(메인메뉴: q)");
        System.out.print("> ");
        String input = scan.nextLine();
        try {
            if (Objects.equals(input, "q")) {
                return null;
            }
            return new ReservedDate(input, todaysDate);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 각 예외 케이스의 에러 메세지를 출력 eg. 한달보다 넘게 예약할수 없습니다.
            return inputReservedDate(todaysDate); // 그리고 다시 입력 받아
        }
    }

    public RoomNumber inputRoomNumber() {   // q입력 받으면 null반환
        System.out.println("예약할 객실의 호수를 입력해주세요. (메인메뉴 : q)");
        System.out.print("> ");
        String userInput = scan.nextLine();
        try {
            if (Objects.equals(userInput, "q")) {
                return null;
            }
            int userIntInput = Integer.parseInt(userInput);
            return new RoomNumber(userIntInput);
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력 해주세요.");
            System.out.print("> ");
            return inputRoomNumber();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return inputRoomNumber();
        }
    }

    public RoomNumber inputSpecificRoomNumber(List<String> availableRooms) {
        System.out.println("예약할 객실의 호수를 입력해주세요. (메인메뉴 : q)");
        System.out.print("> ");
        String userInput = scan.nextLine();
        if (Objects.equals(userInput, "q")) {
            return null;
        }
        try {
            int userIntInput = Integer.parseInt(userInput);
            for (String roomNumber : availableRooms) {
                if (userInput.equals(roomNumber)) {
                    return new RoomNumber(userIntInput);
                }
            }
            throw new Exception("보여준 예약 가능한 방들 중 하나를 입력해주세요.");
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력 해주세요.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return inputSpecificRoomNumber(availableRooms);
    }


    public NumberOfPeople inputNumberOfPeople() {
        System.out.println("인원 수를 입력해주세요.(기본 4인, 최대 6인 입니다.) (메인메뉴 : q)");
        System.out.print("> ");
        String userInput = scan.nextLine();
        try {
            if (Objects.equals(userInput, "q")) {
                return null;
            }
            int userIntInput = Integer.parseInt(userInput);
            return new NumberOfPeople(userIntInput);
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력 해주세요.");
            System.out.print("> ");
            return inputNumberOfPeople();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return inputNumberOfPeople();
        }
    }

    public int inputAdditionalProductMenu() {
        System.out.println("신청하실 서비스 번호를 입력해주세요. (메인메뉴 : q)\n"
                + "1. 카지노 예약 (1인 50,000원)\n"
                + "2. 스파 예약 (1인 30,000원)\n"
                + "3. 조식 예약 (0원)\n"
                + "4. 예약 마무리");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        } else if (Objects.equals(userInput, "2")){
            return 2;
        } else if (Objects.equals(userInput, "3")) {
            return 3;
        } else if (Objects.equals(userInput, "4")) {
            return 4;
        } else if (Objects.equals(userInput, "q")) {
            return -1;
        } else {
            return 0;
        }
    }

    public void inputSpa(AdditionalProduct additionalProduct) {    // 예 따로 반환 값은 없고 바로 setting
        System.out.println("스파 서비스를 이용하실 인원 수를 입력해주세요.");
        System.out.print("> ");
        try {
            additionalProduct.setSpa(Integer.parseInt(scan.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println("숫자가 아닙니다.");
            inputSpa(additionalProduct);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            inputSpa(additionalProduct);
        }
    }

    public void inputBreakfast(AdditionalProduct additionalProduct) {
        // stack overflow -> 재귀호출 (x)  반복문 (o)
        boolean validInput = false;
        while (!validInput) {
            System.out.println("현재 시간을 입력해주세요 (예: 0800):");
            System.out.print("> ");
            String userInput = scan.nextLine();
            int hour = Integer.parseInt(userInput.substring(0, 2));
            int minute = Integer.parseInt(userInput.substring(2,4));

            try {
                // 입력된 input이 4자리가 아닌 경우
                if (userInput.length() != 4) {
                    throw new IllegalArgumentException("시간은 4자리로 입력해주세요 (예: 0800).\n");
                }
                // 입력된 hour이 0~23이 아닌 경우
                if (hour < 0 || hour > 23) {
                    throw new IllegalArgumentException("시간은 0부터 23까지의 숫자로 입력해주세요.\n");
                }
                // 입력된 minute이 00~59가 아닌 경우
                if (minute < 0 || minute > 59) {
                    throw new IllegalArgumentException("분은 00부터 59까지의 숫자로 입력해주세요.\n");
                }
                LocalTime localTime = LocalTime.of(hour, minute);
                // 여기서 localtime 검증 한번더
                additionalProduct.validateBreakfastTime(localTime);
                validInput = true; // 올바른 입력이 들어왔으므로 루프 종료
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return;
            }
        }


        System.out.println("조식 서비스를 이용하실 인원 수를 입력해주세요.");
        System.out.print("> ");
        int numberOfPeople;
        try {
            numberOfPeople = Integer.parseInt(scan.nextLine());
            // 예약 인원 수를 초과하는지 검증
            if (numberOfPeople > additionalProduct.getNumberOfPeople()) {
                throw new IllegalArgumentException("예약 인원 수를 초과하였습니다. 다시 입력해주세요.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력해주세요.");
            inputBreakfast(additionalProduct);
            return;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            inputBreakfast(additionalProduct); // 인원 수 재입력을 위해 다시 호출
            return;
        }

        System.out.println("해당 인원 수 만큼 조식을 예약하시겠습니까?");
        System.out.println("1. yes 2. no");
        System.out.print("> ");
        String response = scan.nextLine();
        if ("1".equals(response)) {
            try {
                // additionalProduct.setBreakfast(numberOfPeople, localTime);
                System.out.println("* 예약이 완료되었습니다. 조식 이용 가능 시간대는 7:00 ~ 11:00 입니다. *");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                inputBreakfast(additionalProduct);
            }
        } else if ("2".equals(response)) {
            System.out.println("조식 예약이 취소되었습니다.");
            inputAdditionalProductMenu(); // 다른 메뉴로 돌아가는 메소드 호출
        } else {
            System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            inputBreakfast(additionalProduct);
        }
    }


    public void inputCasino(AdditionalProduct additionalProduct) {
        System.out.println("카지노 서비스를 이용하실 인원 수를 입력해주세요.");
        System.out.print("> ");
        try {
            additionalProduct.setCasino(Integer.parseInt(scan.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println("숫자가 아닙니다.");
            System.out.print("> ");
            inputCasino(additionalProduct);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            inputCasino(additionalProduct);
        }
    }

    public int inputWillYouPay() {  // 이상한 친구들 들어오면 예외 처리 해줘잉 반복 필요 x
        System.out.println("결제하시겠습니까? \n" +
                "1. yes 2.no");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            return -1;
        }
    }

    public int inputYesOrNo() { // 고객모드 예약 취소할때 확인 메시지 부분입니다. 요친구 재귀로 올바른 값 들어올떄까지 반복
        System.out.println("예약을 취소하시겠습니까? \n" +
                "1. yes 2.no");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(userInput.equals("1")) {
            return 1;
        }
        else if (userInput.equals("2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다.");
            return inputYesOrNo();
        }
    }

    public PhoneNumber inputPhoneNumber() throws Exception{ // exception catch -> recursion,  q일 경우 null 반환   Phonenumber는 constructor에서 throws Exception
        System.out.println("전화번호를 입력해 로그인 해주세요.(종료하려면 q를 입력해주세요)");
        System.out.print("> ");
        String userInput = scan.nextLine();
        if(Objects.equals(userInput, "B09")){
            throw new Exception("매니저입니다.");
        }
        try {
            if(Objects.equals(userInput, "q")) {
                return null;
            }
            return new PhoneNumber(userInput);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return inputPhoneNumber();
        }
    }

    public int inputKillProgram() { // 제대로되 input이 들어올때 까지 무한 반복
        System.out.println("프로그램을 종료하시겠습니까? \n" +
                "1. yes 2.no");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다. ");
            System.out.print("> ");
            return inputKillProgram();
        }
    }

    public int inputManagerPage() { // 관리자 화면의 첫 화면 1.회원관리, 2. 객실관리, 3.로그아웃 이거요, 얘 무한 반복, 재귀로
        System.out.println("1. 회원관리 2. 객실관리 3. 로그아웃");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        } else if (Objects.equals(userInput, "3")) {
            return 3;
        }
        else {
            System.out.println("잘못된 입력입니다.");
            System.out.print("> ");
            return inputManagerPage();
        }
    }

    public int inputLogoutConfirm() {   // 로그아웃 하시겠습니까?1. yes2. no   무한 반복
        System.out.println("로그아웃 하시겠습니까? \n" +
                "1. yes 2.no");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다.");
            System.out.print("> ");
            return inputLogoutConfirm();
        }
    }

    public int inputUserPage() {    // 1. 예약2. 예약 조회 및 취소 3. 로그아웃    고객모드 첫화면
        System.out.println("1. 예약 2. 예약 조회 및 취소 3. 로그아웃");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        } else if (Objects.equals(userInput, "3")) {
            return 3;
        }
        else {
            System.out.println("잘못된 입력입니다. ");
            return inputUserPage();
        }
    }

    public int inputRoomManagement() { // 1. 객실 취소2. 객실 교체3. 객실 제한4. 기간 연장5. 돌아가기
        System.out.println("1. 객실 취소 2. 객실 교체 3. 객실 제한 4.기간 연장 5. 돌아가기");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        } else if (Objects.equals(userInput, "3")) {
            return 3;
        } else if (Objects.equals(userInput, "4")) {
            return 4;
        } else if (Objects.equals(userInput, "5")) {
            return 5;
        } else {
            System.out.println("잘못된 입력입니다.");
            System.out.print("> ");
            return inputRoomManagement();
        }
    }

    public int inputRoomCanceled() { // 객실을취소 하시겠습니까?1. yes2. no
        System.out.println("객실을 취소 하시겠습니까? \n" +
                "1. yes 2.no");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다.");
            System.out.print("> ");
            return inputRoomCanceled();
        }
    }

    public int inputRoomChanged() { //객실을 교체하시겠습니까?1.yes2.no
        System.out.println("객실을 교체 하시겠습니까? \n" +
                "1. yes 2.no");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다.");
            System.out.print("> ");
            return inputRoomChanged();
        }
    }

    public int inputRoomRestricted() {  // 객실을제한 하시겠습니까?1.yes2.no
        System.out.println("객실을 제한 하시겠습니까? \n" +
                "1. yes 2.no");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다.");
            System.out.print("> ");
            return inputRoomRestricted();
        }
    }

    public int inputRoomDateExtended() {    // 투숙 기간을연장 하시겠습니까?1.yes2.no
        System.out.println("투숙 기간을 연장 하시겠습니까? \n" +
                "1. yes 2.no");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다.");
            System.out.print("> ");
            return inputRoomDateExtended();
        }
    }

    public LocalDate inputNewCheckoutDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        while (true) {
            System.out.println("새로운 checkout 날짜를 YYMMDD 형식으로 입력하세요. (메인메뉴: q) ");
            System.out.print("> ");
            String input = scan.nextLine().trim();


            if ("q".equalsIgnoreCase(input)) {
                return null;
            }

            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("잘못된 날짜 형식입니다. 다시 입력해주세요.(YYMMDD)");
                System.out.print("> ");
            }
        }
    }
    public int inputReturnToManagerMenu() {    // 관리자 메뉴로돌아가시겠습니까?1. yes2. no
        System.out.println("로그아웃 하시겠습니까?\n"
                + "1. yes 2. no");
        System.out.print("> ");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다.");
            System.out.print("> ");
            return inputReturnToManagerMenu();
        }
    }

    public PhoneNumber inputUserPhoneNumber() {
        System.out.println("조회하실 회원의 전화번호를 입력해주세요. 종료하려면 q를 입력해주세요.");
        System.out.print("> ");
        String userInput = scan.nextLine();
        try {
            if(Objects.equals(userInput, "q")) {
                return null;
            }
            return new PhoneNumber(userInput);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return inputUserPhoneNumber();
        }
    }

    public String inputChangeRankCommand() {
        System.out.println("바꾸실 등급을 입력해주세요.");
        System.out.print("> ");
        return scan.nextLine();
    }
}