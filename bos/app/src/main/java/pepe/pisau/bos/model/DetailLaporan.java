package pepe.pisau.bos.model;

import java.util.Date;

public class DetailLaporan {
    String bukti;
    String id;
    String nama;
    int ongkostotal;
    String pekerjaan;
    String produk;
    String status;
    int total;
    Date date;

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
