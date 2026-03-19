package UI.Admin.Admin_Functions;

import java.util.Map;

import UI.Common.clsScreen;
import Util.Pause;
import models.Movie_Genere;
import models.clsMovie;
import models.clsSubscription;
import models.clsUser;

public class SystemStatistics extends clsScreen {
    
    private static int GetTotalUsers(){
        return clsUser.CountUsers();
    }

    private static int GetNumberofAciveSub(){
        return clsSubscription.NumofActiveSup();
    }

    private static int GetTotalMovies(){
        return clsMovie.CountofMovies();
    }

    private static Map<String,Integer> GetMoviePerGenre(){
        return Movie_Genere.MoviePerGenre();
    }

    private static int GetSubscriptionsSartedThisMonth(){
        return clsSubscription.SubsStartedThisMonth();
    }

    private static int GetSubscriptionsEndedThisMonth(){
        return clsSubscription.SubsExpiredThisMonth();
    }

    public static void ShowSystemStatisticsScreen() {
        DrawScreenHeader("SYSTEM STATISTICS", null);

        System.out.println("GENERAL STATISTICS");
        System.out.println("-----------------------------------");
        System.out.printf("Total Users                : %d%n", GetTotalUsers());
        System.out.printf("Total Movies               : %d%n", GetTotalMovies());
        System.out.printf("Active Subscriptions       : %d%n", GetNumberofAciveSub());
        System.out.printf("Subscriptions Started Month: %d%n", GetSubscriptionsSartedThisMonth());
        System.out.printf("Subscriptions Ended Month  : %d%n", GetSubscriptionsEndedThisMonth());

        System.out.println("\nMOVIES PER GENRE");
        System.out.println("-----------------------------------");

        Map<String, Integer> moviesPerGenre = GetMoviePerGenre();

        if (moviesPerGenre.isEmpty()) {
            System.out.println("No movies available.");
        } else {
            moviesPerGenre.forEach((genre, count) ->
                System.out.printf("%-15s : %d%n", genre, count)
            );
        }

        System.out.println("-----------------------------------");
        Pause.pause("Press any key to return...");
    }


    

}
