package UI.Common;
import java.time.LocalDate;

import session.*;
public class clsScreen {

    protected static void DrawScreenHeader(String title, String subTitle) {

        System.out.println("\t\t\t\t\t______________________________________");
        System.out.println("\n\t\t\t\t\t  " + title);

        if (subTitle != null && !subTitle.isEmpty()) {
            System.out.println("\t\t\t\t\t  " + subTitle);
        }

        System.out.println("\t\t\t\t\t______________________________________\n");

        if (CurrentUser.getCurrentUser() != null) {
            System.out.println("\t\t\t\t\tUser: " + CurrentUser.getCurrentUser().getFullName());
            System.out.println("\t\t\t\t\tMode: " + CurrentUser.getMode());
        }
        else if(CurrentAdmin.getCurrentAdmin()!=null){
            System.out.println("\t\t\t\t\tAdmin: " + CurrentAdmin.getCurrentAdmin().getFullName());
            System.out.println("\t\t\t\t\tMode: " + CurrentAdmin.getMode());
        }

        System.out.println("\t\t\t\t\tDate: " + LocalDate.now());
        System.out.println();
    }


}

