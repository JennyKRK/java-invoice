package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FuelCanister extends Product {
    public static final double excise = 5.56;

    public FuelCanister(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.00"));
    }

    @Override
    public BigDecimal getPriceWithTax() {
        return super.getPriceWithTax().add(BigDecimal.valueOf(excise));
    }

    @Override
    public BigDecimal getTax() {
        return super.getTax().add(BigDecimal.valueOf(excise));
    }
}

