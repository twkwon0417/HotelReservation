package b09;

import b09.controller.HomeController;
import b09.controller.ManagerController;
import b09.controller.UserController;
import b09.model.Member;

public class Main {
    public static void main(String[] args) {
        HomeController homeController = new HomeController();
        ManagerController managerController = new ManagerController();
        UserController userController = new UserController();

        while(true) {
            Member member = homeController.init();  // kill (전체 프로그램) 하는 코드는 여기 안에서 다룰고 있음

            if (member == null) {
                managerController.initMain();
            } else {
                userController.initMain(member);
            }
        }
    }
}