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
        this.income += (double) Math.round(sc.nextDouble() * 100d) / 100d;
        System.out.println("Income was added!");
    }

    public void setIncomeFromFile() {

        File fileWithPurchases = new File(Util.PATH_TO_FILE);

        try (Scanner sc = new Scanner(fileWithPurchases)) {
            while (sc.hasNext()) {
                String currentLine = sc.nextLine();
                if (currentLine.contains("Balance")) {
                    double balanceFromFile =
                            Double.parseDouble(currentLine.split("\\$")[1]);
                    this.income = (double) Math.round(balanceFromFile * 100d) / 100d;
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
        this.income -= (double) Math.round(costOfProduct * 100d) / 100d;
    }

    public double getIncome() {
        return (double) Math.round(this.income * 100d) / 100d;
    }
}
