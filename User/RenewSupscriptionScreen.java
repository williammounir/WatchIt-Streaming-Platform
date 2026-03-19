package UI.User;

import java.time.LocalDate;

import UI.Common.clsScreen;
import Util.Pause;
import Util.S;
import models.clsSubscription;
import models.clsSubscription.enSaveResult;
import models.clsUser;
import models.enPlan;


public class RenewSupscriptionScreen extends clsScreen {

    private static void ReadNewSup(clsSubscription NewSubs){
        System.out.println("BASIC: 50 EGP");
        System.out.println("STANDARD: 100 EGP");
        System.out.println("PREMIUM: 150 EGP");
        System.out.println("Please Choose one of those plans (BASIC,STANDARD,PREMIUM)");

        boolean Exit = false;
        // default value for managing error
        enPlan p = enPlan.BASIC;
        do {
            System.out.print("Enter Plan Choice: ");
            String plan = S.scanner.nextLine().trim();
            try {
                p = enPlan.valueOf(plan.toUpperCase());
                Exit = true;

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Plan Name..., Try Again.");

            }

        } while (!Exit);

        NewSubs.set_Plan(p);
        switch (p) {
            case BASIC:
                NewSubs.set_Price(50);
                break;
            case STANDARD:
                NewSubs.set_Price(100);
                break;
            case PREMIUM:
                NewSubs.set_Price(150);
                break;

        }
        NewSubs.set_StartDate(LocalDate.now());
    }

    public static void ShowRenewSupscriptionScreen(){
        clsScreen.DrawScreenHeader("Renew Supscription", null);
        System.out.print("Please Enter Your Username: ");
        String UserName = S.scanner.nextLine();
        System.out.print("Please Enter Your Password: ");
        String Password = S.scanner.nextLine();
        while(clsUser.FindUser(UserName, Password) == null){
            System.out.println("YOUR USERNAME OR PASSWORD ISN'T RIGHT, TRY AGAIN");
            System.out.print("Please Enter Your Username: ");
            UserName = S.scanner.nextLine();
            System.out.print("Please Enter Your Password: ");
            Password = S.scanner.nextLine();
        }
        clsSubscription NewSup = clsSubscription.GetAddNewObjectSubscription(UserName);
        ReadNewSup(NewSup);
        enSaveResult result = NewSup.Save();
        switch(result){
            case SvSucceded:
                System.out.println("Congrats you have renewed your subscription!! :-)");
                Pause.pause("Press any key to go back to main menu...");
                break;
            case SvFailedObjectAlreadyExists:
                System.out.println("SAVE FAILED OBJECT ALREADY EXISTS");    
                break;
        }



    }

}
