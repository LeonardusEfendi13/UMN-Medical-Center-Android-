package umn.ac.id.uasbangaumedic;


public class JadwalPiket {
    private String hari, jam, nama, id;
    private int del;

    JadwalPiket(String hari, String jam, String nama, String id, int del){
        this.hari = hari;
        this.jam = jam;
        this.nama = nama;
        this.id = id;
        this.del = del;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getNama() {
        return nama;
    }

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
        this.del = del;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

