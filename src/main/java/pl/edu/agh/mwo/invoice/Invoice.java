package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

    public Map<Product, Integer> getProducts() {
        return products;
    }

    private Map<Product, Integer> products = new HashMap<>();


    //throw illegal argument exception

    public void addProduct(Product product) throws IllegalArgumentException{
        if(product == null ) {
            throw new IllegalArgumentException();
        }

        this.products.put(product,1);


    }

    //throw illegal argument exception

    public void addProduct(Product product, Integer quantity) throws IllegalArgumentException{
        if(product == null ) {
            throw new IllegalArgumentException();
        }

        if(quantity == 0 || quantity < 0 ) {
            throw new IllegalArgumentException();
        }
        this.products.put(product, quantity);


    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.valueOf(0);
        Map<Product, Integer> products = getProducts();

        for (Product product: products.keySet()){
            int quantity = products.get(product);
            BigDecimal totalNetPriceForProduct = BigDecimal.valueOf(quantity).multiply(product.getPrice());
            subtotal = subtotal.add(totalNetPriceForProduct);
        }
        return subtotal;
    }

    public BigDecimal getTax() {
        BigDecimal subtotal = BigDecimal.valueOf(0);
        Map<Product, Integer> products = getProducts();

        for (Product product: products.keySet()){
            int quantity = products.get(product);
            BigDecimal taxValue = product.getTaxPercent().multiply(product.getPrice());
            BigDecimal totalTaxForProduct = BigDecimal.valueOf(quantity).multiply(taxValue);
            subtotal = subtotal.add(totalTaxForProduct);
        }
        return subtotal;
    }

    public BigDecimal getTotal() {
        BigDecimal subtotal = BigDecimal.valueOf(0);
        Map<Product, Integer> products = getProducts();

        for (Product product: products.keySet()){
            int quantity = products.get(product);
            BigDecimal taxValue = product.getTaxPercent().multiply(product.getPrice());
            BigDecimal grossPrice = product.getPrice().add(taxValue);
            BigDecimal totalTaxForProduct = BigDecimal.valueOf(quantity).multiply(grossPrice);
            subtotal = subtotal.add(totalTaxForProduct);
        }
        return subtotal;
    }
}
