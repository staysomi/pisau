package pepe.pisau.bos.model;

public class DetailLaporan {
    private String id;
    private String pekerjaan;
    private String produk;
    private int total;
    private int ongkostotal;


    public DetailLaporan() {
    }

    public DetailLaporan(String id, String pekerjaan, String produk, int total, int ongkostotal) {
        this.id = id;
        this.pekerjaan = pekerjaan;
        this.produk = produk;
        this.total = total;
        this.ongkostotal = ongkostotal;
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
}
