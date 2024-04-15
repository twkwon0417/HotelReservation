package b09.model.room;

public interface RoomStatus {   // room status true 인 경우 : 사용 가능 (사용 중인지 아닌지는 모름), false인 경우 : 사용불가
    boolean PROHIBITED = true;
    boolean NOT_PROHIBITED = false;
}
