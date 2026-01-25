package main.java.com.upb.agripos.model;

public class Member {
    private String id;
    private String nama;
    private int poin;

    public Member(String id, String nama, int poin) {
        this.id = id;
        this.nama = nama;
        this.poin = poin;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public int getPoin() { return poin; }
}