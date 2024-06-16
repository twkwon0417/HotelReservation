package b09.service;

import b09.model.Member;
import b09.model.member.PhoneNumber;
import b09.model.member.Rank;
import b09.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberRepository getMemberRepository(){
        return this.memberRepository;
    }
    public Member enter(PhoneNumber phoneNumber) throws Exception {  // 로그인 화면에서 들어올떄 쓰는 로직 : login + register이라 보시면 편합니다.
        Member member = memberRepository.getMemberByPhoneNumber(phoneNumber);
        if (member == null) {
            throw new Exception("회원이 아닌 전화번호 입니다.");
        }
        return member;
    }

    public void changeMemberRank(Member member, String input) throws Exception {
        Rank rank = commandToRank(input);
        int minimumSpent = 0;
        Rank memberNowRank = member.getRank();
        if(memberNowRank.equals(rank)){
            System.out.println("바꾸려는 등급과 현재 등급이 같습니다.");
            return;
        }
        switch (rank) {
            case BRONZE:
                minimumSpent = 0;
                break;
            case SILVER:
                minimumSpent = 200000;
                break;
            case GOLD:
                minimumSpent = 500000;
                break;
            case PLATINUM:
                minimumSpent = 800000;
                break;
            case VIP:
                minimumSpent = 1000000;
                break;
            default:
                throw new IllegalArgumentException("Invalid rank: " + rank);
        }

        // 회원의 최소 이용 금액 변경
        member.setTotalMoneySpent(minimumSpent);

        // 변경 사항을 파일에 저장
        memberRepository.updateFile();
        System.out.println("등급 수정이 완료 되었습니다.");
    }


    private Rank commandToRank(String input) throws Exception{ // 38개 단어들 중 하나가 아니면 Exception을 던지게 해주세요ㅕ.
        switch (input) {
            case ".":
            case "b":
            case "br":
            case "bro":
            case "bron":
            case "bronz":
            case "bronze":
            case "동":
                return Rank.BRONZE;
            case "/":
            case "s":
            case "si":
            case "sil":
            case "silv":
            case "silve":
            case "silver":
            case "은":
                return Rank.SILVER;
            case "!":
            case "g":
            case "go":
            case "gol":
            case "gold":
            case "금":
                return Rank.GOLD;
            case "+":
            case "p":
            case "pl":
            case "pla":
            case "plat":
            case "plati":
            case "platin":
            case "platinu":
            case "platinum":
            case "백금":
                return Rank.PLATINUM;
            case "-":
            case "v":
            case "vi":
            case "vip":
            case "왕":
                return Rank.VIP;
            default:
                throw new Exception("잘못된 등급입니다.");
        }
    }

    public Member getByPhoneNumber(PhoneNumber phoneNumber) {
        return memberRepository.getMemberByPhoneNumber(phoneNumber);
    }
}