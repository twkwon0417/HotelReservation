package b09.view;

import b09.model.member.PhoneNumber;
import b09.model.reservation.AdditionalProduct;
import b09.model.reservation.NumberOfPeople;
import b09.model.reservation.ReservedDate;
import b09.model.reservation.RoomNumber;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Scanner;
// TODO: q를 입력 받으면 종료되는 친구들은 q를 입력 받으면 null을 반환하게 해주세요.
public class InputView {
    Scanner scan = new Scanner(System.in);
    public LocalDate inputTodaysDate() {    // 요친구는 따로 객체를 안 만들어서 따로 입력 형식에 따른 예외처리 해줘야 합니다. 이 친구도 q들어오면 null 반환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        while (true) {
            System.out.println("날짜를 YYMMDD 형식으로 입력하세요. (메인메뉴: q) ");
            String input = scan.nextLine().trim();


            if ("q".equalsIgnoreCase(input)) {
                return null;
            }

            try {

                LocalDate todays = LocalDate.parse(input, formatter);
                return todays;
            } catch (DateTimeParseException e) {
                System.out.println("잘못된 날짜 형식입니다. 다시 입력해주세요.(YYMMDD)");
            }
        }
    }

    public Integer inputRoomType() {   // 제대로 사용자가 input주는 지 확인하고 계속 반복 돌려주게 해야도;ㄹ듯

        while (true) { // 올바른 입력을 받을 때까지 반복
            System.out.println("객실 유형을 선택해주세요. (1: STANDARD, 2: PREMIER, 3: SUITE, q: 취소)");
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
        System.out.print("날짜 두개 압력해봐라:");
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
        System.out.println("방번호를 입력해주세요");
        String userInput = scan.nextLine();
        try {
            if (Objects.equals(userInput, "q")) {
                return null;
            }
            int userIntInput = Integer.parseInt(userInput);
            return new RoomNumber(userIntInput);
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력 해주세요.");
            return inputRoomNumber();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return inputRoomNumber();
        }
    }

    public NumberOfPeople inputNumberOfPeople() {
        System.out.println("인원수르 입력해주세요");
        String userInput = scan.nextLine();
        try {
            if (Objects.equals(userInput, "q")) {
                return null;
            }
            int userIntInput = Integer.parseInt(userInput);
            return new NumberOfPeople(userIntInput);
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력 해주세요.");
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
        System.out.println("몇명 할껀데?");
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

    public void inputBreakfast(AdditionalProduct additionalProduct) {   // 이 친구는 시간까지 입력 받고 validation 하나 더 있음!!!!!, 둘의 Exception을 다르게 해서 처리하면 깔끔할듯
        System.out.println("현재 시간을 입력해주세요?");
        String userInput = scan.nextLine();
        LocalTime localTime;
        try {
            localTime = LocalTime.of(Integer.parseInt(userInput.substring(0, 2))
                    , Integer.parseInt(userInput.substring(2, 4)));
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력해주세요");
            inputBreakfast(additionalProduct);
            return;
        } catch (Exception e) {
            System.out.println("유효한 시간 값이 아닙니다.");
            inputBreakfast(additionalProduct);
            return;
        }
        // 합치고 생각하자.
    }

    public void inputCasino(AdditionalProduct additionalProduct) {
        System.out.println("몇명 할껀데? 카지노");
        try {
            additionalProduct.setCasino(Integer.parseInt(scan.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println("숫자가 아닙니다.");
            inputCasino(additionalProduct);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            inputCasino(additionalProduct);
        }
    }

    public int inputWillYouPay() {  // 이상한 친구들 들어오면 예외 처리 해줘잉 반복 필요 x
        System.out.println("결제하시겠습니까? \n" +
                "1. yes 2.no");
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
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다. ");
            return inputYesOrNo();
        }
    }

    public PhoneNumber inputPhoneNumber() { // exception catch -> recursion,  q일 경우 null 반환   Phonenumber는 constructor에서 throws Exception
        System.out.println("전화번호를 입력해 로그인 해주세요.(종료하려면 q를 입력해주세요)");
        String userInput = scan.nextLine();
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
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다. ");
            return inputKillProgram();
        }
    }

    public int inputManagerPage() { // 관리자 화면의 첫 화면 1.회원관리, 2. 객실관리, 3.로그아웃 이거요, 얘 무한 반복, 재귀로
        System.out.println("1. 회원관리 2. 객실관리 3. 로그아웃 \n");
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
            return inputManagerPage();
        }
    }

    public int inputLogoutConfirm() {   // 로그아웃 하시겠습니까?1. yes2. no   무한 반복
        System.out.println("로그아웃 하시겠습니까? \n" +
                "1. yes 2.no");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다. ");
            return inputLogoutConfirm();
        }
    }

    public int inputUserPage() {    // 1. 예약2. 예약 조회 및 취소 3. 로그아웃    고객모드 첫화면
        System.out.println("1. 예약 2. 예약 조회 및 취소 3. 로그아웃 \n");
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
        System.out.println("1. 객실 취소 2. 객실 교체 3. 객실 제한 4.기간 연장 5. 돌아가기 \n");
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
            System.out.println("잘못된 입력입니다. ");
            return inputRoomManagement();
        }
    }

    public int inputRoomCanceled() { // 객실을취소 하시겠습니까?1. yes2. no
        System.out.println("객실을 취소 하시겠습니까? \n" +
                "1. yes 2.no");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다. ");
            return inputRoomCanceled();
        }
    }

    public int inputRoomChanged() { //객실을 교체하시겠습니까?1.yes2.no
        System.out.println("객실을 교체 하시겠습니까? \n" +
                "1. yes 2.no");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다. ");
            return inputRoomChanged();
        }
    }

    public int inputRoomRestricted() {  // 객실을제한 하시겠습니까?1.yes2.no
        System.out.println("객실을 제한 하시겠습니까? \n" +
                "1. yes 2.no");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다. ");
            return inputRoomRestricted();
        }
    }

    public int inputRoomDateExtended() {    // 투숙 기간을연장 하시겠습니까?1.yes2.no
        System.out.println("투숙 기간을 연장 하시겠습니까? \n" +
                "1. yes 2.no");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다. ");
            return inputRoomDateExtended();
        }
    }

    public LocalDate inputNewCheckoutDate() {
        String userInput = scan.nextLine();
//        try {
//            int year = Integer.parseInt(userInput.substring(0,1) + userInput[1])
//            return new LocalDate.of(
//        } catch () {
//
//        } catch() {
//
//        }
        return null;
    }
    public int inputReturnToManagerMenu() {    // 관리자 메뉴로돌아가시겠습니까?1. yes2. no
        System.out.println("로그아웃 하시겠습니까?\n"
                + "1. yes 2. no");
        String userInput = scan.nextLine();

        if(Objects.equals(userInput, "1")) {
            return 1;
        }
        else if (Objects.equals(userInput, "2")){
            return 2;
        }
        else {
            System.out.println("잘못된 입력입니다. ");
            return inputReturnToManagerMenu();
        }
    }

    public PhoneNumber inputUserPhoneNumber() {
        System.out.println("조회하실 회원의 전화번호를 입력해주세요. 종료하려면 q를 입력해주세요.");
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
        return scan.nextLine();
    }
}