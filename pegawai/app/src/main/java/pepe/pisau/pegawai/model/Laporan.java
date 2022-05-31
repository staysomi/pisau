package pepe.pisau.pegawai.model;

public class Laporan {
    String bukti;
    String id;
    String nama;
    int ongkostotal;
    String pekerjaan;
    String produk;
    String status;
    int total;

    public Laporan() {

    }

    public Laporan(String bukti, String id, String nama, int ongkostotal, String pekerjaan, String produk, String status, int total) {
        this.bukti = bukti;
        this.id = id;
        this.nama = nama;
        this.ongkostotal = ongkostotal;
        this.pekerjaan = pekerjaan;
        this.produk = produk;
        this.status = status;
        this.total = total;
    }

    public String getBukti() {
        return bukti;
    }

    public void setBukti(String bukti) {
        this.bukti = bukti;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getOngkostotal() {
        return ongkostotal;
    }

    public void setOngkostotal(int ongkostotal) {
        this.ongkostotal = ongkostotal;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
