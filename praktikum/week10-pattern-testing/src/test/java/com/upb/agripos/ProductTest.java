package test.java.com.upb.agripos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.upb.agripos.model.Product;

public class ProductTest {

    @Test
    public void testProductName() {
        Product p = new Product("P01", "Benih Jagung");
        assertEquals("Benih Jagung", p.getName(), "Nama produk harus sesuai");
    }

    @Test
    public void testProductCode() {
        Product p = new Product("P02", "Pupuk Urea");
        assertEquals("P02", p.getCode(), "Kode produk harus sesuai");
    }
}
