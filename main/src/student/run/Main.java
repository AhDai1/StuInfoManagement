package student.run;

import student.dbUtil.dbUtil;

public class Main {
    public static void initDB(){
        dbUtil db = dbUtil.getdbUtil();

        //检查数据库是否初始化
        if (db.execute("SELECT name FROM  sysobjects WHERE name = N'admin' AND type = 'U'")) {
            return;
        }

        //初始化数据库
        //admin表
        db.execute("create table admin(id int primary key," +
                "name varchar(32)," +
                "username varchar(32)," +
                "password varchar(32))");
        db.execute("insert into admin(id, name, username, password) values(1, 'admin', 'test', 'test')");

        //student
        db.execute("create table student(" +
                "id int primary key," +
                "sno varchar(16)," +
                "name varchar(32)," +
                "sex varchar(8)," +
                "faulty varchar(32)," +
                "hometown varchar(64)," +
                "email varchar(32)," +
                "tel varchar(16))");
    }
    public static void main(String[] args) {
        //System.out.println("asd");
        //initDB();

        //dbUtil.getdbUtil().close();
    }

}
