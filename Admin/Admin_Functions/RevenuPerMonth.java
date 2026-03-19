package UI.Admin.Admin_Functions;

import java.util.Map;
import UI.Common.clsScreen;
import models.clsSubscription;

public class RevenuPerMonth extends clsScreen {

    private static String getMonthName(int month) {
        String[] months = {
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };
        return months[month - 1];
    }

    public static void showMonthsRevenuScreen() {
        clsScreen.DrawScreenHeader("Monthly Revenue Report", null);

    Map<Integer, Integer> monthsRevenue = clsSubscription.RevenuForEachMonth();

    System.out.println();
    System.out.println(" Month        Revenue");
    System.out.println(" ---------------------------------------------");

    int totalRevenue = 0;

    int maxMonth = 1;
    int minMonth = 1;
    int maxRevenue = Integer.MIN_VALUE;
    int minRevenue = Integer.MAX_VALUE;

    for (int month = 1; month <= 12; month++) {
        int revenue = monthsRevenue.get(month);
        totalRevenue += revenue;

        if (revenue > maxRevenue) {
            maxRevenue = revenue;
            maxMonth = month;
        }

        if (revenue < minRevenue) {
            minRevenue = revenue;
            minMonth = month;
        }

        System.out.printf(" %-12s %,d%n",
                getMonthName(month),
                revenue);
    }

    System.out.println(" ---------------------------------------------");
    System.out.printf(" Total Yearly Revenue : %,d%n", totalRevenue);
    System.out.println();

    System.out.printf(" Highest Revenue Month: %s (%,d)%n",
            getMonthName(maxMonth), maxRevenue);

    System.out.printf(" Lowest  Revenue Month: %s (%,d)%n",
            getMonthName(minMonth), minRevenue);

    System.out.println();
    }

}
