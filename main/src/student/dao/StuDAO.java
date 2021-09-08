package student.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import student.base.BaseDAO;
import student.table.Stu;

/**
 * 说明：学生信息增删改查
 */
public class StuDAO extends BaseDAO{
    private final int fieldNum = 8;
    private final int showNum = 15;
    private static StuDAO sd = null;

    public static StuDAO getInstance(){
        if(sd == null){
            sd = new StuDAO();
        }
        return sd;
    }

    //update
    public boolean update(Stu stu){
        boolean result = false;
        if (stu == null) {
            return result;
        }
        try {
            if (querySno(stu.getSno()) == 0) {
                return result;
            }
            // update
            String sql = "update student set sex=?,faculty=?,email=?,tel=?,hometown=? where name=? and sno=?";
            String[] param = { stu.getSex(), stu.getFaculty(), stu.getEmail(), stu.getTel(), stu.getHomeTown(),
                     stu.getName(), stu.getSno() };
            int rowCount = db.executeUpdate(sql, param);
            if (rowCount == 1) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destroy();
        }
        return result;
    }

    // delete
    public boolean delete(Stu stu) {
        boolean result = false;
        if (stu == null) {
            return result;
        }
        String sql = "delete from student where name=? and sno=?";
        String[] param = { stu.getName(), stu.getSno() };
        int rowCount = db.executeUpdate(sql, param);
        if (rowCount == 1) {
            result = true;
        }
        destroy();
        return result;
    }

    // add
    public boolean add(Stu stu) {
        boolean result = false;
        if (stu == null) {
            return result;
        }
        try {
            // check
            if (querySno(stu.getSno()) == 1) {
                return result;
            }
            // insert
            String sql = "insert into student(name,sno,sex,department,hometown,mark,email,tel) values(?,?,?,?,?,?,?,?)";
            String[] param = { stu.getName(), stu.getSno(), stu.getSex(), stu.getFaculty(), stu.getHomeTown(),
                    stu.getEmail(), stu.getTel() };
            if (db.executeUpdate(sql, param) == 1) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destroy();
        }
        return result;
    }


    //query by name
    public String[][] queryName(String name){
        String[][] result = null;
        if(name.length() == 0){
            return result;
        }
        List<Stu> stus = new ArrayList<Stu>();
        int i = 0;
        String sql = "select * from student where name like ?";
        String[] param = { "%" + name + "%" };
        rs = db.executeQuery(sql, param);
        try {
            while (rs.next()) {
                buildList(rs, stus, i);
                i++;
            }
            if (stus.size() > 0) {
                result = new String[stus.size()][fieldNum];
                for (int j = 0; j < stus.size(); j++) {
                    buildResult(result, stus, j);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destroy();
        }
        return result;
    }

    //query showNum
    public String[][] list(int pageNum) {
        String[][] result = null;
        if (pageNum < 1) {
            return result;
        }
        List<Stu> stus = new ArrayList<Stu>();
        int i = 0;
        int beginNum = (pageNum - 1) * showNum;
        String sql = "select * from student limit ?,?";
        Integer[] param = { beginNum, showNum };
        rs = db.executeQuery(sql, param);
        try {
            while (rs.next()) {
                buildList(rs, stus, i);
                i++;
            }
            if (stus.size() > 0) {
                result = new String[stus.size()][fieldNum];
                for (int j = 0; j < stus.size(); j++) {
                    buildResult(result, stus, j);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destroy();
        }

        return result;
    }

    // 将list中记录添加到二维数组中
    private void buildResult(String[][] result, List<Stu> stus, int j) {
        Stu stu = stus.get(j);
        result[j][0] = String.valueOf(stu.getId());
        result[j][1] = stu.getName();
        result[j][2] = stu.getSno();
        result[j][3] = stu.getSex();
        result[j][4] = stu.getFaculty();
        result[j][5] = stu.getHomeTown();
        result[j][6] = stu.getEmail();
        result[j][7] = stu.getTel();
    }

    // 将rs记录添加到list中
    private void buildList(ResultSet rs, List<Stu> list, int i) throws SQLException {
        Stu stu = new Stu();
        stu.setId(i + 1);
        stu.setName(rs.getString("name"));
        stu.setFaculty(rs.getString("faculty"));
        stu.setEmail(rs.getString("email"));
        stu.setHomeTown(rs.getString("hometown"));
        stu.setSex(rs.getString("sex"));
        stu.setSno(rs.getString("sno"));
        stu.setTel(rs.getString("tel"));
        list.add(stu);
    }


    //query by sno
    private int querySno(String sno) throws SQLException{
        int result = 0;
        if("".equals(sno) || sno == null){
            return result;
        }
        String sql = "select * from student where sno=?";
        String[] param = { sno };
        rs = db.executeQuery(sql, param);
        if (rs.next()) {
            result = 1;
        }
        return result;
    }
}
