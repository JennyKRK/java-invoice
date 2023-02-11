package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.Collection;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

    private Collection<Product> products;


    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {
        //
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.valueOf(0);

        for (Product product: getProducts()){
            subtotal.add(product.getPrice());
        }
        return subtotal;

    }

    public Collection<Product> getProducts() {
        return products;
    }

    public BigDecimal getTax() {
        return null;
    }

    public BigDecimal getTotal() {
        return null;
    }
}
