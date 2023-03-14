package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private LinkedHashMap<Product, Integer> products = new LinkedHashMap<Product, Integer>();
    private static int nextNumber = 0;
    private final int number = ++ nextNumber;

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0)         {
            throw new IllegalArgumentException();
        }
        if (products.containsKey(product)) {
            int newQuantity = products.get(product) + quantity;
            products.replace(product, newQuantity);
        } else                               {
            products.put(product, quantity);
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getInvoiceNumber() {
        return number;
    }

    public LinkedHashMap<Product, Integer> getProducts() {
        return products;
    }

    public ArrayList<String> getTextToPrint() {
        ArrayList<String> textToPrint = new ArrayList<>();
        int counter = 0;
        textToPrint.add(String.valueOf(number));
        for (Product p: products.keySet()) {
            int numberOfUnits = products.get(p);
            BigDecimal totalPrice = p.getPrice().multiply(BigDecimal.valueOf(numberOfUnits));
            textToPrint.add(p.getName() + " sztuk: " + numberOfUnits + " cena razem " + totalPrice);
            counter++;
        }
        textToPrint.add("Liczba pozycji: " + counter);
        return textToPrint;
    }

}
