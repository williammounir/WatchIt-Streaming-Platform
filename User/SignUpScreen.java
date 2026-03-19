package UI.User;


import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import Util.InputVerfication;
import UI.Common.clsScreen;
import Util.S;


import models.clsSubscription;
import models.clsUser;
import models.enPlan;

public class SignUpScreen extends clsScreen {
    // public clsUser(enMode mode,String UserName,String firstName, String
    // lastName,LocalDate BirthDate, String Gender, String email,
    // String Password,List<String>WatchLaterMovies
    public static enum enSignupResult {
        UNSUCCESSFULL, SUCCESS
    };

    private static void _ReadUserData(clsUser NewUser) {
        System.out.print("Please Enter your FirstName: ");
        NewUser.set_FirstName(S.scanner.nextLine().trim());
        System.out.print("Please Enter your LastName: ");
        NewUser.set_LastName(S.scanner.nextLine().trim());
        System.out.print("Please Enter your BirthData\n");
        System.out.print("Enter year: ");
        int year = Integer.parseInt(S.scanner.nextLine().trim());

        System.out.print("Enter month ");
        int month = InputVerfication.GetNumberBetween(1, 12);
        System.out.print("Enter Day: ");
        List<Integer> thirtyonedays = Arrays.asList(1, 4, 5, 7, 8, 10, 12);
        List<Integer> thirtydays = Arrays.asList(4, 6, 9, 11);
        int day;
        if (thirtydays.contains(month)) {
            day = InputVerfication.GetNumberBetween(1, 31);
        } else if (thirtyonedays.contains(month)) {
            day = InputVerfication.GetNumberBetween(1, 30);
        } else {
            boolean isLeap = Year.isLeap(year);
            if (isLeap) {
                day = InputVerfication.GetNumberBetween(1, 29);
            } else {
                day = InputVerfication.GetNumberBetween(1, 28);
            }
        }

        LocalDate date = LocalDate.of(year, month, day);
        NewUser.set_BirthDate(date);
        System.out.print("Please Enter your Gender: ");
        NewUser.set_Gender(S.scanner.nextLine().trim());
        System.out.print("Please Enter your Email: ");
        NewUser.set_Email(S.scanner.nextLine().trim());
        System.out.print("Please Enter your Password: ");
        NewUser.set_Password(S.scanner.nextLine().trim());
        //NewUser.set_subscription(null);

    }

    private static void _ReadSubscription(clsSubscription NewSubs) {
        // maybe at the future we have to add an asurnece for payment
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

    private static void _PrintData(clsUser User, clsSubscription sub) {
        System.out.println("The Information of The New User Added is As Follows: \n");
        System.out.printf("UserName: %s\n", User.get_UserName());
        System.out.printf("FullName: %s\n", User.getFullName());
        System.out.printf("Email: %s\n", User.get_Email());
        System.out.printf("BirthDate: %s%n", User.get_BirthDate());
        System.out.printf("Gender: %s\n", User.get_Gender());
        System.out.printf("Plan: %s\n", sub.get_Plan());
        System.out.printf("Start Date Of The Plan: %s%n\n\n", sub.get_StartDate());
    }

    public static enSignupResult ShowSignUpScreen() {
        DrawScreenHeader("\tSIGN UP SCREEN", null);
        System.out.print("\nPlease Enter UserName: ");
        String UserName = S.scanner.nextLine().trim();
        while (clsUser.IsUserExist(UserName)) {
            System.out.print("USERNAME ALREADY EXISTS.....,TRY AGAIN\n\n");
            UserName = S.scanner.nextLine().trim();
        }

        clsUser NewUser = clsUser.GetAddNewObject(UserName);
        _ReadUserData(NewUser);
        // CALLING THE FUNCTION TO READ SUBSCRIPTION

        clsSubscription NewSub = clsSubscription.GetAddNewObjectSubscription(UserName);
        _ReadSubscription(NewSub);
        clsUser.enSaveResult SaveResult;
        SaveResult = NewUser.Save();
        switch (SaveResult) {
            case SvFailedEmptyObject:
                System.out.println("Save in file failed Empty Object :(");
                return enSignupResult.UNSUCCESSFULL;

            case SvSucceded:
                System.out.println("User Has Been Created Successfully :)\n\n");
                _PrintData(NewUser, NewSub);
                NewSub.Save();
                return enSignupResult.SUCCESS;

            case SvFailedObjectAlreadyExists:
                System.out.println("UserName Already Exists, Try Again Another Time\n");
                return enSignupResult.UNSUCCESSFULL;
            default:
                return enSignupResult.UNSUCCESSFULL;

        }

    }

}
