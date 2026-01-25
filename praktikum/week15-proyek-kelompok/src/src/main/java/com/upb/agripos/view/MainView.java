package main.java.com.upb.agripos.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.java.com.upb.agripos.model.Member;
import main.java.com.upb.agripos.model.Produk;

public class MainView {
    // === HEADER (ATAS) ===
    private HBox headerBar;
    private Button btnLogout;
    private Label lblTitle;

    // === ADMIN AREA (TAB PANE) ===
    private TabPane tabAdmin;
    private TableView<Produk> tableProduk;
    private TextField txtKode, txtNama, txtHarga, txtStok;
    private Button btnSimpan, btnUpdate, btnHapus, btnClear, btnLaporan;

    // TAB 2: MEMBER
    private TableView<Member> tableMember;
    private TextField txtMemId, txtMemNama;
    private Button btnSimpanMem, btnHapusMem;

    // === KASIR AREA ===
    private VBox areaKasir;
    private ListView<String> listCart;
    private Label lblTotal;
    private Button btnAddToCart, btnHapusCart, btnCheckout;
    private TextField txtQty;
    
    // Member Check
    private TextField txtMemberId;
    private Button btnCekMember;
    private Label lblDiskon;

    private BorderPane layout;

    public MainView() { initUI(); }

    private void initUI() {
        // --- 0. HEADER (LOGOUT) ---
        lblTitle = new Label("Sistem Agri-POS");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        btnLogout = new Button("LOGOUT");
        btnLogout.setStyle("-fx-base: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        
        // Spacer agar judul di kiri, logout di kanan
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        headerBar = new HBox(10, lblTitle, spacer, btnLogout);
        headerBar.setPadding(new Insets(10));
        headerBar.setAlignment(Pos.CENTER_LEFT);
        headerBar.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 0 0 2 0;");

        // --- 1. ADMIN: PRODUK ---
        tableProduk = new TableView<>();
        setupTableProduk();
        
        txtKode = new TextField(); txtKode.setPromptText("Kode");
        txtNama = new TextField(); txtNama.setPromptText("Nama");
        txtHarga = new TextField(); txtHarga.setPromptText("Harga");
        txtStok = new TextField(); txtStok.setPromptText("Stok");
        
        btnSimpan = new Button("Simpan");
        btnUpdate = new Button("Update");
        btnHapus = new Button("Hapus");
        btnClear = new Button("Reset");
        btnLaporan = new Button("LIHAT LAPORAN HARIAN");
        btnLaporan.setMaxWidth(Double.MAX_VALUE);
        btnLaporan.setStyle("-fx-base: orange; -fx-text-fill: white;");

        HBox formProd1 = new HBox(5, txtKode, txtNama);
        HBox formProd2 = new HBox(5, txtHarga, txtStok);
        HBox btnProd = new HBox(5, btnSimpan, btnUpdate, btnHapus, btnClear);
        
        VBox layoutProduk = new VBox(10, btnLaporan, tableProduk, formProd1, formProd2, btnProd);
        layoutProduk.setPadding(new Insets(10));
        Tab tab1 = new Tab("Kelola Produk", layoutProduk);
        tab1.setClosable(false);

        // --- 2. ADMIN: MEMBER ---
        tableMember = new TableView<>();
        setupTableMember();
        
        txtMemId = new TextField(); txtMemId.setPromptText("ID Member");
        txtMemNama = new TextField(); txtMemNama.setPromptText("Nama Member");
        btnSimpanMem = new Button("Tambah");
        btnHapusMem = new Button("Hapus");
        
        HBox formMem = new HBox(5, txtMemId, txtMemNama);
        HBox btnMem = new HBox(5, btnSimpanMem, btnHapusMem);
        VBox layoutMember = new VBox(10, new Label("Daftar Member"), tableMember, formMem, btnMem);
        layoutMember.setPadding(new Insets(10));
        Tab tab2 = new Tab("Kelola Member", layoutMember);
        tab2.setClosable(false);

        tabAdmin = new TabPane(tab1, tab2);
        tabAdmin.setPrefWidth(500);
        tabAdmin.setStyle("-fx-border-color: #ccc; -fx-border-width: 0 2 0 0;");

        // --- 3. KASIR AREA ---
        listCart = new ListView<>();
        lblTotal = new Label("Total: Rp 0");
        lblTotal.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        txtMemberId = new TextField(); txtMemberId.setPromptText("ID Member");
        btnCekMember = new Button("Cek");
        lblDiskon = new Label("Diskon: 0%");
        HBox memberBox = new HBox(5, txtMemberId, btnCekMember, lblDiskon);

        txtQty = new TextField("1"); txtQty.setPromptText("Qty");
        btnAddToCart = new Button("Tambah");
        btnHapusCart = new Button("Hapus");
        btnCheckout = new Button("BAYAR");
        btnCheckout.setStyle("-fx-base: green; -fx-text-fill: white;");

        HBox cartRow = new HBox(5, new Label("Qty:"), txtQty, btnAddToCart, btnHapusCart);
        areaKasir = new VBox(10, new Label("=== KASIR ==="), memberBox, cartRow, listCart, lblTotal, btnCheckout);
        areaKasir.setPadding(new Insets(10));
        areaKasir.setPrefWidth(350);

        // --- GABUNGKAN ---
        layout = new BorderPane();
        layout.setTop(headerBar); // HEADER DI ATAS
        layout.setCenter(tabAdmin);
        layout.setRight(areaKasir);
    }

    private void setupTableProduk() {
        TableColumn<Produk, String> c1 = new TableColumn<>("Kode"); c1.setCellValueFactory(new PropertyValueFactory<>("kode"));
        TableColumn<Produk, String> c2 = new TableColumn<>("Nama"); c2.setCellValueFactory(new PropertyValueFactory<>("nama"));
        TableColumn<Produk, Double> c3 = new TableColumn<>("Harga"); c3.setCellValueFactory(new PropertyValueFactory<>("harga"));
        TableColumn<Produk, Integer> c4 = new TableColumn<>("Stok"); c4.setCellValueFactory(new PropertyValueFactory<>("stok"));
        tableProduk.getColumns().addAll(c1, c2, c3, c4);
    }
    private void setupTableMember() {
        TableColumn<Member, String> c1 = new TableColumn<>("ID"); c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Member, String> c2 = new TableColumn<>("Nama"); c2.setCellValueFactory(new PropertyValueFactory<>("nama"));
        TableColumn<Member, Integer> c3 = new TableColumn<>("Poin"); c3.setCellValueFactory(new PropertyValueFactory<>("poin"));
        tableMember.getColumns().addAll(c1, c2, c3);
    }

    public Parent asParent() { return layout; }

    // GETTERS
    public Button getBtnLogout() { return btnLogout; } // <--- Getter untuk Controller
    
    public TabPane getTabAdmin() { return tabAdmin; }
    public TableView<Member> getTableMember() { return tableMember; }
    public TextField getTxtMemId() { return txtMemId; }
    public TextField getTxtMemNama() { return txtMemNama; }
    public Button getBtnSimpanMem() { return btnSimpanMem; }
    public Button getBtnHapusMem() { return btnHapusMem; }
    public VBox getAreaKasir() { return areaKasir; }
    public TableView<Produk> getTableProduk() { return tableProduk; }
    public TextField getTxtKode() { return txtKode; }
    public TextField getTxtNama() { return txtNama; }
    public TextField getTxtHarga() { return txtHarga; }
    public TextField getTxtStok() { return txtStok; }
    public TextField getInputQty() { return txtQty; }
    public Button getBtnSimpan() { return btnSimpan; }
    public Button getBtnUpdate() { return btnUpdate; }
    public Button getBtnHapus() { return btnHapus; }
    public Button getBtnClear() { return btnClear; }
    public Button getBtnLaporan() { return btnLaporan; } 
    public ListView<String> getListCart() { return listCart; }
    public Button getBtnAddToCart() { return btnAddToCart; }
    public Button getBtnHapusCart() { return btnHapusCart; }
    public Button getBtnCheckout() { return btnCheckout; }
    public Label getLblTotal() { return lblTotal; }
    public TextField getTxtMemberId() { return txtMemberId; }
    public Button getBtnCekMember() { return btnCekMember; }
    public Label getLblDiskon() { return lblDiskon; }
    public void clearForm() {
        txtKode.clear(); txtNama.clear(); txtHarga.clear(); txtStok.clear(); 
        txtQty.setText("1"); txtMemberId.clear(); lblDiskon.setText("Diskon: 0%");
        txtMemId.clear(); txtMemNama.clear();
    }
}