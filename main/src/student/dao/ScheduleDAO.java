package student.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import student.base.BaseDAO;
import student.table.Schedule;
import student.table.Stu;

/**
 * 说明：课程表操作方法
 */
public class ScheduleDAO extends BaseDAO{
    private final int fieldNum = 4;
    private final int showNum = 6;
    private static ScheduleDAO schd = null;

    public static ScheduleDAO getInstance(){
        if(schd == null){
            schd = new ScheduleDAO();
        }
        return schd;
    }

    //update
    public boolean update(Schedule Sch){
        boolean result = false;
        if (Sch == null) {
            return result;
        }
        try {
            if (querySchno(Sch.getSchNo()) == 0) {
                return result;
            }
            // update
            String sql = "update schedule set schname=?, schhour=?, schbook=? where schno=?";
            String[] param = { Sch.getSchName(), Sch.getSchHour(), Sch.getSchBook(), Sch.getSchNo() };
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
    public boolean delete(Schedule Sch) {
        boolean result = false;
        if (Sch == null) {
            return result;
        }
        String sql = "delete from schedule where schno=?";
        String[] param = { Sch.getSchNo() };
        int rowCount = db.executeUpdate(sql, param);
        if (rowCount == 1) {
            result = true;
        }
        destroy();
        return result;
    }

    // add
    public boolean add(Schedule Sch) {
        boolean result = false;
        if (Sch == null) {
            return result;
        }
        try {
            // check
            if (querySchno(Sch.getSchNo()) == 0) {
                return result;
            }
            // insert
            String sql = "insert into schedule(schno,schname,schhour,schbook) values(?,?,?,?)";
            String[] param = { Sch.getSchNo(), Sch.getSchName(), Sch.getSchHour(), Sch.getSchBook() };
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

    //query showNum
    public String[][] list(int pageNum) {
        String[][] result = null;
        if (pageNum < 1) {
            return result;
        }
        List<Schedule> schs = new ArrayList<Schedule>();
        int i = 0;
        int beginNum = (pageNum - 1) * showNum;//m,n=beginNum + showNum
        String sql = "select top " + String.valueOf(showNum)  + " * from schedule  where id not in (select top " + String.valueOf(beginNum)  + " id from schedule)";
        rs = db.executeQuery(sql);
        try {
            while (rs.next()) {
                buildList(rs, schs, i);
                i++;
            }
            if (schs.size() > 0) {
                result = new String[schs.size()][fieldNum];
                for (int j = 0; j < schs.size(); j++) {
                    buildResult(result, schs, j);
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
    private void buildResult(String[][] result, List<Schedule> schs, int j) {
        Schedule sch = schs.get(j);
        //result[j][0] = String.valueOf(stu.getId());
        result[j][0] = sch.getSchNo();
        result[j][1] = sch.getSchName();
        result[j][2] = sch.getSchHour();
        result[j][3] = sch.getSchBook();
    }

    // 将rs记录添加到list中
    private void buildList(ResultSet rs, List<Schedule> list, int i) throws SQLException {
       Schedule sch = new Schedule();
        //stu.setId(i + 1);
        sch.setSchNo(rs.getString("schno"));
        sch.setSchName(rs.getString("schname"));
        sch.setSchHour(rs.getString("schhour"));
        sch.setSchBook(rs.getString("schbook"));

        list.add(sch);
    }

    public String[][] querySchnoResult(String schno){
        String[][] result = null;
        if(schno.length() == 0){
            return result;
        }
        List<Schedule> schs = new ArrayList<Schedule>();
        int i = 0;
        String sql = "select * from schedule where schno like ?";
        String[] param = { "%" + schno + "%" };
        rs = db.executeQuery(sql, param);
        try {
            while (rs.next()) {
                buildList(rs, schs, i);
                i++;
            }
            if (schs.size() > 0) {
                result = new String[schs.size()][fieldNum];
                for (int j = 0; j < schs.size(); j++) {
                    buildResult(result, schs, j);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destroy();
        }
        return result;
    }
    //query by schno
    private int querySchno(String schno) throws SQLException{
        int result = 0;
        if("".equals(schno) || schno == null){
            return result;
        }
        String sql = "select * from schedule where schno=?";
        String[] param = { schno };
        rs = db.executeQuery(sql, param);
        if(rs.next()){
            result = 1;
        }
        return result;
    }

}
