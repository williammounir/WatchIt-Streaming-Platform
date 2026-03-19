package Util;

import java.util.Scanner;

public class Pause {
    public static void pause(String Message) {
    System.out.printf("\n%s\n",Message);
    new Scanner(System.in).nextLine();
}

}
