package student.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import student.base.BaseDAO;
import student.table.Score;
import student.table.Stu;

/**
 * 说明：成绩表方法
 */
public class ScoreDAO extends BaseDAO{
    private final int fieldNum = 3;
    private final int showNum = 6;
    private static ScoreDAO scd = null;

    public static ScoreDAO getInstance(){
        if(scd == null){
            scd = new ScoreDAO();
        }
        return scd;
    }

    //update score
    public boolean update(Score score){
        boolean result = false;
        if (score == null) {
            return result;
        }
        try {
            if (querySchno(score.getSchNo()) == 0 || querySno(score.getStuNo()) == 0) {
                return result;
            }
            // update
            String sql = "update score set score=? where schno=? and sno=?";
            String[] param = { String.valueOf(score.getStuScore()), score.getSchNo(), score.getStuNo() };
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

    // delete by schno and sno
    public boolean delete(Score score) {
        boolean result = false;
        if (score == null) {
            return result;
        }
        String sql = "delete from score where schno=? and sno=?";
        String[] param = { score.getSchNo(), score.getStuNo() };
        int rowCount = db.executeUpdate(sql, param);
        if (rowCount == 1) {
            result = true;
        }
        destroy();
        return result;
    }

    // add
    public int add(Score score) {
        int result = 0;
        if (score == null) {
            return result;
        }
        try {
            // check
            if (querySchno(score.getSchNo()) == 0 || querySno(score.getStuNo()) == 0) {
                result = 1;
                return result;
            }
            if(querySnoAndSchno(score.getStuNo(), score.getSchNo()) == 1){
                result = 2;
                return result;
            }
            // insert
            String sql = "insert into score(sno, schno, score) values(?,?,?)";
            String[] param = { score.getStuNo(), score.getSchNo(), String.valueOf(score.getStuScore()) };
            if (db.executeUpdate(sql, param) == 1) {
                result = 0;
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
        List<Score> scores = new ArrayList<Score>();
        int i = 0;
        int beginNum = (pageNum - 1) * showNum;//m,n=beginNum + showNum
        String sql = "select top " + String.valueOf(showNum)  + " * from score  where id not in (select top " + String.valueOf(beginNum)  + " id from score)";
        rs = db.executeQuery(sql);
        try {
            while (rs.next()) {
                buildList(rs, scores, i);
                i++;
            }
            if (scores.size() > 0) {
                result = new String[scores.size()][fieldNum];
                for (int j = 0; j < scores.size(); j++) {
                    buildResult(result, scores, j);
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
    private void buildResult(String[][] result, List<Score> scores, int j) {
        Score score = scores.get(j);
        //result[j][0] = String.valueOf(stu.getId());
        result[j][0] = score.getStuNo();
        result[j][1] = score.getSchNo();
        result[j][2] = String.valueOf(score.getStuScore());
    }

    // 将rs记录添加到list中
    private void buildList(ResultSet rs, List<Score> list, int i) throws SQLException {
        Score score = new Score();
        score.setStuNo(rs.getString("sno"));
        score.setSchNo(rs.getString("schno"));
        score.setStuScore(rs.getInt("score"));
        list.add(score);
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

    public String[][] queryScoreResult(String sno, String schno){
        String[][] result = null;
        if(schno.length() == 0 && sno.length() == 0){
            return result;
        }
        List<Score> scores = new ArrayList<Score>();
        int i = 0;
        if(schno.length() != 0 && sno.length() != 0){
            String sql = "select * from score where schno=? and sno=?";
            String[] param = { schno, sno };
            rs = db.executeQuery(sql, param);
        }
        else if(schno.length() == 0){
            String sql = "select * from score where sno=?";
            String[] param = { sno };
            rs = db.executeQuery(sql, param);
        }
        else if(sno.length() == 0){
            String sql = "select * from score where schno=?";
            String[] param = { schno };
            rs = db.executeQuery(sql, param);
        }
        try {
            while (rs.next()) {
                buildList(rs, scores, i);
                i++;
            }
            if (scores.size() > 0) {
                result = new String[scores.size()][fieldNum];
                for (int j = 0; j < scores.size(); j++) {
                    buildResult(result, scores, j);
                }
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

    //query by snoandschno from score
    private int querySnoAndSchno(String sno, String schno) throws SQLException{
        int result = 0;
        if("".equals(sno) || sno == null || "".equals(schno) || schno == null){
            return result;
        }
        String sql = "select * from score where sno=? and schno=?";
        String[] param = { sno, schno };
        rs = db.executeQuery(sql, param);
        if (rs.next()) {
            result = 1;
        }
        return result;
    }
}
