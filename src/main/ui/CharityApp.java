package ui;

import model.Charity;
import model.CharityManager;

import java.util.Scanner;

// Charity application

public class CharityApp {
    private CharityManager charityManager;
    private Scanner scanner;

    // MODIFIES: this
    // EFFECTS: initializes charities and runs charity application
    public CharityApp() {
        charityManager = new CharityManager();
        scanner = new Scanner(System.in);
        runCharity();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runCharity() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = scanner.next().toLowerCase();

            if (command.equals("0")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
        scanner.close();
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            viewCharities();
        } else if (command.equals("2")) {
            addCharity();
        } else if (command.equals("3")) {
            removeCharity();
        } else if (command.equals("4")) {
            viewCharitiesWithReachedGoals();
        } else if (command.equals("5")) {
            fundCharity();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> View charities");
        System.out.println("\t2 -> Add charity");
        System.out.println("\t3 -> Remove charity");
        System.out.println("\t4 -> View charities with reached goals");
        System.out.println("\t5 -> Fund a charity");
        System.out.println("\t0 -> Quit");
    }

    // EFFECTS: displays the list of existing charities
    private void viewCharities() {
        System.out.println("List of Charities:");
        for (int i = 0; i < charityManager.getCharitiesSize(); i++) {
            Charity charity = charityManager.getCharities().get(i);
            System.out.println((i + 1) + ". " + charity.getName() + " - Funding Goal: $"
                    + charity.getFundingGoal() + " - Current Funds: $" + charity.getCurrentFunds());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a charity to the existing charities
    private void addCharity() {
        System.out.println("Enter the name of the charity: ");
        String name = scanner.next();
        System.out.println("Enter the funding goal for the charity: $");
        double fundingGoal = scanner.nextDouble();
        charityManager.addCharity(new Charity(name, fundingGoal));
        System.out.println("Charity added successfully.");
    }

    // MODIFIES: this
    // EFFECTS: removes a charity from the existing charities
    private void removeCharity() {
        viewCharities();
        if (charityManager.isEmpty()) {
            return;
        }
        System.out.println("Enter the number of the charity to remove: ");
        int choice = scanner.nextInt();
        if (choice < 1 || choice > charityManager.getCharitiesSize()) {
            System.out.println("Invalid choice!");
            return;
        }
        Charity removedCharity = charityManager.getCharities().remove(choice - 1);
        System.out.println(removedCharity.getName() + " removed.");
    }

    // EFFECTS: displays the count and list of charities that have reached their funding goals
    private void viewCharitiesWithReachedGoals() {
        System.out.println(charityManager.countCharitiesWithReachedGoals() + " charities have reached their goal.");
        System.out.println("Charities with Reached Goals:");
        int count = 0;
        for (Charity charity : charityManager.getCharities()) {
            if (charity.hasReachedGoal()) {
                System.out.println(charity.getName() + " - Funding Goal: $" + charity.getFundingGoal()
                        + " - Current Funds: $" + charity.getCurrentFunds());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No charities have reached their funding goals yet.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the given funds from user to the chosen charity
    private void fundCharity() {
        viewCharities();
        if (charityManager.isEmpty()) {
            return;
        }
        System.out.println("Enter the number of the charity to fund: ");
        int choice = scanner.nextInt();
        if (choice < 1 || choice > charityManager.getCharitiesSize()) {
            System.out.println("Invalid choice!");
            return;
        }
        Charity selectedCharity = charityManager.getCharities().get(choice - 1);
        System.out.println("Enter the amount to fund: $");
        double amount = scanner.nextDouble();
        if (amount < 0) {
            System.out.println("Invalid amount!");
            return;
        }
        selectedCharity.addFunds(amount);
        System.out.println("Thank you for funding " + selectedCharity.getName() + "! Current funds: $"
                + selectedCharity.getCurrentFunds());
    }
}