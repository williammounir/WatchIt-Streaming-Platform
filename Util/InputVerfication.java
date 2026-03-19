package Util;

import java.util.Scanner;

public class InputVerfication {
    public static int GetNumberBetween(int min,int max){
        int num ;
        do{
            System.out.printf("\nKindly Enter a Number Between %d and %d..... ",min,max);
            num = Integer.parseInt(S.scanner.nextLine().trim());
            System.out.println();

        }while(num <min || num>max);
        return num;
    }

    public static char readYesNo() {
    while (true) {
        System.out.print("Enter (y/n): ");
        String input = S.scanner.nextLine().trim();

        if (input.equalsIgnoreCase("y")) {
            return 'y';
        }
        if (input.equalsIgnoreCase("n")) {
            return 'n';
        }

        System.out.println("Invalid input. Please enter y or n.");
    }
    }
    public static char TwoChoices(char x, char y) {
    while (true) {
        System.out.printf("Enter (%c/%c): ",x,y);
        String input = S.scanner.nextLine().trim();

        if (input.equalsIgnoreCase(String.valueOf(x))) {
            return x;
        }
        if (input.equalsIgnoreCase(String.valueOf(y))) {
            return y;
        }

        System.out.printf("Invalid input. Please enter %c or %c.\n",x,y);
    }
}

}
