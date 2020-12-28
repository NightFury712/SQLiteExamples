package nightfury.example.storedata;

public class SinhVien {
    private int MSSV;
    private String hoTen;
    private String ngaySinh;
    private String email;
    private String diaChi;

    public SinhVien() {
    }

    public SinhVien(int MSSV, String hoTen,  String ngaySinh, String email, String diaChi) {
        setMSSV(MSSV);
        setHoTen(hoTen);
        setDiaChi(diaChi);
        setNgaySinh(ngaySinh);
        setEmail(email);
    }

    public int getMSSV() {
        return MSSV;
    }

    public void setMSSV(int MSSV) {
        this.MSSV = MSSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
