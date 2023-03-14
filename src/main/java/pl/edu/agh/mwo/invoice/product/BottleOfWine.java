package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BottleOfWine extends Product {
    public static final double excise = 5.56;

    public BottleOfWine(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.23"));
    }

    @Override
    public BigDecimal getPriceWithTax() {
        return super.getPriceWithTax().add(BigDecimal.valueOf(excise));
    }

}



