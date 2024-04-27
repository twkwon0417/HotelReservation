package b09.model.member;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class PhoneNumber {
    String phoneNumber;

    public PhoneNumber(String phoneNumber) throws Exception {
        // 입력 양식 검토 코드
        if(isManager(phoneNumber)) {
            this.phoneNumber = "MANAGER";
            return;
        }
        validate(phoneNumber);
    }
    private void validate(String phoneNumber) throws Exception{
        if(Pattern.matches("^010\\d{8}$", phoneNumber) || Pattern.matches("^01\\d{7,8}$", phoneNumber)){
            this.phoneNumber = phoneNumber;
        } else{
            throw new Exception("입력양식에 맞지 않는 번호입니다.");
        }
        //ToDo: 정규식 사용!
        // - 1. 010+8자리숫자 -> ^010\d{8}$
        // - 2. 01+7자리숫자 or 8자리숫자 -> ^01\d{7,8}$
        // - 나머지는 취급 No.
    }

    private boolean isManager(String phoneNumber) {
        return Objects.equals(phoneNumber, "B09");
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}