package budget.model;

import budget.Util;
import budget.controller.FillListController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class LoadFromFile {

    public void load(final FillListController fillListController)
            throws IOException {

        fillListFromFile(fillListController.getFoodList(), Util.FOOD_LIST_NAME);
        fillListFromFile(fillListController.getClothesList(), Util.CLOTHES_LIST_NAME);
        fillListFromFile(fillListController.getEntertainmentList(), Util.ENTERTAINMENT_LIST_NAME);
        fillListFromFile(fillListController.getOtherList(), Util.OTHER_LIST_NAME);

        System.out.println("\nPurchases were loaded!");
    }

    private void fillListFromFile(
            final Map<String, BigDecimal> listOfPurchases, final String listName)
            throws FileNotFoundException {

        File fileWithPurchases = new File(Util.PATH_TO_FILE);

        try (Scanner sc = new Scanner(fileWithPurchases)) {
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.contains(listName)) {

                    boolean notFilled = true;
                    while (notFilled) {
                        String currentLine = sc.nextLine();
                        if (currentLine.contains("-----")) {
                            notFilled = false;
                            continue;
                        }
                        int dollarIndex = currentLine.lastIndexOf('$');
                        String nameOfProduct =
                                currentLine.substring(0, dollarIndex);
                        BigDecimal costOfProduct =
                                new BigDecimal(currentLine.substring(dollarIndex + 1));
                        listOfPurchases.put(nameOfProduct, costOfProduct);
                    }
                }
            }
        }
    }
}
