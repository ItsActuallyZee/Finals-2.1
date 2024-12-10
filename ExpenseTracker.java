import java.util.*;

public class ExpenseTracker {
    public static Scanner scan = new Scanner(System.in);
    static final int MAX_ACCOUNT = 3;
    static final int MAX_ITEM = 3;

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

        Item(String name, float price, int sub, boolean isSubscription) {
            this.itemName = name;
            this.itemPrice = price;
            this.itemSub = sub;
            this.isSubscription = isSubscription;
        }
    }

    static ArrayList<User> users = new ArrayList<>(); // stores multiple users
    static User currentUser = null;
    
    static void displayUI() {
        System.out.print("\033\143");
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
        System.out.print("\033\143");
        System.out.println("=".repeat(41)); // == PATTERN
        System.out.println("[   Welcome to Expense Tracker System   ]");
        System.out.println("=".repeat(41));
        System.out.println("[1] Set Budget");
        System.out.println("[2] Add Item");
        System.out.println("[3] Delete Item");
        System.out.println("[4] Display Items");
        System.out.println("[5] Delete All Items");
        System.out.println("[6] Log Out\n");
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
    static void registerUI() {
        System.out.print("\033\143");
        System.out.println("=".repeat(41)); // == PATTERN
        System.out.println("[\t      USER REGISTER\t        ]");
        System.out.println("=".repeat(41));
        
    }

    static void setBudgetUI() {
        System.out.print("\033\143");
        System.out.println("=".repeat(41)); // == PATTERN
        System.out.println("[\t       SET BUDGET\t        ]");
        System.out.println("=".repeat(41));
    }

    static void addItemUI() {
        System.out.print("\033\143");
        System.out.println("=".repeat(41)); // == PATTERN
        System.out.println("[\t        ADD ITEM\t        ]");
        System.out.println("=".repeat(41));
    }
    static void addItem(String itemName, float itemPrice, int itemSub, boolean isSubscription) {
        // Add the item to the current user's list
        currentUser.items.add(new Item(itemName, itemPrice, itemSub, isSubscription));
        addItemUI();
        System.out.println("-- Item Added Successfully --");
    }

    static void deleteItem(String itemName) {
        Item toDelete = null;
        for (Item item : currentUser.items) {
            if (item.itemName.equals(itemName)) {
                toDelete = item;
                break;
            }
        }
        if (toDelete != null) {
            currentUser.items.remove(toDelete);
            logInUI();
            System.out.println("-- Expense Deleted Successfully --");
        } else {
            logInUI();
            System.out.println("-- Item Not Found, Please Try Again --");
        }
    }
    static void deleteItemUI() {
        System.out.print("\033\143");
        System.out.println("=".repeat(41)); // == PATTERN
        System.out.println("[\t      DELETE ITEM\t        ]");
        System.out.println("=".repeat(41));
        
    }

    static void displayItems() {
        System.out.print("\033\143");
        
    
        float totalAmount = 0;
    
        // Display non-subscription items
        System.out.println("=".repeat(65));
        System.out.println("[\t    ITEM NAME   \t][\t     ITEM PRICE      \t]");
        System.out.println("=".repeat(65));
        boolean hasRegularItems = false;
        for (Item item : currentUser.items) {
            if (!item.isSubscription) {
                hasRegularItems = true;
                System.out.printf("  \t%-21s%-15.2f\n", item.itemName, item.itemPrice);
                totalAmount += item.itemPrice;

            }
        }
        if (!hasRegularItems) {
            System.out.printf("\t\t    -No Data-\n");
        }
        // Display Subscription items
        System.out.println("=".repeat(65));
        System.out.println("[\tSUBSCRIPTION NAME\t][\tSUBSCRIPTION PRICE\t]");
        System.out.println("=".repeat(65));
        boolean hasSubscriptionItems = false;
        for (Item item : currentUser.items) {
            if (item.isSubscription) {
                hasSubscriptionItems = true;
                System.out.printf("  \t%-21s%-7.2f/month\n", item.itemName, item.itemPrice);
                totalAmount += item.itemPrice * item.itemSub; // Total cost for subscriptions
            }
        }
        if (!hasSubscriptionItems) {
            System.out.printf("\t\t    -No Data-\n");
        }
        // Display budget details
        float budgetLeft = currentUser.Budget - totalAmount;
        System.out.println("=".repeat(65));
        System.out.println("[\t\t\t   SUMMARY\t\t\t\t]");
        System.out.println("=".repeat(65));
        System.out.printf("\tBudget:              %.2f\n", currentUser.Budget);
        System.out.printf("\tTotal Expenses:      %.2f\n", totalAmount);
        System.out.printf("\tBudget Left:         %.2f\n", budgetLeft);
        System.out.println("=".repeat(65));
    }
    
    static void deleteAllItems() {
        currentUser.items.clear();
    }

    public static void main (String[] args) {
        int choice = -1;
        
        System.out.print("\033\143"); //CLS
        displayUI();

        do {
            if(choice > 3 || choice == 0) {
                System.out.print("Invalid Choice: ");
            } else {
                System.err.print("Choice: ");
            }
            while (true) {
                try {
                    choice = scan.nextInt();
                    break;
                } catch (InputMismatchException e) {
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
                        int subChoice = -1;
                        logInUI();
                        System.out.println("-- Successfully Logged In --");
                        
                        do {
                            if(subChoice > 5 || subChoice == 0) {
                                System.out.print("Invalid Choice: ");
                            } else {
                                System.err.print("Choice: ");
                            }
                            while(true) {
                                try {
                                    subChoice = scan.nextInt();
                                    break;
                                } catch (InputMismatchException e) {
                                    logInUI();
                                    System.out.print("Enter a valid number: ");
                                    scan.next();
                                }
                            }
                            scan.nextLine();
                            
                            String itemName;
                            boolean isSubscription;
                            switch(subChoice) {
                                case 1: // SET BUDGET
                                    setBudgetUI();
                                    
                                    System.out.print("Enter your Budget: ");
                                    while(true) {
                                        try {
                                            currentUser.Budget = scan.nextFloat();
                                            if(currentUser.Budget > 0) {
                                                break;
                                            } else {
                                                setBudgetUI();
                                                System.out.print("Enter a valid number: ");
                                            }
                                        } catch (InputMismatchException e) {
                                            setBudgetUI();
                                            System.out.print("Enter a valid number: ");
                                            scan.next();
                                        }
                                    }
                                    scan.nextLine();

                                    logInUI();
                                    System.out.println("-- Budget Set Successfully --");
                                    break;
                                case 2: // ADD ITEM
                                    addItemUI();
                                    if (currentUser.items.size() >= MAX_ITEM) {
                                        logInUI();
                                        System.out.println("-- Item Limit Reached. Please Delete Items --");
                                        break;
                                    }

                                    int iteration = 0;
                                    System.out.print("Enter how many items you want to add: ");
                                    while(true) {
                                        try {
                                            iteration = scan.nextInt();
                                            if(iteration > 0) {
                                                break;
                                            } else {
                                                addItemUI();
                                                System.out.println("Enter how many items you want to add?");
                                                System.out.print("Enter a valid number: ");
                                            }
                                        } catch (InputMismatchException e) {
                                            addItemUI();
                                            System.out.println("Enter how many items you want to add?");
                                            System.out.print("Enter a valid number: ");
                                            scan.next();
                                        }
                                    }
                                    scan.nextLine();

                                    if(iteration > MAX_ITEM) {
                                        logInUI();
                                        System.out.println("-- Too Many Items! --");
                                        break;                                        
                                    }
                                    
                                    if(iteration > 0) {
                                        for (int i = 0; i < iteration; i++) {
                                            System.out.print("Enter Expense Name: ");
                                            itemName = scan.nextLine();
                                            
                                            System.out.print("Is this item a Subscription? [1] Yes [2] No: ");
                                            int ynChoice = 0;
                                            while(true) {
                                                try {
                                                    ynChoice = scan.nextInt();
                                                    if(ynChoice > 2) {
                                                        addItemUI();
                                                        System.out.println("Is this item a Subscription? [1] Yes [2] No");
                                                        System.out.print("Enter a valid number: ");
                                                    } else {
                                                        break;
                                                    }
                                                } catch (InputMismatchException e) {
                                                    addItemUI();
                                                    System.out.println("Is this item a Subscription? [1] Yes [2] No");
                                                    System.out.print("Enter a valid number: ");
                                                    scan.next();
                                                }
                                            }
                                            isSubscription = (ynChoice == 1);
                                            if (isSubscription) {
                                                float itemPrice = 0;
                                                System.out.print("Enter Monthly Payment: ");
                                                while(true) {
                                                    try {
                                                        itemPrice = scan.nextFloat();
                                                        break;
                                                    } catch (InputMismatchException e) {
                                                        addItemUI();
                                                        System.out.print("Enter Monthly Payment: ");
                                                        scan.next();
                                                    }
                                                }

                                                int itemSub = 0;
                                                System.out.print("Enter Subscription Duration (in months): ");
                                                while(true) {
                                                    try {
                                                        itemSub = scan.nextInt();
                                                        if(itemSub > 0) {
                                                            break;
                                                        } else {
                                                            addItemUI();
                                                            System.out.print("Enter Subscription Duration (in months): ");
                                                        }
                                                    } catch (InputMismatchException e) {
                                                        addItemUI();
                                                        System.out.print("Enter a valid Subscription Duration (in months): ");
                                                        scan.next();
                                                    }
                                                }
                                                scan.nextLine(); // Consume newline
                                                addItem(itemName, itemPrice, itemSub, true);
                                            } else {
                                                float itemPrice = 0;
                                                System.out.print("Enter Expense Price: ");
                                                while(true) {
                                                    try {
                                                        itemPrice = scan.nextFloat();
                                                        if(itemPrice > 0)
                                                            break;
                                                    } catch (InputMismatchException e) {
                                                        addItemUI();
                                                        System.out.print("Enter a valid Expense Price: ");
                                                        scan.next();
                                                    }
                                                }
                                                scan.nextLine(); // Consume newline
                                                addItem(itemName, itemPrice, 0, false);
                                            }
                                        }
                                    }
                                    logInUI();
                                    System.out.println("-- Item Added Successfully --");
                                    break;
                                case 3:
                                    deleteItemUI();
                                    System.out.print("Enter Expense Name to Delete: ");
                                    itemName = scan.nextLine();
                                    deleteItem(itemName);
                                    break;
                                case 4:
                                    if (currentUser.items.isEmpty()) {
                                        logInUI();
                                        System.out.println("-- No Expenses recorded. --");
                                        break;
                                    }
                                    displayItems();
                                    int pressMe = 0;
                                    System.out.print("Press '1' to continue: ");
                                    while(true) {
                                        try {
                                            pressMe = scan.nextInt();
                                            if(pressMe != 1) {
                                                displayItems();
                                                System.out.print("JUST PRESS '1': ");
                                            } else {
                                                break;
                                            }
                                        } catch (InputMismatchException e) {
                                            displayItems();
                                            System.out.print("JUST PRESS '1': ");
                                            scan.next();
                                        }
                                    }
                                    logInUI();
                                    break;
                                case 5:
                                    deleteAllItems();
                                    logInUI();
                                    System.out.println("-- All items have been deleted. --");
                                    break;
                                case 6:
                                    System.out.print("\033\143");
                                    displayUI();
                                    System.out.println("-- User Logged Out --");
                                    break;
                                default:
                                    System.out.print("\033\143");
                                    logInUI();
                            }
                        } while (subChoice != 6);
                    }
                    break;
                case 2: // REGISTER
                    if(users.size() > MAX_ACCOUNT) {
                        displayUI();
                        System.out.println("-- Max Users Reached! --");
                        break;
                    } else {
                        registerUI();
                        System.out.print("Enter User: ");
                        userName = scan.nextLine();

                        System.out.print("Enter Password: ");
                        userPassword = scan.nextLine();
                    }
                    displayUI();
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
                    displayUI();
            }
        } while (choice != 3);
        System.out.println("\n-- PROGRAM TERMINATED --");
    }
}
