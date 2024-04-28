package b09.repository;

import b09.model.Member;
import b09.model.Reservation;
import b09.model.member.PhoneNumber;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class MemberRepository {
    private static long sequence = 0L; //static
    ArrayList<Member> members = new ArrayList<>();
    public Member getMemberById(Long id) {
        for(Member member : members){
            if(member.getId() == id){
                return member;
            }
        }
        return null;
    }
    public Member getMemberByPhoneNumber(PhoneNumber phoneNumber) {
        for(Member member : members){
            if(member.getPhoneNumber() == phoneNumber){
                return member;
            }
        }
        return null;
    }


    public void fileReader(String filename) {
        try (Scanner file = new Scanner(new File(filename));) {
            int count = 0;
            while (file.hasNextLine()) {
                String str = file.nextLine();
                String[] result = str.split("\t"); // 탭을 기준으로 분리
                ArrayList<Integer> reservationList = new ArrayList<>();
                for(int i = 3; i< result.length; i++){
                    reservationList.add(parseInt(result[i]));
                }
                members.add(new Member(new PhoneNumber(result[1]), parseInt(result[2]), reservationList));
                members.get(count).setId(parseLong(result[0]));
                count++;
            }
        } catch (Exception e) {
            System.out.println("파일을 찾을 수 없습니다.");
        }
    }
    public void editMember(Member memberToBeEdited, Member newMember) {
        // 절대 db(txt)파일 수정해
        // 1: 피 수정자 2: 이 객체의 정보로 바꿔줘

        fileReader("./clientInfo.txt");
        for (int i = 0; i < members.size();i++){
            if(members.get(i).getId() == memberToBeEdited.getId()){
                members.get(i).setTotalMoneySpent(newMember.getTotalMoneySpent());
                members.get(i).setReservations(newMember.getReservations());
            }
        } // 자 여기까지 함으로써 멤버 정보 수정 완료.

        File file = new File("./clientInfo.txt");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./clientInfo.txt"));

            // 아래는 데이터파일 형식에 맞게 멤버 정보 정리 후, 파일에 적는 부분.
            for (Member member : members) {
                String memberInfo = member.getId() + "\t" + member.getPhoneNumber() + "\t" +
                        member.getTotalMoneySpent() + "\t";
                for(int i = 0; i < member.getReservations().size(); i++){
                    memberInfo += member.getReservations().get(i);
                    memberInfo += "\t";
                }
                writer.write(memberInfo + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //ToDO: 1. public List<String[]> readClientsFromFile(String filename)를 호출해서 clientList를 가져옴

        //ToDo: 2. client 리스트에서 수정할 멤버의 id값으로 해당 멤버를 찾음 -> Member targetMember = clientList.get(memberId) 느낌으로

        //ToDO: 3. targetMember의 정보를 수정함

        //ToDo: 4. 기존 clientInfo.txt를 삭제하고 새로 clientInfo.txt.를 만듬 (기존 데이터와 중복을 막기 위해서)

        //ToDO: 5. BufferedWriter writer = new BufferedWriter(new FileWriter("clientInfo.txt"))를 해서 clientInfo.txt와 연결
        /*
        ToDO: 6. clientList에 있는 모든 멤버의 정보를 for를 돌면서  Member객체의 데이터를 <고객index><⇥><전화번호><⇥><누적이용금액><⇥><예약index><⇥> 형식으로
              바꾼뒤 writer.write(멤버정보 문자열)로 file에 다가 씀
        */

        //ToDO 7.: 파일에 모든 멤버 데이터 작성했으면 BufferedWriter writer close하기

    }

}
