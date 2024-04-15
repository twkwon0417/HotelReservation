package b09.controller;

import b09.model.Member;
import b09.model.member.PhoneNumber;
import b09.repository.MemberRepository;
import b09.service.MemberService;
import b09.view.InputView;
import java.util.Objects;

public class HomeController {
    MemberService memberService = new MemberService(new MemberRepository());
    InputView inputView = new InputView();

    public void init() {
        PhoneNumber phoneNumber = inputView.inputPhoneNumber();
        if(phoneNumber == null) {
            int userInput = inputView.inputKillProgram();
            if(userInput == 1) {    // 진짜 종료
                System.exit(0);
            } else if (userInput == 2) {
                init(); // 종료 하지마인 경우
            }
        }
        Member member = memberService.enter(phoneNumber);   //  일단 여기까지녕

        if(Objects.equals(phoneNumber.getPhoneNumber(), "MANAGER")) {
            //
        } else {

        }


    }
}
