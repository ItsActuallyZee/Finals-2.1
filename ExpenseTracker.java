import java.util.*;
public class ExpenseTracker {
    public static Scanner scan = new Scanner(System.in);

    public static void displayUI() {
        System.out.println("=".repeat(37)); // == PATTERN
        System.out.println("[ Welcome to Expense Tracker System ]");
        System.out.println("=".repeat(37));
        System.out.println("[1] Log in");
        System.out.println("[2] Register");
        System.out.println("[3] Exit Program\n");
    }
    public static void main (String[] args) {
        int choice = 0;
        
        System.out.print("\033\143"); //CLS
        do {
            displayUI();

            System.out.print("Choice: ");
            while (true) {
                try {
                    choice = scan.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("\033\143");
                    displayUI();
                    System.out.print("Enter a valid number: ");
                    scan.next();
                }
            }
            
        } while (choice != 3);
        System.out.println("hi");
    }
}
