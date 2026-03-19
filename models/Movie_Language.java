package models;

import Util.clsString;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Movie_Language {

    private final static String FileName = "D:\\Watch It - Copy\\Watch It\\src\\models\\Movie_Language.txt";
    private String _Movie_ID;
    private String _Language;

    private static String _ConvertRecordToMovie_LanguageLine(Movie_Language Movie_Language) {
        String Line = "";
        Line += Movie_Language.get_Movie_ID() + "#//#";
        Line += Movie_Language.get_Language();
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

    private static Movie_Language _ConvertLineToMLRecord(String Line, String Delim) {
        List<String> ML = new ArrayList<>();
        ML = clsString.split(Line, Delim);
        return new Movie_Language(
                ML.get(0),
                ML.get(1)
        );
    }

    private static List<Movie_Language> _LoadMovie_LanguagesDataFromFile() {

        List<Movie_Language> ML = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(FileName));

            // Loop from bottom to top
            for (int i = lines.size() - 1; i >= 0; i--) {
                String line = lines.get(i);
                ML.add(_ConvertLineToMLRecord(line, "#//#"));
            }

        } catch (IOException e) {
            System.out.println("something went wrong\n");
        }

        return ML;

    }

    private void _ADD() {
        _AppendDataToFile(_ConvertRecordToMovie_LanguageLine(this));
    }



    public Movie_Language(String Movie_ID,String Language){
        _Movie_ID = Movie_ID;
        _Language  = Language;
    }

    public static void SaveRelations(List<Movie_Language> relations){
        for(Movie_Language mc : relations)
            mc._ADD();   
    }

    public static List<String> GetLanguagesPerMovie(String MovieID){
        List<Movie_Language> MoviesLanguages = _LoadMovie_LanguagesDataFromFile();
        List<String> Languages = new ArrayList<>();
        for(Movie_Language L : MoviesLanguages){
            if(MovieID.equals(L.get_Movie_ID()))
                Languages.add(L.get_Language());
        }
        return Languages;
    }

    public static List<String> getAllLanguges(){
        List<Movie_Language> ml = _LoadMovie_LanguagesDataFromFile();
        List<String> allLanguages = new ArrayList<>();
        for(Movie_Language l : ml){
            if(!allLanguages.contains(l.get_Language()))
                allLanguages.add(l.get_Language());
        }
        return allLanguages;
    }


    public String get_Movie_ID() {
        return _Movie_ID;
    }
    public void set_Movie_ID(String _Movie_ID) {
        this._Movie_ID = _Movie_ID;
    }
    public String get_Language() {
        return _Language;
    }
    public void set_Language(String Language) {
        this._Language = Language;
    }

}