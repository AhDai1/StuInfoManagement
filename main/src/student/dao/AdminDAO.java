package student.dao;

import java.sql.SQLException;

import student.base.BaseDAO;

/**
 * 说明：管理员登录
 */
public class AdminDAO extends BaseDAO{
    private static AdminDAO ad = null;

    public static AdminDAO getInstance(){
        if(ad == null){
            ad = new AdminDAO();
        }
        return ad;
    }

    public boolean queryLogin(String username, String password){
        boolean result =false;
        if(username.length() == 0 || password.length() == 0){
            return result;
        }
        String sql= "select * from admin where username=? and password=?";
        String[] param ={username, password};
        rs = db.executeQuery(sql, param);
        try{
            if(rs.next()){
                result = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            destroy();
        }
        return result;
    }

    public boolean queryRegister(String name, String username, String password){
        boolean result =false;
        boolean flag = false;
        if(username.length() == 0 || password.length() == 0 || name.length() == 0){
            return result;
        }
        String sql1= "select * from admin where username=? and password=?";
        String[] param1 ={username, password};
        rs = db.executeQuery(sql1, param1);
        try{
            if(rs.next()){
                flag= true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            destroy();
        }
        if(!flag) {
            String sql2 = "insert into admin(name,username,password) values(?,?,?)";
            String[] param2 = {name, username, password};

            if (db.executeUpdate(sql2, param2) == 1) {
                result = true;
            }
        }

        return result;
    }
}
