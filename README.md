<img width="2370" alt="스크린샷 2024-04-15 오후 9 19 10" src="https://github.com/twkwon0417/HotelReservation/assets/91003152/0f0802de-d6ee-4d9d-b02b-e3c7cc1e1ddf">

## Main -> HomeController -> MangerController -> UserController 순으로 보길 추천
- MangerController쪽이 그나마 깔끔해서 따라가기 상대적으로 편할것 같습니다.
- InteliJ의 이 메서드의 출저가 어딘지, 어디서 쓰이는지 찾아가는 기능들 잘 활용해서 코드 따라가 주시면 감사하겠습니다. 
- Controller 코드, Main 코드 수정 **X**
- public method에 대한 parameter 값과 return값 수정 **X**
- 새로운 메서드, 객체 만드셔도 되는데 만드실때 팀원들한테 알려주세요.

## 흐름도 함꼐 보면서 의미규칙들 고려하면서 짜주세요.
- InputView는 메서드가 재귀로 반복(+검증)이 필요 한건지 더욱 확인해주세요.

## room 패키지는 삭제 가능
- 방 관련된 상수를 저장하려고 만들 파일들인데, 구현 할때 정하는게 나을 것 같습니다.
- 현재 Room.java와 room 패키지 전체는 쓰이지 않습니다. (마음대로 수정 가능 단, 서로 협의 해주세요)


## 제가 놓인 부분 있으면 알려주세요.
- 사용자에게 보여주는 OutputView많이 까먹었을 듯;
- > eg. 관리자 모드에서 예약을 취소 했는데, "예약이 취소되었습니다."를 출력하는 구문이 controller에 없음
- 이왕이면 OutputView 메서드는 parameter를 쓰지 않고 출력 했으면 좋겠습니다. (예약 내역을 확인 하는 부분과 같이 중요한 부분은 제외
- > 01012345678 고객님 예약 취소 완료 되었습니다. (x) <br>
  > 예약 취소 완료 되었습니다. (0)

