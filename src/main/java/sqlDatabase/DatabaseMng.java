package sqlDatabase;

import RegRecords.RegisterRecords;

import java.sql.*;


//Class that help me with the queries of db

public class DatabaseMng {
    static String url = "jdbc:sqlite:tourBook.db";


    public  void createTable(){
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS tourBookResults (\n"
                + "    aa_id integer PRIMARY KEY,\n"
                + "    hotelnames text, \n"
                + "    hotelprices DOUBLE\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS tourBookResults";

        try {
            Connection conn = InitialDB.connectToDB();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    //--------------------Προσθήκη records απο web scrapping---------------------------//
    public int addAProduct(RegisterRecords records) {

        int status = 0;
        String sql = "INSERT INTO tourBookResults(hotelnames,hotelprices) VALUES(?,?)";
        //String url = "jdbc:sqlite:web.db";
        try {
            Connection conn = InitialDB.connectToDB();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, records.getHotelnames());
            ps.setDouble(2, records.getHotelprices());
            status = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    public int countRecords(){
        int counter = 0;
        try {
            Connection connection = InitialDB.connectToDB();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tourBookResults");

            while (rs.next()){
                RegisterRecords records = new RegisterRecords(rs.getString("hotelnames"),rs.getDouble("hotelprices"));
                counter++;
            }
            InitialDB.closeCon(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    //-----------------------------------sql for finding the average price of hotels-------//
    public float AveragePrice(){
        try {
            Connection connection = InitialDB.connectToDB();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT Avg(hotelprices) As average FROM tourBookResults");

            if (rs.next()){
                return rs.getFloat(1);
            }

            InitialDB.closeCon(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
