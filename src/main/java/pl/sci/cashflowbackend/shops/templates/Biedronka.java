package pl.sci.cashflowbackend.shops.templates;

import pl.sci.cashflowbackend.expenses.dto.ExpensesDto2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Biedronka {
    /**
     * A function takes a receipt and returns its date.
     * @param lines receipt lines in ArrayList<String>
     * @return receipt date
     */
    public static LocalDate getDate(ArrayList<String> lines){
        Pattern pattern = Pattern.compile("\\d{4}.\\d{2}.\\d{2}");
        LocalDate date = null;

        for(int i = 0; i < lines.size(); i++){
            Matcher matcher = pattern.matcher(lines.get(i));
            if(matcher.find()){
                String[] linesList = lines.get(i).split(" ");
                String[] dateFragments = linesList[0].split("\\.");
                String dateStr = dateFragments[2] + "-" + dateFragments[1] + "-" + dateFragments[0];
                date = LocalDate.parse(dateStr);
                break;
            }
        }
        return date;
    }

    /**
     *  A function takes a receipt and returns its products and cost each product
     * @param lines receipt lines in ArrayList
     * @return ArrayList Expenses objects
     */
    public static ArrayList<ExpensesDto2> getProducts(ArrayList<String> lines){
        ArrayList<ExpensesDto2> expensesArrayList = new ArrayList<>();

        lines.forEach( line -> {
            if (line.contains("x")) {
                String product = null;
                BigDecimal cost;
                String costString = null;

                if(line.contains(" A ")){
                    String[] parts = line.split(" A ");
                    String[] secoundPart = parts[1].split(" ");
                    product = parts[0];
                    costString = secoundPart[secoundPart.length - 1];
                }else if(line.contains(" B ")){
                    String[] parts = line.split(" B ");
                    String[] secoundPart = parts[1].split(" ");
                    product = parts[0];
                    costString = secoundPart[secoundPart.length - 1];
                }else if(line.contains(" C ")){
                    String[] parts = line.split(" C ");
                    String[] secoundPart = parts[1].split(" ");
                    product = parts[0];
                    costString = secoundPart[secoundPart.length - 1];
                }else if(line.contains(" D ")){
                    String[] parts = line.split(" D ");
                    String[] secoundPart = parts[1].split(" ");
                    product = parts[0];
                    costString = secoundPart[secoundPart.length - 1];
                }else if(line.contains(" E ")){
                    String[] parts = line.split(" E ");
                    String[] secoundPart = parts[1].split(" ");
                    product = parts[0];
                    costString = secoundPart[secoundPart.length - 1];
                }else if(line.contains(" F ")){
                    String[] parts = line.split(" F ");
                    String[] secoundPart = parts[1].split(" ");
                    product = parts[0];
                    costString = secoundPart[secoundPart.length - 1];
                }else if(line.contains(" G ")){
                    String[] parts = line.split(" G ");
                    String[] secoundPart = parts[1].split(" ");
                    product = parts[0];
                    costString = secoundPart[secoundPart.length - 1];
                }

                cost = new BigDecimal(Double.parseDouble(costString.replace(",", ".")))
                        .setScale(2, RoundingMode.HALF_UP);

                ExpensesDto2 expenses = new ExpensesDto2();
                expenses.setName(product);
                expenses.setCost(cost);
                expensesArrayList.add(expenses);

            }
        });
        return expensesArrayList;
    }
}
