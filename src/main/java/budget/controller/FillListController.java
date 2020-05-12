package budget.controller;

import budget.Util;
import budget.model.Balance;
import budget.view.ListWithProductsView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class FillListController {

    private final Map<String, BigDecimal> foodList = new LinkedHashMap<>();
    private final Map<String, BigDecimal> clothesList = new LinkedHashMap<>();
    private final Map<String, BigDecimal> entertainmentList = new LinkedHashMap<>();
    private final Map<String, BigDecimal> otherList = new LinkedHashMap<>();

    public Map<String, BigDecimal> getFoodList() {
        return foodList;
    }
    public Map<String, BigDecimal> getClothesList() {
        return clothesList;
    }
    public Map<String, BigDecimal> getEntertainmentList() {
        return entertainmentList;
    }
    public Map<String, BigDecimal> getOtherList() {
        return otherList;
    }

    public Map<String, BigDecimal> fillListWithAllPurchases() {

        Map<String, BigDecimal> list = new LinkedHashMap<>();
        list.putAll(foodList);
        list.putAll(clothesList);
        list.putAll(entertainmentList);
        list.putAll(otherList);

        return list;
    }

    public void addProductsToList(final Scanner sc, final Balance balance) {

        String typeOfPurchaseMenu = "\nChoose the type of purchase\n"
                + "1) " + Util.FOOD_LIST_NAME + "\n"
                + "2) " + Util.CLOTHES_LIST_NAME + "\n"
                + "3) " + Util.ENTERTAINMENT_LIST_NAME + "\n"
                + "4) " + Util.OTHER_LIST_NAME + "\n"
                + "5) Back";

        System.out.println(typeOfPurchaseMenu);

        int typeOfPurchase = sc.nextInt();
        switch (typeOfPurchase) {
            case 1:
                addProducts(sc, foodList, balance);
                addProductsToList(sc, balance);
                break;
            case 2:
                addProducts(sc, clothesList, balance);
                addProductsToList(sc, balance);
                break;
            case 3:
                addProducts(sc, entertainmentList, balance);
                addProductsToList(sc, balance);
                break;
            case 4:
                addProducts(sc, otherList, balance);
                addProductsToList(sc, balance);
                break;
            case 5:
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void saveToFile(final SortingController sortingController,
                           final ListWithProductsView listWithProductsView) {

        File fileWithPurchases = new File(Util.PATH_TO_FILE);

        try (FileWriter writer = new FileWriter(fileWithPurchases)) {

            writer.write("All:");

            saveList(foodList, Util.FOOD_LIST_NAME, writer);
            saveList(clothesList, Util.CLOTHES_LIST_NAME, writer);
            saveList(entertainmentList, Util.ENTERTAINMENT_LIST_NAME, writer);
            saveList(otherList, Util.OTHER_LIST_NAME, writer);

            BigDecimal sumOfPurchasesInAllLists = sortingController
                    .getSumOfPurchasesInList(fillListWithAllPurchases());

            writer.write("\n" + Util.TOTAL_SUM
                    + listWithProductsView.showSum(sumOfPurchasesInAllLists));

            System.out.println("Purchases were saved!");
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }

    private void addProducts(final Scanner sc,
                             final Map<String, BigDecimal> listOfPurchases,
                             final Balance balance) {

        System.out.println("\nEnter purchase name:");
        String nameOfProduct = sc.next() + sc.nextLine();
        System.out.println("Enter its price:");
        BigDecimal costOfProduct = new BigDecimal(sc.nextLine());

        listOfPurchases.put(nameOfProduct, costOfProduct);
        balance.subtractIncome(costOfProduct);
    }

    private void saveList(final Map<String, BigDecimal> listOfProducts,
                  final String listName, final FileWriter writer)
            throws IOException {

        if (listOfProducts.isEmpty()) {
            return;
        }

        writer.write("\n" + listName + ":");

        listOfProducts.forEach((name, cost) -> {
            try {
                writer.write("\n" + name + " $" + cost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.write("\n-----");
    }
}
