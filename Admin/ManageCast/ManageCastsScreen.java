package UI.Admin.ManageCast;
import Util.Pause;
import UI.Common.clsScreen;
import Util.InputVerfication;
public class ManageCastsScreen extends clsScreen {
   
        enum enManageCastsOptions{
            ADD,
            DELETE,
            UPDATE,
            RETURN
        }
    
    
        private static void _ADD(){
            //ADDfunction
            ADD_Cast_Screen.ShowAddCastScreen();
        }
        private static void _DELETE(){
            //DELETEfunction
            DELETE_Cast_Screen.ShowDeleteScreen();
        }
        private static void _UPDATE(){
            UPDATE_Cast_Screen.ShowUpdateCastScreen();
            //UPDATEfunction
        }
        private static void _RETURN(){
    
        }
    
        private static void _ReturnToManageCMenu(){
            Pause.pause("PRESS ANY KEY TO GO BACK TO MANAGE ADMINS SCREEN\n");
            //call ManageAdminsScreen
            ShowManageCastsScreen();
        }
    
        private static enManageCastsOptions _MakingaManageChoice(int min, int max){
            System.out.printf("Make a Choice? [%d to %d]?",min,max);
            int choice = InputVerfication.GetNumberBetween(min, max);
            return enManageCastsOptions.values()[choice-1];
        }
    
        private static void _PreformManageChoice(enManageCastsOptions choice){
            switch(choice){
                case ADD:

                    _ADD();
                    _ReturnToManageCMenu();
                    break;
                case DELETE:

                    _DELETE();
                    _ReturnToManageCMenu();
                    break;
                case UPDATE:

                    _UPDATE();
                    _ReturnToManageCMenu();
                    break;
                case RETURN:

                    _RETURN();
                    break;
    
            }
        }
    
    
        public static void ShowManageCastsScreen(){

            clsScreen.DrawScreenHeader("\t\tManage Casts", null);
            String indent = String.format("%-37s", ""); 
        
            System.out.println(indent + "\t[1] ADD Cast");
            System.out.println(indent + "\t[2] DELETE Cast");
            System.out.println(indent + "\t[3] UPDATE Cast");
            System.out.println(indent + "\t[4] RETURN to Admin Main Menu");
            System.out.println(indent + "===========================================");
            _PreformManageChoice(_MakingaManageChoice(1, 4));
    
        }


}
