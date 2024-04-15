package b09.service;

import b09.model.Member;
import b09.model.member.PhoneNumber;
import b09.model.member.Rank;
import b09.repository.MemberRepository;
import java.util.List;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member enter(PhoneNumber phoneNumber) {  // 로그인 화면에서 들어올떄 쓰는 로직 : login + register이라 보시면 편합니다.
        Member member = memberRepository.getMemberByPhoneNumber(phoneNumber);
        if (member == null) {
            member = memberRepository.registerMember(phoneNumber);
        }
        return member;
    }

    public void changeMemberRank(Member member, Rank rank) {
        Member newMember = new Member(member.getPhoneNumber(), rank.getMinimumSpent(), member.getReservations());
        memberRepository.editMember(member, newMember);
    }

}
