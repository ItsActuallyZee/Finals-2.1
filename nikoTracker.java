import java.util.*;

class Main {

    static final int MAX_ITEM = 100;

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

    static class User {
        String userName;
        String userPassword;
        List<Item> items = new ArrayList<>();
        float budget;

        User(String name, String password) {
            this.userName = name;
            this.userPassword = password;
        }
    }

    static List<User> users = new ArrayList<>();
    static User currentUser = null;

    static void addItem(String itemName, float itemPrice, int itemSub, boolean isSubscription) {
    if (currentUser.items.size() >= MAX_ITEM) {
        System.out.println("Invalid, Item Limit Reached. Please Try Again");
        return;
    }

    // Add the item to the current user's list
    currentUser.items.add(new Item(itemName, itemPrice, itemSub, isSubscription));
    System.out.println("[Item Added Successfully]");
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
            System.out.println("Expense Deleted Successfully");
        } else {
            System.out.println("Item Not Found, Please Try Again");
        }
    }
    static void displayItems() {
        System.out.println("\nDisplaying Table...");
        if (currentUser.items.isEmpty()) {
            System.out.println("No Expenses recorded.");
            return;
        }
    
        float totalAmount = 0;
    
        // Display non-subscription items
        System.out.println("\nItem Name           Item Price");
        System.out.println("=======================================================");
        for (Item item : currentUser.items) {
            if (!item.isSubscription) {
                System.out.printf("%-20s%-15.2f\n", item.itemName, item.itemPrice);
                totalAmount += item.itemPrice;
            }
        }
        System.out.println("=======================================================");
    
        // Display subscription items
        System.out.println("\nSubscription Name           Subscription Price");
        System.out.println("=======================================================");
        for (Item item : currentUser.items) {
            if (item.isSubscription) {
                System.out.printf("%-30s%.2f/month\n", item.itemName, item.itemPrice);
                totalAmount += item.itemPrice * item.itemSub; // Total cost for subscriptions
            }
        }
    
        // Display budget details
        float budgetLeft = currentUser.budget - totalAmount;
        System.out.println("=======================================================");
        System.out.printf("Budget:         %.2f\n", currentUser.budget);
        System.out.printf("Total Expenses: %.2f\n", totalAmount);
        System.out.printf("Budget Left:    %.2f\n", budgetLeft);
    }
    static void deleteAllItems() {
        currentUser.items.clear();
        System.out.println("All items have been deleted");
    }

    static void registerUser(String userName, String userPassword) {
        for (User user : users) {
            if (user.userName.equals(userName)) {
                System.out.println("Username already exists. Please try a different username.");
                return;
            }
        }
        users.add(new User(userName, userPassword));
        System.out.println("User registered Successfully!");
    }

    static void logIn(String userName, String userPassword) {
        for (User user : users) {
            if (user.userName.equals(userName) && user.userPassword.equals(userPassword)) {
                currentUser = user;
                System.out.println("Logged In Successfully");
                return;
            }
        }
        System.out.println("Invalid User Name or Password");
    }

    static void logOut() {
        currentUser = null;
        System.out.println("Logged out Successfully");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n[Welcome to Expense Tracker System]");
            System.out.println("[1] Log In");
            System.out.println("[2] Register");
            System.out.println("[3] Exit Program");
            System.out.print("Input Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter User Name: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String userPassword = scanner.nextLine();
                    logIn(userName, userPassword);
                    if (currentUser != null) {
                        int subChoice;
                        do {
                            System.out.println("\n[EXPENSE TRACKER]");
                            System.out.println("[2] Add Item");
                            System.out.println("[3] Delete Item");
                            System.out.println("[5] Display Items");
                            System.out.println("[6] Delete All Items");
                            System.out.println("[7] Log Out");
                            System.out.print("Input Choice: ");
                            subChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            switch (subChoice) {
                                case 2:
                                    System.out.print("Enter your Budget: ");
                                    currentUser.budget = scanner.nextFloat();
                                    scanner.nextLine();

                                    System.out.print("Enter How many items you want to add: ");
                                    int iteration = scanner.nextInt();
                                    scanner.nextLine();
                                    
                                    if (iteration > 0) {
                                        for (int i = 0; i < iteration; i++) {
                                            System.out.print("Enter Expense Name: ");
                                            String itemName = scanner.nextLine();
                                            System.out.print("Is this item a Subscription? [1] Yes  [2] No: ");
                                            int ynChoice = scanner.nextInt();
                                            scanner.nextLine();
                                            boolean isSubscription = (ynChoice == 1);
                                    
                                            if (isSubscription) {
                                                System.out.print("Enter Monthly Payment: ");
                                                float itemPrice = scanner.nextFloat();
                                                System.out.print("Enter Subscription Duration (in months): ");
                                                int itemSub = scanner.nextInt();
                                                scanner.nextLine(); // Consume newline
                                                addItem(itemName, itemPrice, itemSub, true);
                                            } else {
                                                System.out.print("Enter Expense Price: ");
                                                float itemPrice = scanner.nextFloat();
                                                scanner.nextLine(); // Consume newline
                                                addItem(itemName, itemPrice, 0, false);
                                            }
                                        }
                                    }

                                    break;
                                case 3:
                                    System.out.print("Enter Expense Name to Delete: ");
                                    String itemName = scanner.nextLine();
                                    deleteItem(itemName);
                                    break;
                                case 5:
                                    displayItems();
                                    break;
                                case 6:
                                    deleteAllItems();
                                    break;
                                case 7:
                                    logOut();
                                    break;
                                default:
                                    System.out.println("Invalid Choice, Please tryl again");
                            }
                        } while (subChoice != 7);
                    }
                    break;

                case 2:
                    System.out.print("Enter User Name: ");
                    userName = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    userPassword = scanner.nextLine();
                    registerUser(userName, userPassword);
                    break;

                case 3:
                    System.out.println("Thank you for using the Expense Tracker!");
                    break;

                default:
                    System.out.println("Invalid Choice, Please try again");
            }
        } while (choice != 3);
        scanner.close();
    }
}
