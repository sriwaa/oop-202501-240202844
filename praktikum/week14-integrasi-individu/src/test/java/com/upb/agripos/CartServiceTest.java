package com.upb.agripos;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartServiceTest {

    @Test
    void testTotal() {
        CartService cs = new CartService();
        cs.add(new Product("P1", "Test", 1000, 10));
        assertEquals(1000, cs.total());
    }
}
