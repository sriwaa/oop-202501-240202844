package main.java.com.upb.agripos.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.geometry.Pos; 
import main.java.com.upb.agripos.AppAgriPos; // Import Aplikasi Utama
import main.java.com.upb.agripos.dao.SqlMemberRepository;
import main.java.com.upb.agripos.dao.SqlTransactionRepository;
import main.java.com.upb.agripos.model.*;
import main.java.com.upb.agripos.service.*;
import main.java.com.upb.agripos.service.payment.*;
import main.java.com.upb.agripos.view.MainView;

import java.util.Optional;
import java.util.List;

public class MainController {
    private final ProductService productService;
    private final CartService cartService;
    private final MainView view;
    private final SqlTransactionRepository transRepo; 
    private final SqlMemberRepository memberRepo;
    
    private double currentDiscount = 0.0; 

    public MainController(ProductService ps, CartService cs, SqlTransactionRepository tr, SqlMemberRepository mr, MainView view) {
        this.productService = ps;
        this.cartService = cs;
        this.transRepo = tr;
        this.memberRepo = mr;
        this.view = view;
        
        try {
            initEventHandlers();
            refreshAll(); 
            applyRoleAccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyRoleAccess() {
        if (AuthService.getCurrentUser() != null) {
            String role = AuthService.getCurrentUser().getRole();
            
            if ("KASIR".equalsIgnoreCase(role)) {
                if(view.getTabAdmin() != null) {
                    view.getTabAdmin().setDisable(false);
                    // Kunci Tab Member (Index 1) untuk Kasir
                    if(view.getTabAdmin().getTabs().size() > 1) {
                        view.getTabAdmin().getTabs().get(1).setDisable(true);
                    }
                }
                setDisableIfExists(view.getTxtKode(), true);
                setDisableIfExists(view.getTxtNama(), true);
                setDisableIfExists(view.getTxtHarga(), true);
                setDisableIfExists(view.getTxtStok(), true);
                setDisableIfExists(view.getBtnSimpan(), true);
                setDisableIfExists(view.getBtnUpdate(), true);
                setDisableIfExists(view.getBtnHapus(), true);
                setDisableIfExists(view.getBtnClear(), true);
                setDisableIfExists(view.getBtnLaporan(), true);
                setDisableIfExists(view.getTxtMemId(), true);
                setDisableIfExists(view.getTxtMemNama(), true);
                setDisableIfExists(view.getBtnSimpanMem(), true);
                setDisableIfExists(view.getBtnHapusMem(), true);

                if(view.getTableProduk() != null) view.getTableProduk().setDisable(false);

            } else if ("ADMIN".equalsIgnoreCase(role)) {
                if(view.getAreaKasir() != null) {
                    view.getAreaKasir().setDisable(true);
                    view.getAreaKasir().setOpacity(0.5);
                }
            }
        }
    }

    private void setDisableIfExists(Control control, boolean disable) {
        if (control != null) {
            control.setDisable(disable);
            if (disable) control.setOpacity(0.5);
        }
    }

    private void initEventHandlers() {
        // --- LOGOUT HANDLER (NEW) ---
        if(view.getBtnLogout() != null) {
            view.getBtnLogout().setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Yakin ingin Logout?");
                if(alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    try {
                        // 1. Clear Session
                        AuthService.logout();
                        
                        // 2. Tutup Window Dashboard
                        Stage currentStage = (Stage) view.asParent().getScene().getWindow();
                        currentStage.close();
                        
                        // 3. Buka Login Screen Lagi
                        new AppAgriPos().start(new Stage());
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        // --- ADMIN: MEMBER ---
        if(view.getBtnSimpanMem() != null) {
            view.getBtnSimpanMem().setOnAction(e -> {
                try {
                    String id = view.getTxtMemId().getText();
                    String nama = view.getTxtMemNama().getText();
                    if(id.isEmpty() || nama.isEmpty()) throw new Exception("Isi ID dan Nama!");
                    memberRepo.save(new Member(id, nama, 0));
                    refreshAll();
                    showAlert("Sukses", "Member ditambahkan!");
                } catch(Exception ex) { showAlert("Gagal", ex.getMessage()); }
            });
        }
        
        if(view.getBtnHapusMem() != null) {
            view.getBtnHapusMem().setOnAction(e -> {
                Member m = view.getTableMember().getSelectionModel().getSelectedItem();
                if(m != null) {
                    try { memberRepo.delete(m.getId()); refreshAll(); } catch(Exception ex) {}
                }
            });
        }

        // --- ADMIN: PRODUK ---
        if (view.getBtnLaporan() != null) {
            view.getBtnLaporan().setOnAction(e -> {
                try {
                    double income = transRepo.getDailyIncome();
                    showAlert("Laporan Harian", "Pendapatan: Rp " + String.format("%,.0f", income));
                } catch (Exception ex) { }
            });
        }
        
        if(view.getBtnSimpan()!=null) view.getBtnSimpan().setOnAction(e -> processProduct(1));
        if(view.getBtnUpdate()!=null) view.getBtnUpdate().setOnAction(e -> processProduct(2));
        if(view.getBtnHapus()!=null) view.getBtnHapus().setOnAction(e -> processProduct(3));
        if(view.getBtnClear()!=null) view.getBtnClear().setOnAction(e -> refreshAll());
        
        if(view.getTableProduk()!=null) view.getTableProduk().setOnMouseClicked(e -> {
            Produk p = view.getTableProduk().getSelectionModel().getSelectedItem();
            if(p!=null) {
                if(view.getTxtKode()!=null) { view.getTxtKode().setText(p.getKode()); view.getTxtKode().setDisable(true); }
                if(view.getTxtNama()!=null) view.getTxtNama().setText(p.getNama());
                if(view.getTxtHarga()!=null) view.getTxtHarga().setText(String.valueOf((int)p.getHarga()));
                if(view.getTxtStok()!=null) view.getTxtStok().setText(String.valueOf(p.getStok()));
            }
        });

        // --- KASIR ---
        if(view.getBtnCekMember() != null) {
            view.getBtnCekMember().setOnAction(e -> {
                try {
                    String nama = transRepo.checkMember(view.getTxtMemberId().getText());
                    if(nama != null) {
                        currentDiscount = 0.10;
                        view.getLblDiskon().setText("Diskon: 10% ("+nama+")");
                    } else {
                        currentDiscount = 0.0;
                        view.getLblDiskon().setText("Tidak Ditemukan");
                    }
                    refreshCartView();
                } catch(Exception ex) {}
            });
        }
        
        if(view.getBtnAddToCart()!=null) view.getBtnAddToCart().setOnAction(e -> {
            if(view.getTableProduk() == null) return;
            Produk s = view.getTableProduk().getSelectionModel().getSelectedItem();
            if(s!=null) {
                try {
                    int qty = Integer.parseInt(view.getInputQty().getText());
                    cartService.addToCart(s, qty);
                    refreshCartView();
                } catch(Exception ex) {}
            } else {
                showAlert("Pilih Produk", "Klik produk di tabel sebelah kiri!");
            }
        });
        
        if(view.getBtnHapusCart()!=null) view.getBtnHapusCart().setOnAction(e -> {
            int idx = view.getListCart().getSelectionModel().getSelectedIndex();
            if(idx>=0) { cartService.getCartItems().remove(idx); refreshCartView(); }
        });
        
        if(view.getBtnCheckout()!=null) view.getBtnCheckout().setOnAction(e -> {
            if(cartService.getCartItems().isEmpty()) { showAlert("Info", "Keranjang Kosong!"); return; }
            handleCheckout();
        });
    }

    private void processProduct(int type) {
        try {
            if(view.getTxtKode() == null) return;
            Produk p = new Produk(view.getTxtKode().getText(), view.getTxtNama().getText(),
                    Double.parseDouble(view.getTxtHarga().getText()), Integer.parseInt(view.getTxtStok().getText()));
            if(type==1) productService.addProduct(p);
            else if(type==2) productService.updateProduct(p);
            else productService.deleteProduct(p.getKode());
            refreshAll();
        } catch(Exception e) { showAlert("Info", e.getMessage()); }
    }

    private void refreshCartView() {
        view.getListCart().getItems().clear();
        for(CartItem item : cartService.getCartItems()) view.getListCart().getItems().add(item.toString());
        double subtotal = cartService.calculateTotal();
        double discountAmount = subtotal * currentDiscount;
        view.getLblTotal().setText(String.format("Total: Rp %,.0f (Disc: %,.0f)", subtotal - discountAmount, discountAmount));
    }

    private void handleCheckout() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Tunai", "Tunai", "E-Wallet");
        dialog.setTitle("Bayar"); dialog.setHeaderText("Metode:");
        Optional<String> res = dialog.showAndWait();
        if(res.isPresent()) {
            PaymentStrategy strategy = res.get().equals("Tunai") ? new CashPayment() : new EWalletPayment();
            double total = cartService.calculateTotal() - (cartService.calculateTotal() * currentDiscount);
            if(strategy.processPayment(total)) {
                try {
                    String kasir = (AuthService.getCurrentUser() != null) ? AuthService.getCurrentUser().getUsername() : "Guest";
                    transRepo.saveTransaction(total, strategy.getMethodName(), kasir, cartService.getCartItems());
                    try { productService.processStockReduction(cartService.getCartItems()); } catch(Exception e){}
                    showStruk(strategy.getMethodName(), total, kasir);
                    cartService.clearCart(); currentDiscount = 0; refreshAll();
                } catch(Exception e) { e.printStackTrace(); }
            }
        }
    }

    private void showStruk(String method, double total, String kasir) {
        Stage s = new Stage(); 
        s.initModality(Modality.APPLICATION_MODAL);
        s.setTitle("Cetak Struk");

        StringBuilder sb = new StringBuilder();
        sb.append("      AGRI-POS STORE      \n");
        sb.append("==========================\n");
        sb.append("Kasir   : ").append(kasir).append("\n");
        sb.append("Metode  : ").append(method).append("\n");
        sb.append("--------------------------\n");
        for(CartItem i : cartService.getCartItems()) {
            String nama = i.getProduk().getNama();
            if(nama.length() > 18) nama = nama.substring(0, 18);
            sb.append(String.format("%-18s x%d %8.0f\n", nama, i.getQty(), i.getSubtotal()));
        }
        sb.append("--------------------------\n");
        sb.append(String.format("TOTAL   : Rp %,.0f\n", total));
        sb.append("==========================\n");
        sb.append("    Terima Kasih!        \n");

        TextArea area = new TextArea(sb.toString());
        area.setEditable(false); 
        area.setPrefRowCount(15);
        area.setStyle("-fx-font-family: 'monospaced'; -fx-font-size: 12px;");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10; -fx-background-color: white;");
        layout.getChildren().add(area);

        if("E-Wallet".equalsIgnoreCase(method)) {
            try {
                String qrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=PAY-" + (int)total;
                layout.getChildren().addAll(new Label("Scan QRIS:"), new ImageView(new Image(qrUrl, true)));
            } catch (Exception e) {}
        }
        
        s.setScene(new Scene(layout, 300, 500)); s.show();
    }

    private void refreshAll() {
        try {
            if(view.getTableProduk() != null) {
                view.getTableProduk().getItems().clear();
                view.getTableProduk().getItems().addAll(productService.getAllProducts());
                view.getTableProduk().refresh();
            }
            if(view.getTableMember() != null) {
                view.getTableMember().getItems().clear();
                view.getTableMember().getItems().addAll(memberRepo.findAll());
            }
            refreshCartView();
            view.clearForm();
            if(view.getTxtKode()!=null) view.getTxtKode().setDisable(false);
            
            applyRoleAccess();
        } catch(Exception e) {}
    }

    private void showAlert(String t, String m) { new Alert(Alert.AlertType.INFORMATION, m).show(); }
}