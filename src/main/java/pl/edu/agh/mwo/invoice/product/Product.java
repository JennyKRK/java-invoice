package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public abstract class Product {
    private final String name;

    private final BigDecimal price;

    private final BigDecimal taxPercent;

    protected Product (String name, BigDecimal price, BigDecimal tax) throws IllegalArgumentException{
        this.name = name;
        this.price = price;
        this.taxPercent = tax;   //if(name==null || name.equals("")
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if(price == null ) {
            throw new IllegalArgumentException();
        }
        if(price.signum() < 0 ) {   //can also use price.compareTo(BigDecimal.ZERO)== -1
            throw new IllegalArgumentException();
        }
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
