package pepe.pisau.pegawai.model;

import java.util.Date;

public class DetailLaporan {
    private String id;
    private String pekerjaan;
    private String produk;
    private String status;
    private String nama;
    private String bukti;
    private int total;
    private int ongkostotal;
    private Date date;

    public DetailLaporan() {
    }

    public DetailLaporan(String id, String pekerjaan, String produk, int total, int ongkostotal, String status, String nama, String bukti, Date date) {
        this.id = id;
        this.pekerjaan = pekerjaan;
        this.produk = produk;
        this.total = total;
        this.ongkostotal = ongkostotal;
        this.status = status;
        this.nama = nama;
        this.bukti = bukti;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }
    public String getBukti() {
        return bukti;
    }

    public void setBukti(String bukti) {
        this.bukti = bukti;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOngkostotal() {
        return ongkostotal;
    }

    public void setOngkostotal(int ongkostotal) {
        this.ongkostotal = ongkostotal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
