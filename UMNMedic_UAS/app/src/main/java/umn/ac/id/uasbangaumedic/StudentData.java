package umn.ac.id.uasbangaumedic;

public class StudentData {
    private String namaReg = "";
    private String nimReg = "";
    private String prodi = "";
    private String emailReg = "";
    private String passwordReg = "";
    private String statusReg = "";

    StudentData(){

    }

    StudentData(String nama, String nim, String prodi, String emailReg, String passwordReg, String statusReg){
        this.namaReg = nama;
        this.nimReg = "000000"+nim;
        this.prodi = prodi;
        this.emailReg = emailReg;
        this.passwordReg = passwordReg;
        this.statusReg = statusReg;
    }

    public String getNamaReg(){
        return this.namaReg;
    }
    public String getNimReg(){
        return this.nimReg;
    }
    public String getProdi(){
        return this.prodi;
    }
    public String getEmailReg(){
        return this.emailReg;
    }
    public String getPasswordReg(){
        return this.passwordReg;
    }
    public String getStatusReg(){return this.statusReg;}
}
