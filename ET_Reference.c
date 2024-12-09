#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define MAX_ITEM 100
#define MAX_ACCOUNT 100

typedef struct {
    char itemName[50];
    float itemPrice;
} itemData;

typedef struct {
    char userName[50];
    char userPassword[50];
    itemData items[MAX_ITEM];
    int numItems;
} userData;

void addItem(itemData item[], int *numItems, char itemName[], float itemPrice) {
    // Check if the maximum item limit is reached
    if (*numItems >= MAX_ITEM) {
        printf("\nInvalid, Item Limit Reached. Please Try again\n");
        return;
    }

    // Copy item name and price to the item array
    strcpy(item[*numItems].itemName, itemName);
    item[*numItems].itemPrice = itemPrice;
    (*numItems)++;

    printf("\n\n[Item Added Successfully]\n");
}

void deleteItem(itemData item[], int *numItems, char itemName[]) {
    int found = 0;

    // Search for the item to delete
    for (int i = 0; i < *numItems; i++) {
        if (strcmp(item[i].itemName, itemName) == 0) {
            found = 1;
            item[i] = item[(*numItems) - 1];
            (*numItems)--;
            printf("\nExpense Deleted Successfully\n");
        }
    }
    if (found == 0) {
        printf("\nItem Not Found, Please Try Again\n");
    }
}

void displayItem(itemData item[], int *numItems, float budget) {
    printf("\n\nDisplaying Table...\n\n");
    if (*numItems == 0) {
        printf("\nNo Expenses recorded.\n");
        return;
    }
    
    float totalAmount = 0;
    float subtracted_budget;

    printf("\nItem Name\tItem Price\n");
    printf("==============================\n");

    for (int i = 0; i < *numItems; i++) {
        printf("%-17s%-17.2f\n", item[i].itemName, item[i].itemPrice);
        totalAmount += item[i].itemPrice;
    }
    char budget_string[] = "Budget:";
    char budget_left_string[] = "Budget Left:";
    subtracted_budget = budget - totalAmount;
    printf("------------------------------\n");
    printf("%-17s%-17.2f\n",budget_string, budget);
    printf("Total Expenses:  %.2f\n", totalAmount);
    printf("------------------------------\n");
    printf("%-17s%-17.2f\n", budget_left_string, subtracted_budget);
    printf("==============================\n");
}

void deleteAllItem(itemData item[], int *numItems) {
    *numItems = 0;
    printf("\nAll items have been deleted");
}

void userDelete(userData users[], int *numUsers, char userName[], char userPassword[]) {
    int found = 0;
    char password[100];
    for (int i = 0; i < *numUsers; i++) {
        if (strcmp(users[i].userName, userName) == 0) {
            found = 1;
            int correct = 0;
            do {
                printf("Input past password (Enter 'back' to go back): ");
                fgets(password, sizeof(password), stdin);
                password[strcspn(password, "\n")] = '\0';
                if (strcmp(password, "back") == 0) {
                    system("cls");
                    return; // Go back to the main menu
                }
                if (strcmp(password, users[i].userPassword) == 0) {
                    correct = 1;
                    int choice = 0;
                    do {
                        printf("\n\nPress [1] to Confirm DELETION or [2] to Cancel: ");
                        if (scanf("%d", &choice) != 1) {
                            while (getchar() != '\n'); 
                            choice = 0; 
                        }
                        if (choice == 1) {
                            users[i] = users[(*numUsers) - 1];
                            (*numUsers)--;
                            printf("Account Deleted Successfully\n");

                            system("cls");
                        } else if (choice == 2) {
                            system("cls");
                            return; // Cancel deletion and go back to the main menu
                        } else {
                            system("cls");
                            printf("Error: Invalid choice. Please try again.\n");
                        }
                    } while (choice != 1 && choice != 2);
                } else {
                    system("cls");
                    printf("Password Incorrect, Please Try Again\n");
                }
            } while (correct != 1);
        }
    }
    if (found == 0) {
        system("cls");
        printf("Account Not Found\n");
    }
}

void registerUser(userData users[], int *numUsers, char userName[], char userPassword[]) {
    if (*numUsers < MAX_ACCOUNT) {
        strcpy(users[*numUsers].userName, userName);
        strcpy(users[*numUsers].userPassword, userPassword);
        users[*numUsers].numItems = 0;
        (*numUsers)++;
        printf("\nUser registered Successfully!");
    } else {
        printf("\nMax user reached!");
    }
}

void logIn(userData users[], int *numUsers, char userName[], char userPassword[], int *currentUserIndex) {
    for (int i = 0; i < *numUsers; i++) {
        if (strcmp(users[i].userName, userName) == 0 && strcmp(users[i].userPassword, userPassword) == 0) {
            *currentUserIndex = i;
            printf("\nLogged In Successfully\n");
            return;
        }
    }
    printf("\nInvalid User Name or Password");
}

void logOut(int *currentUserIndex) {
    *currentUserIndex = -1;
    printf("\nLogged out Successfully");
}

void saveUserData(userData users[], int numUsers) {
    FILE *usersFile = fopen("usersDatabase.txt", "w");
    if (usersFile != NULL) {
        fwrite(users, sizeof(userData), numUsers, usersFile);
        fclose(usersFile);
    }
}

void loadUserData(userData users[], int *numUsers) {
    FILE *usersFile = fopen("usersDatabase.txt", "r");
    if (usersFile != NULL) {
        *numUsers = fread(users, sizeof(userData), MAX_ACCOUNT, usersFile);
        fclose(usersFile);
    }
}

