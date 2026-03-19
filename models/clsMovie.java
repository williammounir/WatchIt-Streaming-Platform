package models;

import static Util.clsString.split;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Util.clsString;

public class clsMovie implements Comparable<clsMovie> {

    private final static String FileName = "D:\\Watch It - Copy\\Watch It\\src\\models\\Movies.txt";

    @Override
    public int compareTo(clsMovie o) {
        return (int) (o.getRating() - this.getRating());
    }

    private enum enMode {
        EmptyMode, UpdateMode, NewMode
    };

    private enMode _mode;
    private String _id;
    private String _title;
    private LocalDate _releaseDate;
    private int _durationMinutes;
    // CIRCULAR DEPENDANCY
    // private List<String> _cast;
    // multivalued atribute
    // private List<String> _genres;
    private String _director;
    // multivalued atribute
    // private List<String> _languages;
    private String _country;
    private double _budget;
    private double _revenue;
    // calculated every time user make a rating
    private double _rating;
    private String _posterPath;
    private String _VideoPath;
    private String _Description;
    private boolean _Marked_For_Delete = false;

    private static clsMovie _ConvertLineToMovieRecord(String Line, String Delim) {
        List<String> MovieData = new ArrayList<>();
        MovieData = clsString.split(Line, Delim);
        // CIRCULAR DEPENDANCY
        // List<String> cast = split(MovieData.get(4),"-");
        // multivalued atribute
        // List<String> Generes = split(MovieData.get(4),"-");
        // List<String> languages = split(MovieData.get(6),"-");
        return new clsMovie(
                enMode.UpdateMode,
                MovieData.get(0), // ID
                MovieData.get(1), // Title
                LocalDate.parse(MovieData.get(2)), // realase date
                Integer.parseInt(MovieData.get(3)), // DuarationMinutes
                // CIRCULAR DEPENDANCY
                // cast, // cast ids
                // multivalued atribute
                // Generes, // Genres
                MovieData.get(4), // Director
                // multivalued atribute
                // languages, // Languages
                MovieData.get(5), // Country
                Double.parseDouble(MovieData.get(6)), // budget
                Double.parseDouble(MovieData.get(7)), // Revenue
                Double.parseDouble(MovieData.get(8)), // Rating
                MovieData.get(9),
                MovieData.get(10), // PosterPath
                MovieData.get(11)  //MoviePath

        );
        // Integer.parseInt(CastData.get(7)));

    }

    private void _Update() {

        List<clsMovie> movies = _LoadMoviesDataFromFile();

        for (int i = 0; i < movies.size(); i++) {
            if (this.get_id().equals(movies.get(i).get_id())) {
                movies.set(i, this);
                break;
            }
        }

        _SaveMovieDataToFile(movies);
    }
    private static String _ConvertRecordToMovieLine(clsMovie Movie) {
        String Line = "";
        Line += Movie.getId() + "#//#";
        Line += Movie.getTitle() + "#//#";
        Line += Movie.getReleaseDate().toString() + "#//#";
        Line += String.valueOf(Movie.getDurationMinutes()) + "#//#";
        // CIRCULAR DEPENDANCY
        /*
         * int CastSize = Movie.getCast().size();
         * for(int i = 0 ; i<CastSize;i++){
         * Line+= Movie.getCast().get(i);
         * if(i<CastSize - 1)
         * Line+='-';
         * }
         * Line +="#//#";
         */

        // multivalued atribute
        /*
         * int GeneresSize = Movie.getGenres().size();
         * for(int i = 0 ; i<GeneresSize;i++){
         * Line+= Movie.getGenres().get(i);
         * if(i<GeneresSize - 1)
         * Line+='-';
         * }
         * Line +="#//#";
         * 
         * 
         * int LanguagesSize = Movie.getLanguages().size();
         * for(int i = 0 ; i<LanguagesSize;i++){
         * Line+= Movie.getLanguages().get(i);
         * if(i<LanguagesSize - 1)
         * Line+='-';
         * }
         * Line +="#//#";
         */
        Line += Movie.getDirector() + "#//#";
        Line += Movie.getCountry() + "#//#";
        Line += String.valueOf(Movie.getBudget()) + "#//#";
        Line += String.valueOf(Movie.getRevenue()) + "#//#";
        Line += String.valueOf(Movie.getRating()) + "#//#";
        Line += Movie.get_Description() + "#//#";
        Line += Movie.getPosterPath() + "#//#";
        Line += Movie.getVideoPath();
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

    private void _ADD() {
        _AppendDataToFile(_ConvertRecordToMovieLine(this));
    }

    private static List<clsMovie> _LoadMoviesDataFromFile() {

        List<clsMovie> Movies = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(FileName));

            // Loop from bottom to top
            for (int i = lines.size() - 1; i >= 0; i--) {
                String line = lines.get(i);
                Movies.add(_ConvertLineToMovieRecord(line, "#//#"));
            }

        } catch (IOException e) {
            System.out.println("something went wrong\n");
        }

