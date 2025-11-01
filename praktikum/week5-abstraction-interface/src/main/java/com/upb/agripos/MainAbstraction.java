package com.upb.agripos;

import com.upb.agripos.model.pembayaran.*;
import com.upb.agripos.model.kontrak.*;
import com.upb.agripos.util.CreditBy;

public class MainAbstraction {
    public static void main(String[] args) {
        Pembayaran cash = new Cash("WWA-001", 200000, 250000);
        System.out.println(((Receiptable) cash).cetakStruk());

        Pembayaran ew = new EWallet("WWA-002", 221300, "wawa@ewallet", "020505");
        System.out.println(((Receiptable) ew).cetakStruk());

        Pembayaran transfer = new TransferBank("WWA-003", 200000, "wawa@bank", "112255");
        System.out.println(((Receiptable) transfer).cetakStruk());

        CreditBy.print();
    }
}
