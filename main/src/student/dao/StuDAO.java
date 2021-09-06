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
    private final int filedNum = 8;
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
