    static void displayItems() {
        System.out.print("\033\143");
        if (currentUser.items.isEmpty()) {
            System.out.println("=".repeat(56));
            System.out.println("[\t\t[No Expenses recorded.]                ]");
            System.out.println("=".repeat(56));
            return;
        }
    
        float totalAmount = 0;
    
        // Display non-subscription items
        System.out.println("=".repeat(56)); 
        System.out.println("[\t[ITEM NAME]        [ITEM PRICE]                ]");
        System.out.println("=".repeat(56)); 
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
        System.out.println("=".repeat(56));
        System.out.println("[\t[SUBSCRIPTION NAME] [SUBSCRIPTION PRICE}       ]");
        System.out.println("=".repeat(56));
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
        System.out.println("=".repeat(56));
        System.out.println("[\t[SUMMARY]                                      ]");
        System.out.println("=".repeat(56));
        System.out.printf("\tBudget:              %.2f\n", currentUser.Budget);
        System.out.printf("\tTotal Expenses:      %.2f\n", totalAmount);
        System.out.printf("\tBudget Left:         %.2f\n", budgetLeft);
        System.out.println("=".repeat(56));
    }
