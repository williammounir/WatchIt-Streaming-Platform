package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import session.CurrentUser;

import models.clsUser.enSaveResult;

import static Util.clsString.split;

public class clsSubscription {

    private final static String FileName = "D:\\Watch It - Copy\\Watch It\\src\\models\\Subscriptions.txt";

    private enum enMode {
        EmptyMode, UpdateMode, NewMode
    };

    private enMode _mode;

    private String _UserId;
    private enPlan _Plan;

    private double _Price;

    private LocalDate _StartDate;
    private int _CountMovies = 0;

    private static clsSubscription _ConvertLineToSubscriptionRecord(String Line, String Delim) {
        List<String> SubscriptionData = new ArrayList<>();

        SubscriptionData = split(Line, Delim);

        return new clsSubscription(
                enMode.UpdateMode,
                SubscriptionData.get(0), // UserName of user
                enPlan.valueOf(SubscriptionData.get(1)), // Plan
                Double.parseDouble(SubscriptionData.get(2)), // Price
                LocalDate.parse(SubscriptionData.get(3)), // BirthDate
                Integer.parseInt(SubscriptionData.get(4)) // MoviesCount

        );
    }

    private static List<clsSubscription> _LoadSubscriptionDataFromFile() {

        List<clsSubscription> Subscriptions = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(FileName));

            // Loop from bottom to top
            for (int i = lines.size() - 1; i >= 0; i--) {
                String line = lines.get(i);
                Subscriptions.add(_ConvertLineToSubscriptionRecord(line, "#//#"));
            }

        } catch (IOException e) {
            System.out.println("something went wrong\n");
        }

        return Subscriptions;

    }

    private static String _ConvertRecordToUserLine(clsSubscription sub) {
        String Line = "";
        Line += sub.get_UserId() + "#//#";
        Line += sub.get_Plan() + "#//#";
        Line += sub.get_Price() + "#//#";
        Line += sub.get_StartDate().toString() + "#//#";
        Line += sub.getCountMovies();
        return Line;

    }

    private static void _AppendDataToFile(String Line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName, true))) {
            writer.newLine(); // VERY important
            writer.write(Line);
        } catch (IOException e) {
            System.out.println("Could not write to file: " + FileName);
        }
    }

    private void _addNew() {
        _AppendDataToFile(_ConvertRecordToUserLine(this));
    }

    public clsSubscription(enMode mode, String userId, enPlan plan, double price, LocalDate startDate,
            int countMovies) {
        this._mode = mode;
        this._UserId = userId;
        this._Plan = plan;
        this._Price = price;
        this._StartDate = startDate;
        this._CountMovies = countMovies;
    }

    public static clsSubscription findSubscription(String UserName) {
        List<clsSubscription> Subscriptions = _LoadSubscriptionDataFromFile();
        for (int i = 0; i < Subscriptions.size(); i++) {
            if (UserName.equals(Subscriptions.get(i).get_UserId())) {
                return Subscriptions.get(i);
            }
        }
        return null;
    }

    public static clsSubscription GetAddNewObjectSubscription(String UserNameForUser) {
        // prematives like int and double doesn't accept null
        return new clsSubscription(enMode.NewMode, UserNameForUser, null, 0, null, 0);

    }

    public static enum enSaveResult {
        SvFailedEmptyObject, SvSucceded, SvFailedObjectAlreadyExists
    };

    public enSaveResult Save() {
        switch (_mode) {
            case EmptyMode:
                return enSaveResult.SvFailedEmptyObject;
            case UpdateMode:
                // call the update function
                return enSaveResult.SvSucceded;
            case NewMode:
                _addNew();
                _mode = enMode.UpdateMode;
                return enSaveResult.SvSucceded;

        }
        return enSaveResult.SvFailedEmptyObject;
    }

    public static Map<Integer, Integer> RevenuForEachMonth() {
        Map<Integer, Integer> m = new HashMap<>();
        for (Integer i = 1; i <= 12; i++) {
            m.put(i, 0);
        }
        List<clsSubscription> subs = _LoadSubscriptionDataFromFile();
        for (clsSubscription s : subs) {
            Integer month = Integer.valueOf(s.get_StartDate().getMonthValue());
            Integer revenue = (int) s.get_Price();
            m.put(month, m.get(month) + revenue);
        }
        return m;
    }

    public static boolean DidSubscriptionEnded(String UserName) {
        List<clsSubscription> subs = _LoadSubscriptionDataFromFile();
        for (clsSubscription s : subs) {
            if (s.get_UserId().equals(UserName)) {
                if (s.get_StartDate().plusMonths(1).isBefore(LocalDate.now()))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    public static int NumofActiveSup(){
        int Count = 0;
        List<clsSubscription> subs = _LoadSubscriptionDataFromFile();
        for(clsSubscription s : subs){
            if(s.get_EndDate().isAfter(LocalDate.now())){
                Count++;
            }
        }
        return Count;
    }

    public static int SubsStartedThisMonth(){
        int count = 0;
        List<clsSubscription> subs = _LoadSubscriptionDataFromFile();
        for(clsSubscription s : subs){
            if((s.get_StartDate().getMonth().equals(LocalDate.now().getMonth()))&&(s.get_StartDate().getYear() == LocalDate.now().getYear()))
                count++;
        }
        return count;
    }

    public static int SubsExpiredThisMonth(){
        int count = 0;
        List<clsSubscription> subs = _LoadSubscriptionDataFromFile();
        for(clsSubscription s : subs){
            if((s.get_EndDate().getYear() == LocalDate.now().getYear())&&(
                s.get_EndDate().getMonth().equals(LocalDate.now().getMonth()))&&
            (s.get_EndDate().isBefore(LocalDate.now()))){
                count++;
            }
        }
        return count;
    }

    // Getters

    public String get_UserId() {
        return _UserId;
    }

    public LocalDate get_EndDate() {
        LocalDate EndDate = _StartDate.plusMonths(1);
        return EndDate;
    }

    public enPlan get_Plan() {
        return _Plan;
    }

    public void set_Plan(enPlan _Plan) {
        this._Plan = _Plan;
    }

    public void set_StartDate(LocalDate _StartDate) {
        this._StartDate = _StartDate;
    }

    public void set_Price(double _Price) {
        this._Price = _Price;
    }

    public double get_Price() {
        return _Price;
    }

    public LocalDate get_StartDate() {
        return _StartDate;
    }

    public void setCountMovies(int countMovies) {
        _CountMovies = countMovies;
    }

    public int getCountMovies() {
        return _CountMovies;
    }

}
