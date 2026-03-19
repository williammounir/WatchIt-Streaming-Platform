package UI.Common;

import UI.LoginResult;
import UI.Admin.MainAdmin.clsAdminLoginScreen;
import UI.User.clsUserLoginScreen;
import UI.User.LoginAndSignUp;
import UI.User.SignUpScreen;
import UI.User.RenewSupscriptionScreen;
import Util.Pause;
import Util.S;

import static Util.clsString.lowerAll;

public class clsMainScreen extends clsScreen {

    public enum ScreenFlow {
        GO_TO_ADMIN_MENU,
        GO_TO_USER_MENU,
        EXIT_APP,
        STAY_IN_LOOP
    }

    public static ScreenFlow show() {

  
        DrawScreenHeader("Main Screen", null);

        System.out.println("Are you an Admin or a User?");
        System.out.print("[A] Admin   [U] User   [E] Exit : ");

        String choice = lowerAll(S.scanner.nextLine().trim());

        switch (choice) {

            case "a": {
                LoginResult result = clsAdminLoginScreen.ShowLoginScreenForAdmin();
                return handleAdminResult(result);
            }

            case "u": {
                LoginAndSignUp.enChoice userChoice =
                LoginAndSignUp.ShowAuthenticationMenuForUser();

                return handleUserChoice(userChoice);
            }

            case "e":
                return ScreenFlow.EXIT_APP;

            default:
                System.out.println("Invalid choice");
                Pause.pause("press any key...");
                return ScreenFlow.STAY_IN_LOOP;
        }
    }

    private static ScreenFlow handleAdminResult(LoginResult result) {

        switch (result) {
            case SUCCESS:
                System.out.println("Admin login successful");
                Pause.pause("Press any key to continue...");
                return ScreenFlow.GO_TO_ADMIN_MENU;

            case INVALID_CREDENTIALS:
                System.out.println("Invalid admin credentials");
                Pause.pause("press any key..");
                return ScreenFlow.STAY_IN_LOOP;

            case LOCKED_OUT:
                System.out.println("Too many attempts. Locked out.");
                return ScreenFlow.EXIT_APP;

            default:
                return ScreenFlow.STAY_IN_LOOP;
        }
    }

    private static ScreenFlow handleUserChoice(LoginAndSignUp.enChoice choice) {

        switch (choice) {

            case LOGIN:
                return handleUserLogin();

            case SIGNUP:
                SignUpScreen.enSignupResult signupResult =
                        SignUpScreen.ShowSignUpScreen();

                if (signupResult == SignUpScreen.enSignupResult.SUCCESS)
                    System.out.println("Signup successful");

                Pause.pause("press any key");
                return ScreenFlow.STAY_IN_LOOP;

            case RENEWSUP:
                RenewSupscriptionScreen.ShowRenewSupscriptionScreen();
                return ScreenFlow.STAY_IN_LOOP;

            case RETURN:
                return ScreenFlow.STAY_IN_LOOP;

            default:
                return ScreenFlow.STAY_IN_LOOP;
        }
    }

    private static ScreenFlow handleUserLogin() {

        LoginResult result = clsUserLoginScreen.ShowLoginScreenForUser();

        switch (result) {
            case SUCCESS:
                System.out.println("User login successful");
                Pause.pause("Press any key to continue...");
                return ScreenFlow.GO_TO_USER_MENU;

            case INVALID_CREDENTIALS:
                System.out.println("Invalid username or password");
                Pause.pause("press any key");
                return ScreenFlow.STAY_IN_LOOP;

            case ACCOUNT_SUSPENDED:
                System.out.println("Account is suspended");
                return ScreenFlow.STAY_IN_LOOP;

            case SUBSCRIPTION_EXPIRED:
                System.out.println("Subscription expired. Please renew.");
                return ScreenFlow.STAY_IN_LOOP;

            case LOCKED_OUT:
                System.out.println("Too many attempts. Locked out.");
                return ScreenFlow.EXIT_APP;

            default:
                return ScreenFlow.STAY_IN_LOOP;
        }
    }
}
