package b09.repository;

import b09.model.Member;
import b09.model.Reservation;
import b09.model.member.PhoneNumber;
import b09.model.reservation.AdditionalProduct;
import b09.model.reservation.NumberOfPeople;
import b09.model.reservation.ReservedDate;
import b09.model.reservation.RoomNumber;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class MemberRepository {
    public MemberRepository() {
        fileReader("./clientInfo.txt");
    }

    private static long sequence = 0L; //static
    ArrayList<Member> members = new ArrayList<>();
    public Member getMemberById(Long id) {
        for(Member member : members){
            if(Objects.equals(member.getId(), id)){
                return member;
            }
        }
        return null;
    }
    public Member getMemberByPhoneNumber(PhoneNumber phoneNumber) {
        for(Member member : members){
            if(Objects.equals(member.getPhoneNumber().getPhoneNumber(), phoneNumber.getPhoneNumber())){
                return member;
            }
        }
        return null;
    }


    public void fileReader(String filename) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\t");
                if (parts.length >= 3) {
                    long memberId = Long.parseLong(parts[0]);
                    PhoneNumber phoneNumber = new PhoneNumber(parts[1]);
                    int totalMoneySpent = Integer.parseInt(parts[2]);
                    ArrayList<Integer> reservationList = new ArrayList<>();
                    for (int i = 3; i < parts.length; i++) {
                        reservationList.add(Integer.parseInt(parts[i]));
                    }
                    Member member = new Member(phoneNumber, totalMoneySpent, reservationList);
                    member.setId(memberId);
                    members.add(member);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("파일을 찾을 수 없습니다: " + filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void updateFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("./clientInfo.txt"))) {
            for (Member member : members) {
                StringBuilder memberInfo = new StringBuilder();
                memberInfo.append(member.getId()).append("\t")
                        .append(member.getPhoneNumber().getPhoneNumber()).append("\t")
                        .append(member.getTotalMoneySpent()).append("\t");

                List<Integer> reservations = member.getReservations();
                for (int i = 0; i < reservations.size(); i++) {
                    memberInfo.append(reservations.get(i));
                    if (i < reservations.size() - 1) {
                        memberInfo.append("\t");
                    }
                }

                out.write(memberInfo.toString());
                out.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일을 업데이트하는데 실패했습니다.");
        }
    }


}
