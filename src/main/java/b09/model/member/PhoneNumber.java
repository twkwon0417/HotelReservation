package b09.model.member;

import java.util.List;
import java.util.Objects;

public class PhoneNumber {
    String phoneNumber;

    public PhoneNumber(String phoneNumber) throws Exception {
        if(isManager(phoneNumber)) {
            this.phoneNumber = "MANAGER";
            return;
        }
        this.phoneNumber = phoneNumber;
    }

    private void validate(String phoneNumber) throws Exception{

    }

    private boolean isManager(String phoneNumber) {
        return Objects.equals(phoneNumber, "B09");
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
