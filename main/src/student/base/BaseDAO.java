package student.base;

import java.sql.ResultSet;
import java.sql.SQLException;

import student.DAO;
import student.dao.ScheduleDAO;
import student.dao.ScoreDAO;
import student.dbUtil.dbUtil;
import student.dao.StuDAO;
import student.dao.AdminDAO;
import student.table.Score;

/**
 * 说明：DAO基类
 */
public abstract class BaseDAO {
    protected final dbUtil db = dbUtil.getdbUtil();
    protected ResultSet rs;
    private static BaseDAO baseDAO;

    public BaseDAO(){}

    public static BaseDAO getDAO(DAO dao){
        switch (dao){
            case AdMinDAO:
                if(baseDAO == null || baseDAO.getClass() != AdminDAO.class){
                    baseDAO = AdminDAO.getInstance();
                }
                break;
            case StuDAO:
                if(baseDAO == null || baseDAO.getClass() != StuDAO.class){
                    baseDAO = StuDAO.getInstance();
                }
                break;
            case ScheduleDAO:
                if(baseDAO == null || baseDAO.getClass() != ScheduleDAO.class){
                    baseDAO = ScheduleDAO.getInstance();
                }
                break;
            case ScoreDAO:
                if(baseDAO == null || baseDAO.getClass() != ScoreDAO.class){
                    baseDAO = ScoreDAO.getInstance();
                }
                break;
            default:
                break;
        }
        return baseDAO;
    }

    protected void destroy(){
        try{
            if(rs != null){
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
