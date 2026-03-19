package UI.Admin.ManageAdmins;
import Util.Pause;
import UI.Common.clsScreen;
import Util.InputVerfication;

public class ManageAdminsMenuScreen extends clsScreen {

    enum enManageAdminsOptions{
        ADD,
        DELETE,
        UPDATE,
        RETURN
    }


    private static void _ADD(){
        //ADDfunction
        ADD_Admin_Screen.ShowAddScreen();
    }
    private static void _DELETE(){
        //DELETEfunction
        DELETE_Admin_Screen.ShowDeleteScreen();
    }
    private static void _UPDATE(){
        UPDATE_Admin_Screen.ShowUpdateAdminScreen();
        //UPDATEfunction
    }
    private static void _RETURN(){

    }

    private static void _ReturnToManageAMenu(){
        Pause.pause("PRESS ANY KEY TO GO BACK TO MANAGE ADMINS SCREEN\n");
        //call ManageAdminsScreen
        ShowManageAdminsScreen();
    }

    private static enManageAdminsOptions _MakingaManageChoice(int min, int max){
        System.out.printf("Make a Choice? [%d to %d]?",min,max);
        int choice = InputVerfication.GetNumberBetween(min, max);
        return enManageAdminsOptions.values()[choice-1];
    }

    private static void _PreformManageChoice(enManageAdminsOptions choice){
        switch(choice){
            case ADD:

                _ADD();
                _ReturnToManageAMenu();
                break;
            case DELETE:

                _DELETE();
                _ReturnToManageAMenu();
                break;
            case UPDATE:

                _UPDATE();
                _ReturnToManageAMenu();
                break;
            case RETURN:

                _RETURN();
                break;

        }
    }


    public static void ShowManageAdminsScreen(){

        clsScreen.DrawScreenHeader("\t\tManage Admins", null);
        String indent = String.format("%-37s", ""); 
    
        System.out.println(indent + "\t[1] ADD Admin");
        System.out.println(indent + "\t[2] DELETE Admin");
        System.out.println(indent + "\t[3] UPDATE Admin");
        System.out.println(indent + "\t[4] RETURN to Admin Main Menu");
        System.out.println(indent + "===========================================");
        _PreformManageChoice(_MakingaManageChoice(1, 4));

    }




}
