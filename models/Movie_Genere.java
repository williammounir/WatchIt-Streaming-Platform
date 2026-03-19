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

import Util.clsString;

public class Movie_Genere {
    public static enum genres {
        ACTION,
        DRAMA,
        COMEDY,
        HORROR,
        SCIENCE_FICTION
    }

    private final static String FileName = "D:\\Watch It - Copy\\Watch It\\src\\models\\Movie_Genere.txt";
    private String _Movie_ID;
    private String _Genere;

    private static String _ConvertRecordToMovie_GenereLine(Movie_Genere Movie_Genere) {
        String Line = "";
        Line += Movie_Genere.get_Movie_ID() + "#//#";
        Line += Movie_Genere.get_Genere();
        return Line;
    }

    private static Movie_Genere _ConvertLineToMGRecord(String Line, String Delim) {
        List<String> MG = new ArrayList<>();
        MG = clsString.split(Line, Delim);
        return new Movie_Genere(
            MG.get(0),
            MG.get(1)
        );
    }

    private static void _AppendDataToFile(String Line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName, true))) {
            writer.newLine(); // VERY important
            writer.write(Line);
        } catch (IOException e) {
            System.out.println("Could not write to file: " + FileName);
        }
    }

    private void _ADD() {
        _AppendDataToFile(_ConvertRecordToMovie_GenereLine(this));
    }

    private static List<Movie_Genere> _LoadMovie_GeneresDataFromFile() {

        List<Movie_Genere> MG = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(FileName));

            // Loop from bottom to top
            for (int i = lines.size() - 1; i >= 0; i--) {
                String line = lines.get(i);
                MG.add(_ConvertLineToMGRecord(line, "#//#"));
            }

        } catch (IOException e) {
            System.out.println("something went wrong\n");
        }

        return MG;

    }

    public Movie_Genere(String Movie_ID, String Genere) {
        _Movie_ID = Movie_ID;
        _Genere = Genere;
    }

    public static void SaveRelations(List<Movie_Genere> relations) {
        for (Movie_Genere mc : relations)
            mc._ADD();
    }

    public static Map<String,Integer> MoviePerGenre(){
        Map<String,Integer> m = new HashMap<>();
        for(genres g: genres.values()){
            m.put(g.name(),0);
        }
        List<Movie_Genere> MG = _LoadMovie_GeneresDataFromFile();

        for(Movie_Genere mg : MG){
            m.put(mg.get_Genere(),m.get(mg.get_Genere())+1);
        }    
        return m;    
    }

    public static List<String> GetGenresPerMovie(String MovieID){
        List<Movie_Genere> MoviesGenres = _LoadMovie_GeneresDataFromFile();
        List<String> Genres = new ArrayList<>();
        for(Movie_Genere g : MoviesGenres){
            if(MovieID.equals(g.get_Movie_ID()))
                Genres.add(g.get_Genere());
        }
        return Genres;
    }

    public String get_Movie_ID() {
        return _Movie_ID;
    }

    public void set_Movie_ID(String _Movie_ID) {
        this._Movie_ID = _Movie_ID;
    }

    public String get_Genere() {
        return _Genere;
    }

    public void set_Genere(String Genere) {
        this._Genere = Genere;
    }
}