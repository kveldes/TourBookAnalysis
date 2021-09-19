package targetWebPages;

import RegRecords.RegisterRecords;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import sqlDatabase.DatabaseMng;
import sqlDatabase.InitialDB;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

public class EkdromiGR {
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:79.0) Gecko/20100101 Firefox/79.0";

    public void print( String city, String bookingStartDate, String bookingEndDate) throws IOException {
        final String cityquery = city;
        final String bookingStartDatequery = bookingStartDate;
        final String bookingEndDatequery = bookingEndDate;
        DatabaseMng db = new DatabaseMng();



        final Document page = Jsoup.connect("https://www.ekdromi.gr/frontend/deals/live_deals?date=" + URLEncoder.encode(bookingStartDatequery, "UTF-8")+ "+-+"+ URLEncoder.encode(bookingEndDatequery, "UTF-8")
                + "&city[]="+ URLEncoder.encode(cityquery, "UTF-8")).userAgent(USER_AGENT).timeout(10 * 1000).get();

        Elements namesOfHotel = page.getElementsByClass("list_deal_title");
        Elements hotelPrice = page.getElementsByClass("price");

        //Έλεγχος σύνσεση με Database
        try {
            if(InitialDB.connectToDB()!=null){
                System.out.println("Επιτυχία συνδέσεως με την Βάση!");
            }else{
                System.out.println("Πρόβλημα σύνδεσης με την Βάση δεδομένων");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int sum = 0;

        System.out.println("--------------------------------------");
        System.out.println("Αποτελέσματα απο EkdromiGR");
        for (int i =0; i< hotelPrice.size(); i++){
            String names = namesOfHotel.get(i).text().trim();
            String priceString = hotelPrice.get(i).text();
            if (priceString.length()>7){
                String pricenotready = priceString.substring(7);
                //Αφαίρεση με Regular expression  όλων των χαρακτήρων εκτός απο τους  αριθμούς
                String priceready = pricenotready.replaceAll("[^0-9]", "");
                double price = Double.parseDouble(priceready);
                sum+=price;
                RegisterRecords records  = new RegisterRecords(names,price);
                db.addAProduct(records);
                System.out.println("| " + names  + " | "  + " | " + price );
                System.out.println("--------------------------------------");
            }else{
                String priceready = priceString.replaceAll("[^0-9]", "");
                double price = Double.parseDouble(priceready);
                sum+=price;
                RegisterRecords records  = new RegisterRecords(names,price);
                db.addAProduct(records);
                System.out.println("| " + names  + " | "  + " | " + price );
                System.out.println("--------------------------------------");
            }
        }

    }
}
