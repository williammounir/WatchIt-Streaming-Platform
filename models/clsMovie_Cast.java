package models;

import Util.clsString;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class clsMovie_Cast {

    private final static String FileName = "D:\\Watch It - Copy\\Watch It\\src\\models\\Movie_Cast.txt";
    private String _Movie_ID;
    private String _Cast_ID;

    private static String _ConvertRecordToMovie_CastLine(clsMovie_Cast Movie_Cast) {
        String Line = "";
        Line += Movie_Cast.get_Movie_ID() + "#//#";
        Line += Movie_Cast.get_Cast_ID();
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

    private static clsMovie_Cast _ConvertLineToMCRecord(String Line, String Delim) {
        List<String> MC = new ArrayList<>();
        MC = clsString.split(Line, Delim);
        return new clsMovie_Cast(
                MC.get(0),
                MC.get(1)
        );
    }

    private static List<clsMovie_Cast> _LoadMovie_CastDataFromFile() {

        List<clsMovie_Cast> ML = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(FileName));

            // Loop from bottom to top
            for (int i = lines.size() - 1; i >= 0; i--) {
                String line = lines.get(i);
                ML.add(_ConvertLineToMCRecord(line, "#//#"));
            }

        } catch (IOException e) {
            System.out.println("something went wrong\n");
        }

        return ML;

    }

    private void _ADD() {
        _AppendDataToFile(_ConvertRecordToMovie_CastLine(this));
    }



    public clsMovie_Cast(String Movie_ID,String Cast_ID){
        _Movie_ID = Movie_ID;
        _Cast_ID  = Cast_ID;
    }

    public static void SaveRelations(List<clsMovie_Cast> relations){
        for(clsMovie_Cast mc : relations)
            mc._ADD();   
    }

    public static List<String> GetCastPerMovie(String MovieID){
        List<clsMovie_Cast> MoviesCast = _LoadMovie_CastDataFromFile();
        List<String> Casts = new ArrayList<>();
        for(clsMovie_Cast C : MoviesCast){
            if(MovieID.equals(C.get_Movie_ID())){
                clsCast cast = clsCast.FindCast(C.get_Cast_ID());
                Casts.add(cast.getFullName());
            }
        }
        return Casts;
    }


    public String get_Movie_ID() {
        return _Movie_ID;
    }
    public void set_Movie_ID(String _Movie_ID) {
        this._Movie_ID = _Movie_ID;
    }
    public String get_Cast_ID() {
        return _Cast_ID;
    }
    public void set_Cast_ID(String _Cast_ID) {
        this._Cast_ID = _Cast_ID;
    }
}
