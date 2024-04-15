package b09.view;

import b09.model.member.PhoneNumber;
import b09.model.reservation.AdditionalProduct;
import b09.model.reservation.NumberOfPeople;
import b09.model.reservation.ReservedDate;
import b09.model.reservation.RoomNumber;
import b09.model.room.RoomType;
import java.time.LocalDate;
import java.util.Scanner;
// TODO: q를 입력 받으면 종료되는 친구들은 q를 입력 받으면 null을 반환하게 해주세요.
public class InputView {
    Scanner scan = new Scanner(System.in);
    public LocalDate inputTodaysDate() {    // 요친구는 따로 객체를 안 만들어서 따로 입력 형식에 따른 예외처리 해줘야 합니다. 이 친구도 q들어오면 null 반환

        return null;
    }

    public RoomType inputRoomType() {   // 애도 enum이라서 제대로 사용자가 input주는 지 확인하고 계속 반복 돌려주게 해야도;ㄹ듯
        return null;
    }

    public ReservedDate inputReservedDate() {   // 이런 식으로 try-catch로 원하는 입력이 들어올떄까지 계속 돌릴슈 있음. 참고 하면 좋읅섯 같습니다.
        System.out.print("날짜 두개 압력해봐라:");
        String input = scan.next();
        try {
            return new ReservedDate(input);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 각 예외 케이스의 에러 메세지를 출력 eg. 한달보다 넘게 예약할수 없습니다.
            return inputReservedDate(); // 그리고 다시 입력 받아
        }
    }

    public RoomNumber inputRoomNumber() {   // q입력 받으면 null반환
        return null;
    }

    public NumberOfPeople inputNumberOfPeople() {
        return null;
    }

    public int inputAdditionalProductMenu() {   // 요 친구는 null 반환 한되서 controller에서 해야됨. (q를 입력받으면 다시 돌아기는 친군데)
        // 일단 반환값이 int라서 Integer.parseInt할껀데 그전에 q인지 확인 해서 q라면 -1 return 하게 해주세요.
        return 1;
    }

    public void inputSpa(AdditionalProduct additionalProduct) {    // 예 따로 반환 값은 없고 바로 setting
        System.out.println("몇명 할껀데?");
        try {
            additionalProduct.setSpa(Integer.parseInt(scan.next()));
        } catch (NumberFormatException e) {
            System.out.println("숫자가 아닙니다.");
            inputSpa(additionalProduct);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            inputSpa(additionalProduct);
        }
    }

    public void inputBreakfast(AdditionalProduct additionalProduct) {   // 이 친구는 시간까지 입력 받고 validation 하나 더 있음!!!!!, 둘의 Exception을 다르게 해서 처리하면 깔끔할듯
        // line 44 참고
    }

    public void inputCasino(AdditionalProduct additionalProduct) {
        // line 44 참고
    }

    public int inputWillYouPay() {  // 이상한 친구들 들어오면 예외 처리 해줘잉 반복 필요 x
        return 1;
    }

    public int inputYesOrNo() { // 고객모드 예약 취소할때 확인 메시지 부분입니다. 요친구 재귀로 올바른 값 들어올떄까지 반복
        return 1;
    }

    public PhoneNumber inputPhoneNumber() { // exception catch -> recursion,  q일 경우 null 반환   Phonenumber는 constructor에서 throws Exception
        return null;
    }

    public int inputKillProgram() { // 제대로되 input이 들어올때 까지 무한 반복
        return 1;
    }

    public int inputManagerPage() { // 관리자 화면의 첫 화면 1.회원관리, 2. 객실관리, 3.로그아웃 이거요, 얘 무한 반복, 재귀로
        return 1;
    }

    public int inputLogoutConfirm() {   // 로그아웃 하시겠습니까?1. yes2. no   무한 반복
        return 0;
    }

    public int inputUserPage() {    // 1. 예약2. 예약 조회 및 취소 3. 로그아웃    고객모드 첫화면
        return 0;
    }

    public int inputRoomManagement() { // 1. 객실 취소2. 객실 교체3. 객실 제한4. 기간 연장5. 돌아가기
        return 0;
    }

    public int inputRoomCanceled() { // 객실을취소 하시겠습니까?1. yes2. no
        return 0;
    }

    public int inputRoomChanged() {
        return 0;
    }

    public int inputRoomRestricted() {
        return 0;
    }

    public int inputRoomDateExtended() {
        return 0;
    }
}
