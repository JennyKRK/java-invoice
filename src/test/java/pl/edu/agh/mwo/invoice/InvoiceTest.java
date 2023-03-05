package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.Invoice;
import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.Product;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

public class InvoiceTest {
    private Invoice invoice;

    @Before
    public void createEmptyInvoiceForTheTest() {
        invoice = new Invoice();
    }

    @Test
    public void testEmptyInvoiceHasEmptySubtotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTaxAmount() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTaxTotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceSubtotalWithTwoDifferentProducts() {
        Product onions = new TaxFreeProduct("Warzywa", new BigDecimal("10"));
        Product apples = new TaxFreeProduct("Owoce", new BigDecimal("10"));
        invoice.addProduct(onions);
        invoice.addProduct(apples);
        Assert.assertThat(new BigDecimal("20"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceSubtotalWithManySameProducts() {
        Product onions = new TaxFreeProduct("Warzywa", BigDecimal.valueOf(10));
        invoice.addProduct(onions, 100);
        Assert.assertThat(new BigDecimal("1000"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceHasTheSameSubtotalAndTotalIfTaxIsZero() {
        Product taxFreeProduct = new TaxFreeProduct("Warzywa", new BigDecimal("199.99"));
        invoice.addProduct(taxFreeProduct);
        Assert.assertThat(invoice.getNetTotal(), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceHasProperSubtotalForManyProducts() {
        invoice.addProduct(new TaxFreeProduct("Owoce", new BigDecimal("200")));
        invoice.addProduct(new DairyProduct("Maslanka", new BigDecimal("100")));
        invoice.addProduct(new OtherProduct("Wino", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("310"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceHasProperTaxValueForManyProduct() {
        // tax: 0
        invoice.addProduct(new TaxFreeProduct("Pampersy", new BigDecimal("200")));
        // tax: 8
        invoice.addProduct(new DairyProduct("Kefir", new BigDecimal("100")));
        // tax: 2.30
        invoice.addProduct(new OtherProduct("Piwko", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("10.30"), Matchers.comparesEqualTo(invoice.getTaxTotal()));
    }

    @Test
    public void testInvoiceHasProperTotalValueForManyProduct() {
        // price with tax: 200
        invoice.addProduct(new TaxFreeProduct("Maskotki", new BigDecimal("200")));
        // price with tax: 108
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("100")));
        // price with tax: 12.30
        invoice.addProduct(new OtherProduct("Chipsy", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("320.30"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceHasPropoerSubtotalWithQuantityMoreThanOne() {
        // 2x kubek - price: 10
        invoice.addProduct(new TaxFreeProduct("Kubek", new BigDecimal("5")), 2);
        // 3x kozi serek - price: 30
        invoice.addProduct(new DairyProduct("Kozi Serek", new BigDecimal("10")), 3);
        // 1000x pinezka - price: 10
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("50"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceHasPropoerTotalWithQuantityMoreThanOne() {
        // 2x chleb - price with tax: 10
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
        // 3x chedar - price with tax: 32.40
        invoice.addProduct(new DairyProduct("Chedar", new BigDecimal("10")), 3);
        // 1000x pinezka - price with tax: 12.30
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("54.70"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithZeroQuantity() {
        invoice.addProduct(new TaxFreeProduct("Tablet", new BigDecimal("1678")), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithNegativeQuantity() {
        invoice.addProduct(new DairyProduct("Zsiadle mleko", new BigDecimal("5.55")), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullProduct() {
        invoice.addProduct(null);
    }

    @Test
    public void invoiceHasNumber() {
        int number = invoice.getNumber();
        Assert.assertNotNull(number);
    }

    @Test
    public void invoiceNumberGreaterThan0() {
        int number = invoice.getNumber();
        Assert.assertTrue(number>0);
    }

    @Test
    public void invoiceNumberDifferent() {
        int number1 = invoice.getNumber();
        int number2 = new Invoice().getNumber();
        Assert.assertNotEquals(number1, number2);
    }

    @Test
    public void textToPrintNotEmpty() {
        List<String> linesToPrint = invoice.getTextToPrint();
        Assert.assertNotNull(linesToPrint);
    }

    @Test
    public void textToPrintForEmptyInvoice() {
        String header = String.valueOf(invoice.getNumber());
        List<String> printTextForEmptyInvoice = Arrays.asList(header,"Liczba pozycji: 0");
        Assert.assertEquals(printTextForEmptyInvoice,invoice.getTextToPrint());
    }

    @Test
    public void textToPrintForInvoiceWith1Product() {
        invoice.addProduct(new TaxFreeProduct("Tablet", new BigDecimal("1678")),2);
        String header = String.valueOf(invoice.getNumber());
        String body = "Tablet sztuk: 2 cena 1678";
        String footer = "Liczba pozycji: 1";
        List<String> printTextForInvoiceWith1Product = Arrays.asList(header, body, footer);
        Assert.assertEquals(printTextForInvoiceWith1Product, invoice.getTextToPrint());
    }

    @Test
    public void textToPrintCheckFooter() {
        invoice.addProduct(new TaxFreeProduct("Tablet", new BigDecimal("1678")),2);
        invoice.addProduct(new TaxFreeProduct("Smartfon", new BigDecimal("500")),3);
        String footer = "Liczba pozycji: 2";
        Assert.assertEquals(footer, invoice.getTextToPrint().get(3));
    }

    @Test
    public void textToPrintCheckHeader() {
        String header = String.valueOf(invoice.getNumber());
        Assert.assertEquals(header, invoice.getTextToPrint().get(0));
    }

    @Test
    public void duplicatesCheckQuantity() {
        Product p = new TaxFreeProduct("Tablet", new BigDecimal("1678"));
        invoice.addProduct(p,2);
        invoice.addProduct(p,1);
        Map<Product,Integer> products = invoice.getProducts();
        int foundQuantity = products.get(p);
        Assert.assertEquals(3, foundQuantity);
    }

    @Test
    public void duplicatesCheckTotal() {
        Product p = new TaxFreeProduct("Tablet", new BigDecimal("1678"));
        invoice.addProduct(p,2);
        invoice.addProduct(p,1);
        BigDecimal totalPrice = invoice.getGrossTotal();
        Assert.assertEquals(BigDecimal.valueOf(5034), totalPrice);
    }

    @Test
    public void duplicatesCheckUnique(){
        Product p = new TaxFreeProduct("Tablet", new BigDecimal("1678"));
        Product p2 = new DairyProduct("Milk", new BigDecimal("20"));
        Product p3 = new DairyProduct("Cheese", new BigDecimal("30"));
        invoice.addProduct(p,1);
        invoice.addProduct(p2,3);
        invoice.addProduct(p,3);
        invoice.addProduct(p2,4);
        invoice.addProduct(p3,2);
        Assert.assertEquals(3,invoice.getProducts().size());

    }
}
