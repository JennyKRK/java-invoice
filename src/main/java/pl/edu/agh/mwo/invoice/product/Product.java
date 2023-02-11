package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public abstract class Product {
    private final String name;

    private final BigDecimal price;

    private final BigDecimal taxPercent;

    //throw illegal argument exception

    protected Product (String name, BigDecimal price, BigDecimal tax) throws IllegalArgumentException{
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if(price == null ) {
            throw new IllegalArgumentException();
        }
        if(price.signum() < 0 ) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.price = price;
        this.taxPercent = tax;

    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getTaxPercent() {
        return this.taxPercent;
    }

    public BigDecimal getPriceWithTax() {

        return this.price.add(this.price.multiply(this.taxPercent));
    }
}
