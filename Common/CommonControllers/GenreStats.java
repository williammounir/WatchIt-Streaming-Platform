package UI.Common.CommonControllers;

import javafx.beans.property.*;

public class GenreStats {

    private StringProperty genre;
    private IntegerProperty count;

    public GenreStats(String genre, int count) {
        this.genre = new SimpleStringProperty(genre);
        this.count = new SimpleIntegerProperty(count);
    }

    public StringProperty genreProperty(){
        return genre;
    }

    public IntegerProperty countProperty(){
        return count;
    }
}
