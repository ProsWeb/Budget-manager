package budget.view;

import budget.Util;
import budget.model.Balance;
import budget.controller.FillListController;

import java.util.Map;

public class ListWithProductsView {

    public void showOneList(final Map<String, Double> listOfProducts,
                            final String listName) {

        if (listOfProducts.isEmpty()) {
            System.out.println("\nPurchase list is empty");
            return;
        }

        System.out.println("\n" + listName + ":");
        listOfProducts.forEach((name, cost) ->
                System.out.println(name + " $" + String.format("%.2f", cost)));

        double sumOfPurchasesInList = getSumOfPurchasesInList(listOfProducts);
        System.out.println(Util.TOTAL_SUM + showSum(sumOfPurchasesInList));
    }

    public void showTotalList(final FillListController fillListController) {

        System.out.println("\nAll:");
        show(fillListController.getFoodList(), Util.FOOD_LIST_NAME);
        show(fillListController.getClothesList(), Util.CLOTHES_LIST_NAME);
        show(fillListController.getEntertainmentList(), Util.ENTERTAINMENT_LIST_NAME);
        show(fillListController.getOtherList(), Util.OTHER_LIST_NAME);

        Map<String, Double> listWithAllProducts =
                fillListController.fillListWithAllPurchases();
        double sumOfPurchasesInAllLists =
                getSumOfPurchasesInList(listWithAllProducts);
        System.out.println(Util.TOTAL_SUM + showSum(sumOfPurchasesInAllLists));
    }

    public String showSum(final double sum) {
        return String.format("%.2f", sum);
    }

    public void showBalance(final Balance b) {
        System.out.println("\nBalance: $" + b.getIncome());
    }

    private void show(
            final Map<String, Double> listOfProducts, final String listName) {

        if (listOfProducts.isEmpty()) {
            return;
        }

        System.out.println(listName + ":");
        listOfProducts.forEach((name, cost) ->
                System.out.println(name + " $" + String.format("%.2f", cost)));
    }

    private double getSumOfPurchasesInList(final Map<String, Double> list) {

        return list.values().stream().reduce(0.0, Double::sum);
    }
}
