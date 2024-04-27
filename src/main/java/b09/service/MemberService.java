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

    public Member enter(PhoneNumber phoneNumber) throws Exception {  // 로그인 화면에서 들어올떄 쓰는 로직 : login + register이라 보시면 편합니다.
        Member member = memberRepository.getMemberByPhoneNumber(phoneNumber);
        if (member == null) {
            throw new Exception("회원이 아닌 전화번호 입니다.");
        }
        return member;
    }

    public void changeMemberRank(Member member, String input) throws Exception{ // member에 rank parameter가 없는데 그 등급이 되기 위한 최소한의 금액을 넣어주세요. 절대 txt 바뀌어야해
        Rank rank = commandToRank(input);
        int minimumSpent;

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
                break;
        }

        Member newMember = new Member(member.getPhoneNumber(), rank.getMinimumSpent(), member.getReservations());
        memberRepository.editMember(member, newMember);
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
