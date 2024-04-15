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

    public Member enter(PhoneNumber phoneNumber) {  // 로그인 화면에서 들어올떄 쓰는 로직 : login + register이라 보시면 편합니다.
        Member member = memberRepository.getMemberByPhoneNumber(phoneNumber);
        if (member == null) {
            member = memberRepository.registerMember(new Member(
                    phoneNumber,
                    0,
                    new ArrayList<>()
            ));
        }
        return member;
    }

    public void changeMemberRank(Member member, String input) throws Exception{ // member에 rank parameter가 없는데 그 등급이 되기 위한 최소한의 금액을 넣어주세요. 절대 txt 바뀌어야해
        Rank rank = commandToRank(input); // 실실적으로 사용자의 입력값을 처리하는 메서드
        Member newMember = new Member(member.getPhoneNumber(), rank.getMinimumSpent(), member.getReservations());
        memberRepository.editMember(member, newMember);
    }

    private Rank commandToRank(String input) throws Exception{ // 38개 단어들 중 하나가 아니면 Exception을 던지게 해주세요ㅕ.
        return null;
    }

    public Member getByPhoneNumber(PhoneNumber phoneNumber) {
        return memberRepository.getMemberByPhoneNumber(phoneNumber);
    }
}
