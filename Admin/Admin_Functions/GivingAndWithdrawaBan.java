package UI.Admin.Admin_Functions;

import java.time.DateTimeException;
import java.time.LocalDate;

import UI.Common.clsScreen;
import Util.InputVerfication;
import Util.S;
import models.clsSuspention;
import models.clsUser;
import session.CurrentAdmin;

public class GivingAndWithdrawaBan extends clsScreen {


    private static LocalDate GetEndDate() {

        LocalDate endDate;

        while (true) {
            try {
                System.out.println("Please Enter End Date of Suspension");

                System.out.print("Enter Year: ");
                int year = InputVerfication.GetNumberBetween(
                        LocalDate.now().getYear(),
                        LocalDate.now().getYear() + 10
                );

                System.out.print("Enter Month: ");
                int month = InputVerfication.GetNumberBetween(1, 12);

                System.out.print("Enter Day: ");
                int day = InputVerfication.GetNumberBetween(1, 31);

                endDate = LocalDate.of(year, month, day);

                if (endDate.isBefore(LocalDate.now())) {
                    System.out.println("End date cannot be before today. Try again.\n");
                    continue;
                }

                return endDate;

            } catch (DateTimeException e) {
                System.out.println("Invalid date entered. Try again.\n");
            }
        }
    }


    public static void ShowSuspentionScreen() {

        clsScreen.DrawScreenHeader("User Suspension", null);

        System.out.print("Enter User ID: ");
        String userID = S.scanner.nextLine().trim();

        while (!clsUser.IsUserExist(userID)) {
            System.out.printf("User with ID %s does not exist. Try again: ", userID);
            userID = S.scanner.nextLine().trim();
        }

        System.out.printf(
                "Do you want to Suspend or Withdraw suspension for User %s? (s / w): ",
                userID
        );

        char choice = InputVerfication.TwoChoices('s', 'w');


        if (choice == 's') {

            if (clsSuspention.IsUserSusbended(userID)) {
                System.out.println("User is already suspended.");
                return;
            }

            clsSuspention suspension = new clsSuspention(
                    userID,
                    CurrentAdmin.getCurrentAdmin().get_UserName(),
                    LocalDate.now(),
                    null,
                    null
            );

            suspension.set_EndDate(GetEndDate());

            System.out.print("Enter Suspension Reason: ");
            String reason = S.scanner.nextLine();
            suspension.setReason(reason);

            if (suspension.SuspendUser()) {
                System.out.printf(
                        "User %s has been suspended successfully.\n",
                        userID
                );
            } else {
                System.out.println("Suspension process failed.");
            }

        }

        else {

            if (!clsSuspention.IsUserSusbended(userID)) {
                System.out.printf(
                        "User %s is not suspended. Cannot withdraw.\n",
                        userID
                );
                return;
            }

            if (clsSuspention.WithdrawSusbention(userID)) {
                System.out.printf(
                        "Suspension for User %s has been withdrawn successfully.\n",
                        userID
                );
            } else {
                System.out.println("Failed to withdraw suspension.");
            }
        }
    }
}
