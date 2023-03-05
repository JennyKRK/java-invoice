package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.product.Product;

public class ProductTest {
    @Test
    public void testProductNameIsCorrect() {
        Product product = new OtherProduct("buty", new BigDecimal("100.0"));
        Assert.assertEquals("buty", product.getName());
    }

    @Test
    public void testProductPriceAndTaxWithDefaultTax() {
        Product product = new OtherProduct("Ogorki", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.23"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndTaxWithDairyProduct() {
        Product product = new DairyProduct("Szarlotka", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.08"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testPriceWithTax() {
        Product product = new DairyProduct("Oscypek", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("108"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullName() {
        new OtherProduct(null, new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithEmptyName() {
        new TaxFreeProduct("", new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullPrice() {
        new DairyProduct("Banany", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNegativePrice() {
        new TaxFreeProduct("Mandarynki", new BigDecimal("-1.00"));
    }

    @Test
    public void testTotalPriceForBottle(){
        Product product = new BottleOfWine("Bottle1",new BigDecimal(100.00));
        Assert.assertThat(new BigDecimal("128.56"), Matchers.comparesEqualTo(product.getPriceWithTax()));
     }

    @Test
    public void testTotalPriceForFuel(){
        Product product = new FuelCanister("Fuel1",new BigDecimal(100.00));
        Assert.assertThat(new BigDecimal("105.56"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test
    public void testGetTotalTaxFuel() {
        Product product = new FuelCanister("Fuel1",new BigDecimal(100.00));
        Assert.assertThat(new BigDecimal("5.56"), Matchers.comparesEqualTo(product.getTax()));
    }

    @Test
    public void testGetTotalTaxWine() {
        Product product = new BottleOfWine("Bottle1",new BigDecimal(100.00));
        Assert.assertThat(new BigDecimal("28.56"), Matchers.comparesEqualTo(product.getTax()));
    }

    @Test
    public void testGetTaxOther() {
        Product product = new OtherProduct("Ogorki", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("23"), Matchers.comparesEqualTo(product.getTax()));
    }

    @Test
    public void testGetTaxDairy() {
        Product product = new DairyProduct("Oscypek", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("8"), Matchers.comparesEqualTo(product.getTax()));
    }
}
