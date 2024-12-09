import java.util.*;

public class ExpenseTracker {
    public static Scanner scan = new Scanner(System.in);
    static final int MAX_ACCOUNT = 10;
    static final int MAX_ITEM = 10;

    static class User {
        String name;
        String password;
        ArrayList<Item> items = new ArrayList<>(); // lists down multiple items

        User(String name, String password) {
            this.name = name;
            this.password = password;
        }
    }

    static class Item {
        String itemName;
        float itemPrice;

        Item(String itemName, float itemPrice) {
            this.itemName = itemName;
            this.itemPrice = itemPrice;
        }
    }

    static ArrayList<User> users = new ArrayList<>(); // stores multiple users
    static User currentUser = null;
    
    static void displayUI() {
        System.out.println("=".repeat(41)); // == PATTERN
        System.out.println("[   Welcome to Expense Tracker System   ]");
        System.out.println("=".repeat(41));
        System.out.println("[1] Log in");
        System.out.println("[2] Register");
        System.out.println("[3] Exit Program\n");
    }

    static void logIn(String userName, String userPassword) {
        for(User existing : users) {
            if(existing.name.equals(userName) && existing.password.equals(userPassword)) {
                currentUser = existing;
                return; 
            }
        }
        displayUI();
        System.out.println("-- Invalid User Credentials --");
    }

    static void registerUser(String newName, String newPassword) {
        for(User existing : users) {
            if(existing.name.equals(newName)) {
                displayUI();
                System.out.println("-- Username already exists. Please try a different username --");
                return;
            }
        }
        users.add(new User(newName, newPassword));
        displayUI();
        System.out.println("-- User Registered Successfully --");
    }

    public static void main (String[] args) {
        int choice = 0;
        
        System.out.print("\033\143"); //CLS
        displayUI();

        do {
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
            scan.nextLine();

            String userName;
            String userPassword;
            switch (choice) {
                case 1: // LOG IN
                    System.out.print("\033\143");
                    System.out.println("=".repeat(41)); // == PATTERN
                    System.out.println("[\t       USER LOG IN\t        ]");
                    System.out.println("=".repeat(41));

                    System.out.print("Enter User: ");
                    userName = scan.nextLine();

                    System.out.print("Enter Password: ");
                    userPassword = scan.nextLine();
                    
                    
                    System.out.print("\033\143");
                    logIn(userName, userPassword);
                    if(currentUser != null) {
                        System.out.println("TITE");
                    }
                    break;
                case 2: // REGISTER
                    System.out.print("\033\143");
                    System.out.println("=".repeat(41)); // == PATTERN
                    System.out.println("[\t      USER REGISTER\t        ]");
                    System.out.println("=".repeat(41));

                    System.out.print("Enter User: ");
                    userName = scan.nextLine();

                    System.out.print("Enter Password: ");
                    userPassword = scan.nextLine();

                    System.out.print("\033\143");
                    registerUser(userName, userPassword);
                    break;
                case 3: // TERMINATE PROGRAM
                    System.out.print("\033\143");
                    System.out.println("=".repeat(41)); // == PATTERN
                    System.out.println("[\t   TERMINATING PROGRAM     \t]");
                    System.out.println("=".repeat(41));

                    System.out.println("Thank you for using our program!");
                    break;
                default:
                    System.out.print("\033\143");
                    displayUI();
                    System.out.print("Invalid Choice: ");
                    choice = scan.nextInt();
            }
            
        } while (choice != 3);
        System.out.println("-- PROGRAM TERMINATED --");
    }
}
