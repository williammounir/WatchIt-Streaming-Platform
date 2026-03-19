package UI.User;

import UI.Common.clsScreen;
import Util.InputVerfication;

public class LoginAndSignUp extends clsScreen {
    public enum enChoice{
        LOGIN,
        SIGNUP,
        RENEWSUP,
        RETURN
    }

private static enChoice _MakingAChoice(int FirstChoice, int LastChoice){
    System.out.println("\nEnter a Choice: ");
   return enChoice.values()[InputVerfication.GetNumberBetween(FirstChoice, LastChoice)-1];
}


public static enChoice ShowAuthenticationMenuForUser(){
   
    clsScreen.DrawScreenHeader("\tUSER AUTHENTICATION MENU", null);
    String indent = String.format("%-37s", ""); 
    
    System.out.println(indent + "\t[1] LOGIN");
    System.out.println(indent + "\t[2] SIGN UP");
    System.out.println(indent+  "\t[3] RENEW SUPSCRIPTION");
    System.out.println(indent + "\t[4] RETURN TO PREVIOUS WINDOW");
    
    System.out.println(indent + "===========================================");
    return _MakingAChoice(1, 4);

}


}
