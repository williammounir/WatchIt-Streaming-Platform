package UI.Common;

import models.clsUser;
import models.clsAdmin;
import models.clsSuspention;
import models.clsSubscription;
import session.CurrentUser;
import session.CurrentAdmin;
import UI.LoginResult;

public class AuthService {

    private static int userTrials = 3;
    private static int adminTrials = 3;

    // ================= USER LOGIN =================
    public static LoginResult loginUser(String username, String password) {

        clsUser user = clsUser.FindUser(username, password);

        if (user == null) {
            userTrials--;
            if (userTrials == 0)
                return LoginResult.LOCKED_OUT;

            return LoginResult.INVALID_CREDENTIALS;
        }

        if (clsSuspention.IsUserSusbended(username))
            return LoginResult.ACCOUNT_SUSPENDED;

        if (clsSubscription.DidSubscriptionEnded(username))
            return LoginResult.SUBSCRIPTION_EXPIRED;

        CurrentUser.setCurrentUser(user);
        userTrials = 3; // reset
        return LoginResult.SUCCESS;
    }

    // ================= ADMIN LOGIN =================
    public static LoginResult loginAdmin(String username, String password) {

        clsAdmin admin = clsAdmin.FindAdmin(username, password);

        if (admin == null) {
            adminTrials--;
            if (adminTrials == 0)
                return LoginResult.LOCKED_OUT;

            return LoginResult.INVALID_CREDENTIALS;
        }

        CurrentAdmin.setCurrentAdmin(admin);
        adminTrials = 3; 
        return LoginResult.SUCCESS;
    }
}
