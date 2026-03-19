package UI.Admin.ManageDirectors;

import Util.Pause;
import UI.Common.clsScreen;
import Util.InputVerfication;
public class ManageDirectorsMenuScreen extends clsScreen {
   
        enum enManageDirectorsOptions{
            ADD,
            DELETE,
            UPDATE,
            RETURN
        }
    
    
        private static void _ADD(){
            //ADDfunction
            //ADD_Cast_Screen.ShowAddCastScreen();
            ADD_Director_Screen.ShowAddDirectorScreen(null);
        }
        private static void _DELETE(){
            //DELETEfunction
            DELETE_Director_Screen.ShowDeleteScreen();
        }
        private static void _UPDATE(){
            UPDATE_Director_Screen.ShowUpdateDirectorScreen();
            //UPDATEfunction
        }
        private static void _RETURN(){
    
        }
    
        private static void _ReturnToManageDMenu(){
            Pause.pause("PRESS ANY KEY TO GO BACK TO MANAGE Directors SCREEN\n");
            //call ManageAdminsScreen
            ShowManageDirectorsScreen();
        }
    
        private static enManageDirectorsOptions _MakingaManageChoice(int min, int max){
            System.out.printf("Make a Choice? [%d to %d]?",min,max);
            int choice = InputVerfication.GetNumberBetween(min, max);
            return enManageDirectorsOptions.values()[choice-1];
        }
    
        private static void _PreformManageChoice(enManageDirectorsOptions choice){
            switch(choice){
                case ADD:

                    _ADD();
                    _ReturnToManageDMenu();
                    break;
                case DELETE:

                    _DELETE();
                    _ReturnToManageDMenu();
                    break;
                case UPDATE:

                    _UPDATE();
                    _ReturnToManageDMenu();
                    break;
                case RETURN:

                    _RETURN();
                    break;
    
            }
        }
    
    
        public static void ShowManageDirectorsScreen(){

            clsScreen.DrawScreenHeader("\t\tManage Directors", null);
            String indent = String.format("%-37s", ""); 
        
            System.out.println(indent + "\t[1] ADD Director");
            System.out.println(indent + "\t[2] DELETE Director");
            System.out.println(indent + "\t[3] UPDATE Director");
            System.out.println(indent + "\t[4] RETURN to Admin Main Menu");
            System.out.println(indent + "===========================================");
            _PreformManageChoice(_MakingaManageChoice(1, 4));
    
        }
}