int main() {
    itemData item[MAX_ITEM];
    int currentUserIndex = -1;
    int numItems;
    int numUsers = 0;
    int choice;
    char choice2;
    float itemPrice;

    userData users[MAX_ACCOUNT];
    char userPassword[50];
    char userName[50];
    char itemName[50];

    loadUserData(users, &numUsers);

    system("cls");

    do {
        printf("\n\n===================================\n");
        printf("[Welcome to Expense Tracker System]\n");
        printf("===================================\n");
        printf("[1] Log In\n");
        printf("[2] Register\n");
        printf("[3] Delete Account\n");
        printf("[4] Exit Program\n\n");

        printf("Input Choice: ");
        scanf("%d", &choice);

        getchar();

        switch (choice) {
            case 1:
                system("cls");
                printf("===============================\n");
                printf(" [         USER LOGIN        ]\n");
                printf("===============================\n");
                printf("Enter User Name: ");
                fgets(userName, sizeof(userName), stdin);
                userName[strcspn(userName, "\n")] = '\0';

                printf("Enter Password: ");
                fgets(userPassword, sizeof(userPassword), stdin);
                userPassword[strcspn(userPassword, "\n")] = '\0';
                
                system("cls");

                logIn(users, &numUsers, userName, userPassword, &currentUserIndex);
                
                if (currentUserIndex != -1) {
                    do {
                        printf("\n================================\n");
                        printf("[        EXPENSE TRACKER       ]\n");
                        printf("================================\n");
                        printf("[1] Add Item\n");
                        printf("[2] Delete Item\n");
                        printf("[3] Display Item\n");
                        printf("[4] Delete All\n");
                        printf("[5] Log Out\n\n");

                        printf("Input Choice: ");
                        scanf("%d", &choice);
                        getchar();

                        switch (choice) {
                            case 1:
                                system("cls");

                                int iteration;
                                int loop = 1;
                                float budget;

                                printf("Enter your budget: ");
                                scanf("%f", &budget);
                                
                                printf("Enter how many items you want to add: ");
                                while (1) {
                                    if (scanf("%d", &iteration) != 1) {
                                        while (getchar() != '\n'); 
                                        system("cls");
                                        printf("Invalid input!\n");
                                        printf("Enter how many items do you want to add: ");
                                        continue;            
                                    }
                                    if (iteration > 0) {
                                        // iteration = number
                                        getchar();
                                        for (int i = 0; i < iteration; i++) {
                                            printf("==============================\n");
                                            printf("Enter Expense Name: ");
                                            fgets(itemName, sizeof(itemName), stdin);
                                            itemName[strcspn(itemName, "\n")] = '\0';

                                            printf("Enter Expense Price: ");
                                            while (scanf("%f", &itemPrice) != 1) {
                                                while (getchar() != '\n');
                                                system("cls");
                                                printf("Please input a number!\n");
                                                printf("Expense Name: %s\n", itemName);
                                                printf("Enter Expense Price: ");
                                            }
                                            getchar();

                                            printf("==============================\n");
                                            addItem(users[currentUserIndex].items, &users[currentUserIndex].numItems, itemName, itemPrice);

                                            system("cls");
                                        }
                                        break;
                                    }
                                }                                          
                                break;
                            case 2:
                                system("cls");

                                printf("=======================================\n");
                                printf("Enter expense name you want to delete: ");
                                fgets(itemName, sizeof(itemName), stdin);
                                itemName[strcspn(itemName, "\n")] = '\0';
                                printf("=======================================\n");

                                deleteItem(users[currentUserIndex].items, &users[currentUserIndex].numItems, itemName);

                                break;
                            case 3:
                                system("cls");
                                
                                displayItem(users[currentUserIndex].items, &users[currentUserIndex].numItems, budget);
                                do {
                                   printf("\nPRESS '1' TO PROCEED TO MENU: ");
                                   scanf("%d", &choice);
                                   getchar();

                                    if (choice != 1) {
                                        printf("Error: Please Try again!");
                                    }
                               } while (choice != 1);
                                        
                                system("cls");
                                break;
                            case 4:
                                deleteAllItem(users[currentUserIndex].items, &users[currentUserIndex].numItems);
                                system("cls");

                                printf("\nAll items have been deleted\n");
                                break;
                            case 5:
                                system("cls");
                                logOut(&currentUserIndex);
                                break;
                            default:
                                system("cls");
                                printf("\nInvalid Choice input, Please try again\n");
                        }
                    } while (choice != 5);
                }
                break;
            case 2:
                system("cls");
                printf("===============================\n");
                printf("[      USER REGISTRATION      ]\n");
                printf("===============================\n");
                printf("Enter User Name: ");
                fgets(userName, sizeof(userName), stdin);
                userName[strcspn(userName, "\n")] = '\0';

                printf("Enter Password: ");
                fgets(userPassword, sizeof(userPassword), stdin);
                userPassword[strcspn(userPassword, "\n")] = '\0';

                registerUser(users, &numUsers, userName, userPassword);
                saveUserData(users, numUsers);

                system("cls");
                break;
            case 3:
                system("cls");
                printf("\n\n==================================\n");
                printf("[        ACCOUNT DELETION        ]\n");
                printf("==================================\n");
                printf("Enter username of the Account: ");
                fgets(userName, sizeof(userName), stdin);
                userName[strcspn(userName, "\n")] = '\0';

                userDelete(users, &numUsers, userName, userPassword);
                break;
            case 4:
                system("cls");
                printf("Thank you for using the expense tracker system! Please come again\n");
                saveUserData(users, numUsers);
                break;
            default:
                system("cls");
                printf("Invalid Choice input, Please try again\n");                
        }
    } while (choice != 4);
    return 0;
}
