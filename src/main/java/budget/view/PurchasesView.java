package budget.view;

import budget.Util;
import budget.controller.FillListController;
import budget.controller.SortingController;

import java.util.Map;
import java.util.Scanner;

public class PurchasesView {

    public void chooseList(final Scanner sc,
                           final SortingController sortingController,
                           final FillListController fillListController,
                           final ListWithProductsView listWithProductsView) {

        Map<String, Double> listWithAllPurchases =
                fillListController.fillListWithAllPurchases();
        boolean allListsAreEmpty = sortingController
                        .getSumOfPurchasesInList(listWithAllPurchases) == 0.0;
        if (allListsAreEmpty) {
            System.out.println("\nPurchase list is empty");
            return;
        }

        String typeOfListMenu = "\nChoose the type of purchases\n"
                + "1) " + Util.FOOD_LIST_NAME + "\n"
                + "2) " + Util.CLOTHES_LIST_NAME + "\n"
                + "3) " + Util.ENTERTAINMENT_LIST_NAME + "\n"
                + "4) " + Util.OTHER_LIST_NAME + "\n"
                + "5) All\n" + "6) Back";

        System.out.println(typeOfListMenu);

        int typeOfList = sc.nextInt();
        switch (typeOfList) {
            case 1:
                listWithProductsView.showOneList(
                        fillListController.getFoodList(), Util.FOOD_LIST_NAME);
                chooseList(sc, sortingController, fillListController, listWithProductsView);
                break;
            case 2:
                listWithProductsView.showOneList(
                        fillListController.getClothesList(), Util.CLOTHES_LIST_NAME);
                chooseList(sc, sortingController, fillListController, listWithProductsView);
                break;
            case 3:
                listWithProductsView.showOneList(
                        fillListController.getEntertainmentList(), Util.ENTERTAINMENT_LIST_NAME);
                chooseList(sc, sortingController, fillListController, listWithProductsView);
                break;
            case 4:
                listWithProductsView.showOneList(
                        fillListController.getOtherList(), Util.OTHER_LIST_NAME);
                chooseList(sc, sortingController, fillListController, listWithProductsView);
                break;
            case 5:
                listWithProductsView
                        .showTotalList(fillListController);
                chooseList(sc, sortingController, fillListController, listWithProductsView);
                break;
            case 6:
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
