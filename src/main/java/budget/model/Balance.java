package budget.model;

import budget.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Balance {

    private double income = 0.0;

    public void addAIncome(final Scanner sc) {

        System.out.println("\nEnter income:");
        double amount = sc.nextDouble();
        if (amount > 0) {
            this.income += (double) Math.round(amount * 100d) / 100d;
            System.out.println("Income was added!");
            return;
        }

        System.out.println("Income can't be negative");
    }

    public void setIncomeFromFile() {

        File fileWithPurchases = new File(Util.PATH_TO_FILE);

        try (Scanner sc = new Scanner(fileWithPurchases)) {
            while (sc.hasNext()) {
                String currentLine = sc.nextLine();
                if (currentLine.contains("Balance")) {
                    double balanceFromFile =
                            Double.parseDouble(currentLine.split("\\$")[1]);
                    if (balanceFromFile > 0) {
                        this.income = (double) Math.round(balanceFromFile * 100d) / 100d;
                    } else {
                        System.out.print("Can't load negative income from file.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }

    public void saveIncomeToFile() {

        File fileWithPurchases = new File(Util.PATH_TO_FILE);

        try (FileWriter writer = new FileWriter(fileWithPurchases, true)) {
            writer.write("\nBalance: $"
                    + (double) Math.round(this.income * 100d) / 100d);
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }

    public void subtractIncome(final double costOfProduct) {
        if (income >= costOfProduct) {
            this.income -= (double) Math.round(costOfProduct * 100d) / 100d;
            System.out.println("Purchase was added!");
            return;
        }

        System.out.println("You can't afford this purchase\n"
                            + "Your income: " + income);
    }

    public double getIncome() {
        return (double) Math.round(this.income * 100d) / 100d;
    }
}
