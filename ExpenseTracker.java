import java.util.*;

public class ExpenseTracker {
    public static Scanner scan = new Scanner(System.in);
    static final int MAX_ACCOUNT = 5;
    static final int MAX_ITEM = 5;

    static class User {
        String name;
        String password;
        float Budget;
        ArrayList<Item> items = new ArrayList<>(); // lists down multiple items

        User(String name, String password) {
            this.name = name;
            this.password = password;
        }
    }

    static class Item {
        String itemName;
        float itemPrice;
        int itemSub; // Subscription duration, 0 if not a subscription
        boolean isSubscription; // Flag to check if it's a subscription
        int itemMonth, itemDay, itemYear;
        
        Item(String name, float price, int sub, boolean isSubscription, int itemMonth, int itemDay, int itemYear) {
            this.itemName = name;
            this.itemPrice = price;
            this.itemSub = sub;
            this.isSubscription = isSubscription;
            this.itemMonth = itemMonth;
            this.itemDay = itemDay;
            this.itemYear = itemYear;
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
    static void logInUI() {
        System.out.println("=".repeat(41)); // == PATTERN
        System.out.println("[   Welcome to Expense Tracker System   ]");
        System.out.println("=".repeat(41));
        System.out.println("[1] Add Item");
        System.out.println("[2] Delete Item");
        System.out.println("[3] Display Items");
        System.out.println("[4] Delete All Items");
        System.out.println("[5] Log Out");
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
    static void addItemUI() {
        System.out.print("\033\143");
        System.out.println("=".repeat(41)); // == PATTERN
        System.out.println("[\t        ADD ITEM\t        ]");
        System.out.println("=".repeat(41));
    }
    static void addItem(String itemName, float itemPrice, int itemSub, boolean isSubscription) {
        if (currentUser.items.size() >= MAX_ITEM) {
            System.out.println("Invalid, Item Limit Reached. Please Try Again");
            return;
        }
        //currentUser.items.add(new Item(itemName, itemPrice, itemSub, isSubscription));
        System.out.println("[Item Added Successfully]");
    }

    public static void main (String[] args) {
        int choice = 0;
        
        System.out.print("\033\143"); //CLS
        displayUI();

        do {
            if(choice > 3) {
                System.out.print("Invalid Choice: ");
            } else {
                System.err.print("Choice: ");
            }
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
                        int subChoice = 0;
                        logInUI();
                        System.out.println("-- Successfully Logged In --");
                        
                        do {
                            if(subChoice > 3) {
                            System.out.print("Invalid Choice: ");
                            } else {
                                System.err.print("Choice: ");
                            }
                            while(true) {
                                try {
                                    subChoice = scan.nextInt();
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.print("\033\143");
                                    logInUI();
                                    System.out.print("Enter a valid number: ");
                                    scan.next();
                                }
                            }
                            scan.nextLine();
                            
                            String itemName;
                            boolean isSubscription;
                            switch(subChoice) {
                                case 1: // ADD ITEM
                                    addItemUI();

                                    System.out.print("Enter your Budget: ");
                                    while(true) {
                                        try {
                                            currentUser.Budget = scan.nextFloat();
                                            if(currentUser.Budget > 0) {
                                                break;
                                            } else {
                                                addItemUI();
                                                System.out.print("Enter a valid number: ");
                                            }
                                        } catch (InputMismatchException e) {
                                            addItemUI();
                                            System.out.print("Enter a valid number: ");
                                            scan.next();
                                        }
                                    }
                                    scan.nextLine();
                                    
                                    int iteration = 0;
                                    System.out.print("Enter how many items you want to add: ");
                                    while(true) {
                                        try {
                                            iteration = scan.nextInt();
                                            if(iteration > 0) {
                                                break;
                                            } else {
                                                addItemUI();
                                                System.out.print("Enter how many items you want to add: ");
                                                System.out.print("Enter a valid number: ");
                                            }
                                        } catch (InputMismatchException e) {
                                            addItemUI();
                                            System.out.print("Enter how many items you want to add: ");
                                            System.out.print("Enter a valid number: ");
                                            scan.next();
                                        }
                                    }
                                    scan.nextLine();
                                    
                                    if (iteration > 0) {
                                        for (int i = 0; i < iteration; i++) {
                                            System.out.print("Enter Expense Name: ");
                                            itemName = scan.nextLine();
                                            
                                            System.out.print("Is this item a Subscription? [1] Yes  [2] No: ");
                                            int ynChoice = 0;
                                            while(true) {
                                                try {
                                                    ynChoice = scan.nextInt();
                                                    if(ynChoice > 2) {
                                                        addItemUI();
                                                        System.out.print("Is this item a Subscription? [1] Yes  [2] No: ");
                                                        System.out.print("Enter a valid number: ");
                                                        break;
                                                    }
                                                } catch (InputMismatchException e) {
                                                    addItemUI();
                                                    System.out.print("Is this item a Subscription? [1] Yes  [2] No: ");
                                                    System.out.print("Enter a valid number: ");
                                                }
                                            }
                                            isSubscription = (ynChoice == 1);
                                            float itemSub = 0;
                                            if (isSubscription) {
                                                System.out.print("Enter Monthly Payment: ");
                                                itemSub = scan.nextInt();
                                                scan.nextLine();
                                            } else {
                                            System.out.print("Enter Expense Price: ");
                                            float itemPrice = scan.nextFloat();

                                            //addItem(itemName, itemPrice, itemSub, isSubscription);
                                            }
                                        }
                                    }
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    break;
                                case 5:
                                    System.out.print("\033\143");
                                    displayUI();
                                    System.out.println("-- User Logged Out --");
                                    break;
                                default:
                                    System.out.print("\033\143");
                                    logInUI();
                            }
                        } while (subChoice != 5);
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
            }
        } while (choice != 3);
        System.out.println("\n-- PROGRAM TERMINATED --");
    }
}
