package UI.User;
import Util.S;
import UI.LoginResult;
import UI.Common.AuthService;
import UI.Common.clsScreen;


public class clsUserLoginScreen extends clsScreen {

    

    public static LoginResult ShowLoginScreenForUser() {

        DrawScreenHeader("User Login Screen", null);

        System.out.print("Enter Username: ");
        String username = S.scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = S.scanner.nextLine();

        return AuthService.loginUser(username, password);
    }
}


