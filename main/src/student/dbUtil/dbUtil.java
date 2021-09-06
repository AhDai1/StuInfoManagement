/**
 * 说明：数据库工具类
 */
package student.dbUtil;

import java.lang.invoke.TypeDescriptor;
import java.sql.*;

import student.Final;

public class dbUtil {
    private static dbUtil db;

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    private dbUtil() {}//不能在其他类中实例化

    public static dbUtil getdbUtil(){//返回一个dbUtil实例
        if(db == null){
            db = new dbUtil();
        }
        return db;
    }

    public int executeUpdate(String sql){//执行更新操作
        int result = -1;
        if(getConn() == null){
            return result;
        }
        try{
            ps = conn.prepareStatement(sql);
            result = ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public int executeUpdate(String sql, Object[] obj){//obj替换？，配合执行sql
        int result = -1;
        if(getConn() == null){
            return result;
        }
        try{
            ps = conn.prepareStatement(sql);
            for(int i = 0;i < obj.length; i++){
                ps.setObject(i+1, obj[i]);
            }
            result = ps.executeUpdate();
            close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet executeQuery(String sql){//执行查询操作，将结果放入ResultSet
        if (getConn() == null) {
            return null;
        }
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet executeQuery(String sql, Object[] obj){//同上一个重载
        if (getConn() == null) {
            return null;
        }
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean execute(String sql) {//执行sql语句
        if (getConn() == null) {
            return false;
        }
        try {
            Statement statement = conn.createStatement();
            statement.execute(sql);
            statement.close();
            return true;
        } catch (SQLException e) {
			e.printStackTrace();
            return false;
        }
    }

    private Connection getConn(){//连接数据库，返回Conn
        try{
            if(conn == null || conn.isClosed()){
                Class.forName(Final.JDBC_DRIVER);
                conn = DriverManager.getConnection(Final.JDBC_URL,Final.JDBC_USERNAME,Final.JDBC_PASSWORD);
            }
        }
        catch (ClassNotFoundException e){
            System.out.println("jdbc driver is not found.");
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    private void close(){
        try{
            if(rs != null){
                rs.close();
            }
            if(ps != null){
                ps.close();
            }
            if(conn != null){
                conn.close();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
