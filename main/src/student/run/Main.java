package student.run;

import student.dbUtil.dbUtil;
import student.view.LoginView;

public class Main {
    public static void initDB(){
        dbUtil db = dbUtil.getdbUtil();

        //检查数据库是否初始化
        if (db.executeIsTable("SELECT name FROM  sysobjects WHERE name = N'admin' AND type = 'U'")) {
            return;
        }

        //初始化数据库
        //admin表
        db.execute("create table admin(id int primary key," +
                "name varchar(32)," +
                "username varchar(32)," +
                "password varchar(32))");
        db.execute("insert into admin(id, name, username, password) values(1, 'admin', '1', '1')");

        //student
        db.execute("create table student(" +
                "id int identity(1,1) ," +
                "sno varchar(16) primary key," +
                "name varchar(32)," +
                "sex varchar(8)," +
                "faculty varchar(32)," +
                "hometown varchar(64)," +
                "email varchar(32)," +
                "tel varchar(16))");
    }
    public static void main(String[] args) {
        //System.out.println("asd");
        initDB();
        new LoginView();
        //dbUtil.getdbUtil().close();
    }

}
