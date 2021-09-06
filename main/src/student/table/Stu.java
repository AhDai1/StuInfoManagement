/**
 * 说明：学生用户
 */
package student.table;

public class Stu {
    private int id;
    private String sno;// 学号
    private String name;
    private String sex;
    private String faculty;// 院系
    private String homeTown;// 籍贯
    private String email;
    private String tel;// 联系方式

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) { this.sex = sex; }

    public String getFaculty(){ return faculty; }

    public void setDepartment(String faculty) { this.faculty= faculty; }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}