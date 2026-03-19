package UI.Admin.MainAdmin;

import Util.S;
import UI.LoginResult;
import UI.Common.clsScreen;
import UI.Common.AuthService;

public class clsAdminLoginScreen extends clsScreen {

    public static LoginResult ShowLoginScreenForAdmin() {

        DrawScreenHeader("Admin Login Screen", null);

        System.out.print("Enter Username: ");
        String username = S.scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = S.scanner.nextLine();

        return AuthService.loginAdmin(username, password);
    }
}
