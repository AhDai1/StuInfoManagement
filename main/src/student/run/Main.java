package student.run;

import student.dbUtil.dbUtil;
import student.view.LoginView;
import student.view.SelectView;

public class Main {
    public static void initDB(){
        dbUtil db = dbUtil.getdbUtil();

        //检查数据库是否初始化
        if (db.executeIsTable("SELECT name FROM  sysobjects WHERE name = N'admin' AND type = 'U'")) {
            return;
        }

        //初始化数据库
        //admin表
        db.execute("create table admin(id int identity(1,1)," +
                "name varchar(32)," +
                "username varchar(32) primary key," +
                "password varchar(32))");
        db.execute("insert into admin(name, username, password) values('admin', '1', '1')");

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

        //schedule
        db.execute("create table Schedule(" +
                "id int identity(1,1)," +
                "schno varchar(10) primary key," +
                "schname varchar(20)," +
                "schhour varchar(20)," +
                "schbook varchar(20)" +
                ")");

        //score
        db.execute("create table Score(" +
                "id int identity(1,1)," +
                "sno varchar(16)," +
                "schno varchar(10)," +
                "score int," +
                "primary key(sno, schno)," +
                "foreign key (sno) references student(sno) on update cascade on delete cascade," +
                "foreign key (schno) references Schedule(schno) on update cascade on delete cascade" +
                ")");


    }

    public static void main(String[] args) {
        //System.out.println("asd");
        initDB();
        new LoginView();
        dbUtil.getdbUtil().close();
    }

}
