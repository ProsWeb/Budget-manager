package budget.controller;

import budget.Util;
import budget.view.ListWithProductsView;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SortingController {

    public void sortAll(final FillListController fillListController,
                        final ListWithProductsView listWithProductsView) {

        Map<String, Double> listWithAllPurchases =
                fillListController.fillListWithAllPurchases();
        if (listWithAllPurchases.isEmpty()) {
            System.out.println("\nPurchase list is empty!");
            return;
        }

        Map<String, Double> sortedList =
                sortListDescendingOrder(listWithAllPurchases);

        System.out.println("\nAll:");
        sortedList.forEach((name, sum) ->
                System.out.println(name + "$" + String.format("%.2f", sum)));

        double sumOfPurchasesInList = getSumOfPurchasesInList(sortedList);
        System.out.println(Util.TOTAL_SUM
                + listWithProductsView.showSum(sumOfPurchasesInList));
    }

    public void sortByType(final ListWithProductsView listWithProductsView,
                           final FillListController fillListController) {

        Map<String, Double> filledList = fillListByType(fillListController);
        Map<String, Double> sortedPurchasesByType =
                sortListDescendingOrder(filledList);

        System.out.println("\nTypes:");
        sortedPurchasesByType.forEach((name, sum) ->
                System.out.println(name + " - $" + String.format("%.2f", sum)));

        double sumOfPurchasesInList =
                getSumOfPurchasesInList(sortedPurchasesByType);
        System.out.println(Util.TOTAL_SUM
                + listWithProductsView.showSum(sumOfPurchasesInList));
    }

    public void sortCertainType(final Scanner sc,
                                final ListWithProductsView listWithProductsView,
                                final FillListController fillListController) {

        String typeOfPurchaseSortMenu = "\nChoose the type of purchase\n"
                + "1) " + Util.FOOD_LIST_NAME + "\n"
                + "2) " + Util.CLOTHES_LIST_NAME + "\n"
                + "3) " + Util.ENTERTAINMENT_LIST_NAME + "\n"
                + "4) " + Util.OTHER_LIST_NAME;

        System.out.println(typeOfPurchaseSortMenu);

        int typeOfPurchase = sc.nextInt();
        switch (typeOfPurchase) {
            case 1:
                Map<String, Double> sortedFoodList =
                        sortListDescendingOrder(fillListController.getFoodList());
                listWithProductsView.showOneList(sortedFoodList, Util.FOOD_LIST_NAME);
                break;
            case 2:
                Map<String, Double> sortedClothesList =
                        sortListDescendingOrder(fillListController.getClothesList());
                listWithProductsView.showOneList(sortedClothesList, Util.CLOTHES_LIST_NAME);
                break;
            case 3:
                Map<String, Double> sortedEntertainmentList =
                        sortListDescendingOrder(fillListController.getEntertainmentList());
                listWithProductsView.showOneList(sortedEntertainmentList, Util.ENTERTAINMENT_LIST_NAME);
                break;
            case 4:
                Map<String, Double> sortedOtherList =
                        sortListDescendingOrder(fillListController.getOtherList());
                listWithProductsView.showOneList(sortedOtherList, Util.OTHER_LIST_NAME);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public double getSumOfPurchasesInList(final Map<String, Double> list) {

        return list.values().stream().reduce(0.0, Double::sum);
    }

    private Map<String, Double> sortListDescendingOrder(
            final Map<String, Double> listWithPurchases) {

        return listWithPurchases.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Map<String, Double> fillListByType(
            final FillListController fillListController) {

        Map<String, Double> allPurchasesList = new LinkedHashMap<>();
        allPurchasesList.put(Util.FOOD_LIST_NAME,
                getSumOfPurchasesInList(fillListController.getFoodList()));
        allPurchasesList.put(Util.CLOTHES_LIST_NAME,
                getSumOfPurchasesInList(fillListController.getClothesList()));
        allPurchasesList.put(Util.ENTERTAINMENT_LIST_NAME,
                getSumOfPurchasesInList(fillListController.getEntertainmentList()));
        allPurchasesList.put(Util.OTHER_LIST_NAME,
                getSumOfPurchasesInList(fillListController.getOtherList()));

        return allPurchasesList;
    }
}
