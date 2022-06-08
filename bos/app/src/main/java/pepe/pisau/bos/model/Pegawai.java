package pepe.pisau.bos.model;

public class Pegawai {
    public String alamat;
    public String hp;
    public String id;
    public String password;

    public Pegawai() {
    }

    public Pegawai(String alamat, String hp, String id, String password) {
        this.alamat = alamat;
        this.hp = hp;
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }
}
