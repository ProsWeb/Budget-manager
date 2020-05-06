package budget;

import budget.controller.FillListController;
import budget.controller.SortingController;
import budget.model.*;
import budget.view.ListWithProductsView;
import budget.view.PurchasesView;
import budget.view.SortingView;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static boolean budgeting = true;

    public static void main(String[] args) throws IOException {

        Balance balance = new Balance();
        LoadFromFile load = new LoadFromFile();
        FillListController fillListController = new FillListController();
        SortingController sortingController = new SortingController();
        ListWithProductsView listWithProductsView = new ListWithProductsView();
        SortingView typesView = new SortingView();
        PurchasesView purchasesView = new PurchasesView();

        launch(balance, load, fillListController, sortingController,
                listWithProductsView, typesView, purchasesView);
    }

    public static void launch(final Balance balance, final LoadFromFile load,
                              final FillListController fillListController,
                              final SortingController sortingController,
                              final ListWithProductsView listView,
                              final SortingView sortTypes,
                              final PurchasesView purchasesView)
            throws IOException {

        Scanner sc = new Scanner(System.in);

        String menu = "\nChoose your action:\n"
                + "1) Add income\n"
                + "2) Add purchase\n"
                + "3) Show list of purchases\n"
                + "4) Balance\n"
                + "5) Save\n"
                + "6) Load\n"
                + "7) Analyze (Sort)\n"
                + "0) Exit;";
        System.out.println(menu);

        while (budgeting) {
            switch (sc.nextInt()) {
                case 1:
                    balance.addAIncome(sc);
                    System.out.println(menu);
                    break;
                case 2:
                    fillListController.addProductsToList(sc, balance);
                    System.out.println(menu);
                    break;
                case 3:
                    purchasesView.chooseList(sc, sortingController,
                            fillListController, listView);
                    System.out.println(menu);
                    break;
                case 4:
                    listView.showBalance(balance);
                    System.out.println(menu);
                    break;
                case 5:
                    fillListController.saveToFile(sortingController, listView);
                    balance.saveIncomeToFile();
                    System.out.println(menu);
                    break;
                case 6:
                    balance.setIncomeFromFile();
                    load.load(fillListController);
                    System.out.println(menu);
                    break;
                case 7:
                    sortTypes.showSortTypes(sc, fillListController,
                            listView, sortingController);
                    System.out.println(menu);
                    break;
                case 0:
                    budgeting = false;
                    System.out.println("\nBye!");
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
}