        return Movies;

    }

    private static void _SaveMovieDataToFile(List<clsMovie> movies) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))) {

            boolean firstLine = true;

            for (clsMovie movie : movies) {
                if (!movie._Marked_For_Delete) {

                    if (!firstLine) {
                        writer.newLine();
                    }

                    writer.write(_ConvertRecordToMovieLine(movie));
                    firstLine = false;
                }
            }

        } catch (IOException e) {
            System.out.println("Could not write to file: " + FileName);
        }
    }

    public clsMovie(enMode mode, String id, String title, LocalDate releaseDate, int durationMinutes,
            String director, String country,
            double budget, double revenue, double rating,String Description, String PosterPath, String VideoPath) {
        _mode = mode;
        _id = id;
        _title = title;
        _releaseDate = releaseDate;
        _durationMinutes = durationMinutes;
        // CIRCULAR DEPENDANCY
        /*
         * if(cast != null){
         * _cast = cast;
         * }else{
         * _cast = new ArrayList<>();
         * }
         */
        // multivalued atribute
        /*
         * if(genres != null){
         * _genres = genres;
         * }else{
         * _genres = new ArrayList<>();
         * }
         * 
         * if(languages != null){
         * _languages = languages;
         * }else{
         * _languages = new ArrayList<>();
         * }
         */
        _director = director;
        _country = country;
        _budget = budget;
        _revenue = revenue;
        _rating = rating;
        _Description = Description;
        _posterPath = PosterPath;
        _VideoPath = VideoPath;

    }

    public static clsMovie FindMovie(String Id) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FileName))) {
            String line;
            while ((line = reader.readLine()) != null) {

                clsMovie Movie = _ConvertLineToMovieRecord(line, "#//#");
                if (Movie.get_id().equals(Id))
                    return Movie;

            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not locate file\n");

        } catch (IOException e) {
            System.out.println("something went wrong\n");

        }

        return null;

    }

    public static boolean IsMovieExists(String Id) {
        if (FindMovie(Id) != null)
            return true;
        else
            return false;
    }

    public static clsMovie GetAddNewObjectMovie(String ID) {
        return new clsMovie(enMode.NewMode, ID, null, null, 0, null, null, 0d, 0d, 0d,null, null, null);
    }

    public enum enSaveResult {
        SVSUCCEDED,
        SvFailedEmptyObject,
        SvFailedObjectAlreadyExists
    }

    public enSaveResult SaveM() {
        switch (_mode) {
            case EmptyMode:
                return enSaveResult.SvFailedEmptyObject;

            case NewMode:
                if (IsMovieExists(_id))
                    return enSaveResult.SvFailedObjectAlreadyExists;
                else {
                    _ADD();
                    return enSaveResult.SVSUCCEDED;
                }
            case UpdateMode:
                _Update();
                return enSaveResult.SVSUCCEDED;

            default:
                return enSaveResult.SvFailedEmptyObject;

        }
    }

    public boolean DELETEM() {
        List<clsMovie> Movies = new ArrayList<>();
        Movies = _LoadMoviesDataFromFile();
        for (clsMovie movie : Movies) {
            if (movie._id.equals(this.getId())) {
                movie._Marked_For_Delete = true;
                break;

            }
        }
        _SaveMovieDataToFile(Movies);
        return true;
    }

    public static int CountofMovies(){
        return _LoadMoviesDataFromFile().size();
    }
    public static List<clsMovie> GetAllMovies(){
        return _LoadMoviesDataFromFile();
    }

    public static List<clsMovie> GetAllMoviesPerGenere(Movie_Genere.genres g){
        List<clsMovie>MoviesPergenere = new ArrayList<>();
        for(clsMovie m : GetAllMovies()){
            if(m.getGenres().contains(g.toString()))
                MoviesPergenere.add(m);
        }
        return MoviesPergenere;
    }

    public enMode get_mode() {
        return _mode;
    }

    public String get_id() {
        return _id;
    }

    // ===== Getter for ID only (no setter) =====
    public String getId() {
        return _id;
    }

    // ===== Getters & Setters =====

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public LocalDate getReleaseDate() {
        return _releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this._releaseDate = releaseDate;
    }

    public int getDurationMinutes() {
        return _durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this._durationMinutes = durationMinutes;
    }

    public String getDirector() {
        return _director;
    }
    public String getDirectorName(){
        return clsDirector.FindDirector(_director).getFullName();
    }

    public void setDirector(String director) {
        this._director = director;
    }

    public String getCountry() {
        return _country;
    }

    public void setCountry(String country) {
        this._country = country;
    }

    public double getBudget() {
        return _budget;
    }

    public void setBudget(double budget) {
        this._budget = budget;
    }

    public double getRevenue() {
        return _revenue;
    }

    public void setRating(double rating) {
        this._rating = rating;
    }

    public double getRating() {
        return _rating;
    }

    public void setRevenue(double revenue) {
        this._revenue = revenue;
    }

    public String getPosterPath() {
        return _posterPath;
    }

    public String get_Description() {
        return _Description;
    }

    public void set_Description(String Description) {
        this._Description = Description;
    }

    public void setPosterPath(String posterPath) {
        this._posterPath = posterPath;
    }

    public void setVideoPath(String VideoPath) {
        this._VideoPath = VideoPath;
    }

    public String getVideoPath() {
         return _VideoPath;
    }
    public  List<String> getGenres(){
        return Movie_Genere.GetGenresPerMovie(this._id);
    }

    public  List<String> getLanguages(){
        return Movie_Language.GetLanguagesPerMovie(this._id);
    }

    public  List<String> getCasts(){
        return clsMovie_Cast.GetCastPerMovie(this._id);
    }
}
