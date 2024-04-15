package b09.repository;

import b09.model.Member;
import b09.model.member.PhoneNumber;

public class MemberRepository {
    public Member getMemberById(Long id) {
        return null;
    }
    public Member getMemberByPhoneNumber(PhoneNumber phoneNumber) { // 못찾으면 null 반환
        return null;
    }

    public Member registerMember(PhoneNumber phoneNumber) {
        return null;
    }

    public void editMember(Member memberToBeEdited, Member newMember) {  // 절대 db(txt)파일 수정해
        // 1: 피 수정자 2: 이 객체의 정보로 바꿔줘

    }
}
