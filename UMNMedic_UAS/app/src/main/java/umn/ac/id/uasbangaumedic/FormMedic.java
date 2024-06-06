package umn.ac.id.uasbangaumedic;

public class FormMedic {
    private String email = "";
    private String nama = "";
    private String nim = "";
    private String lineId = "";
    private String organisasi = "";
    private String jabatan = "";
    private String acara = "";
    private String tanggalPelaksanaan = "";
    private String waktuTempatPelaksanaan = "";
    private String deskripsi = "";
    private int jumlahMedis = 0;
    private String keterangan = "";
    private String tanggalPengembalian = "";
    //    True artinya form jasa
    private boolean request = true;
    private int status;
    private String id;
    private String imagePath = "";
    private String imageFileName = "";


    FormMedic() {

    }

    FormMedic(String email, String nama, String nim, String lineId, String organisasi, String jabatan, String acara, String tanggalPelaksanaan,
              String waktuTempatPelaksanaan, String deskripsi, int jumlahMedis, String keterangan, String tanggalPengembalian, boolean request,
              int status, String id, String imagePath, String imageFileName) {
        this.email = email;
        this.nama = nama;
        this.nim = nim;
        this.lineId = lineId;
        this.organisasi = organisasi;
        this.jabatan = jabatan;
        this.acara = acara;
        this.tanggalPelaksanaan = tanggalPelaksanaan;
        this.waktuTempatPelaksanaan = waktuTempatPelaksanaan;
        this.deskripsi = deskripsi;
        this.jumlahMedis = jumlahMedis;
        this.keterangan = keterangan;
        this.tanggalPengembalian = tanggalPengembalian;
        this.request = request;
//        0 = pending, 1 = ditolak, 2 = diterima
        this.status = status;
        this.id = id;
        this.imagePath = imagePath;
        this.imageFileName = imageFileName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNama() {
        return this.nama;
    }

    public String getNim() {
        return this.nim;
    }

    public String getLineId() {
        return this.lineId;
    }

    public String getOrganisasi() {
        return this.organisasi;
    }

    public String getJabatan() {
        return this.jabatan;
    }

    public String getAcara() {
        return this.acara;
    }

    public String getTanggalPelaksanaan() {
        return this.tanggalPelaksanaan;
    }

    public String getWaktuTempatPelaksanaan() {
        return this.waktuTempatPelaksanaan;
    }

    public String getDeskripsi() {
        return  this.deskripsi;
    }

    public int getJumlahMedis() {
        return this.jumlahMedis;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public String getTanggalPengembalian() {
        return this.tanggalPengembalian;
    }

    public boolean getRequest() {
        return this.request;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {return this.id;}

    public String getImagePath(){return this.imagePath;}
    public String getImageFileName(){return this.imageFileName;}
}
