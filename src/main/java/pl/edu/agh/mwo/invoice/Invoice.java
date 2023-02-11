package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

    private Collection<Product> products = new ArrayList()
    {
    };


    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {
        this.products.add(product);
    }

    public BigDecimal getSubtotal() {
         BigDecimal subtotal = BigDecimal.valueOf(0);

        for (Product product: getProducts()){
            subtotal = subtotal.add(product.getPrice());
                    }
        return subtotal;

    }

    public Collection<Product> getProducts() {
        return products;
    }

    public BigDecimal getTax() {
        BigDecimal subtotal = BigDecimal.valueOf(0);

        for (Product product: getProducts()){
            //;
        }
        return subtotal;


    }

    public BigDecimal getTotal() {

        if (products  == null){
            return BigDecimal.valueOf(0);
        }
        return null;

    }
}
