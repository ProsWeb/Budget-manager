package budget.model;

import budget.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class Balance {

    private BigDecimal income = new BigDecimal("0.0");

    public void addAIncome(final Scanner sc) {

        System.out.println("\nEnter income:");
        BigDecimal amount = sc.nextBigDecimal();
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            income = income.add(amount);
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
                    BigDecimal balanceFromFile =
                            new BigDecimal(currentLine.split("\\$")[1]);
                    if (balanceFromFile.compareTo(BigDecimal.ZERO) > 0) {
                        income = balanceFromFile;
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
            writer.write("\nBalance: $" + income);
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }

    public void subtractIncome(final BigDecimal costOfProduct) {

        if (income.compareTo(costOfProduct) > 0) {
            income = income.subtract(costOfProduct);
            System.out.println("Purchase was added!");

            return;
        }

        System.out.println("You can't afford this purchase\n"
                            + "Your income: " + income);
    }

    public BigDecimal getIncome() {
        return income;
    }
}
