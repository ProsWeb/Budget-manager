package budget.view;

import budget.controller.FillListController;
import budget.controller.SortingController;

import java.util.Scanner;

public class SortingView {

    public void showSortTypes(final Scanner sc,
                              final FillListController fillListController,
                              final ListWithProductsView listWithProductsView,
                              final SortingController sort) {

        String sortMenu = "\nHow do you want to sort?\n"
                + "1) Sort all purchases\n"
                + "2) Sort by type\n"
                + "3) Sort certain type\n"
                + "4) Back";
        System.out.println(sortMenu);

        int typeOfSort = sc.nextInt();

        switch (typeOfSort) {
            case 1:
                sort.sortAll(fillListController, listWithProductsView);
                showSortTypes(sc, fillListController, listWithProductsView, sort);
                break;
            case 2:
                sort.sortByType(listWithProductsView, fillListController);
                showSortTypes(sc, fillListController, listWithProductsView, sort);
                break;
            case 3:
                sort.sortCertainType(sc, listWithProductsView, fillListController);
                showSortTypes(sc, fillListController, listWithProductsView, sort);
                break;
            case 4:
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
