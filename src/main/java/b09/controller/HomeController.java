package b09.controller;

import b09.model.Member;
import b09.model.member.PhoneNumber;
import b09.repository.MemberRepository;
import b09.service.MemberService;
import b09.view.InputView;

public class HomeController {
    MemberService memberService = new MemberService(new MemberRepository());
    InputView inputView = new InputView();

    public Member init() {
        PhoneNumber phoneNumber = null;
        try {
            phoneNumber = inputView.inputPhoneNumber();
        } catch (Exception e) {
            return null;
        }
        if (phoneNumber == null) {  // q를 누른 경우임
            int userInput = inputView.inputKillProgram();
            if (userInput == 1) {    // 진짜 종료
                System.exit(0);
            } else if (userInput == 2) {
                init();
            }
        }
        Member member;
        try {
            member = memberService.enter(phoneNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return init();
        }

        return member;
    }
}
