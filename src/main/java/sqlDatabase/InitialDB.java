package sqlDatabase;

//Class that initialize the SQLite Database and make a new table in it with name tourBookAnaly.db

import java.sql.*;

public class InitialDB {
    static String url = "jdbc:sqlite:tourBook.db";

    public InitialDB() {

    }


        public static Connection  connectToDB () throws SQLException {

            Connection conn = DriverManager.getConnection(url); {



        } return conn;
    }

    public static void  closeCon(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
