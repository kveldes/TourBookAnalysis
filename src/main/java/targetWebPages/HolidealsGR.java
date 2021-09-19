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

public class HolidealsGR {
   public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:79.0) Gecko/20100101 Firefox/79.0";

    public void print( String cityEn, String bookingStartDate, String bookingEndDate, int numOfAdults) throws IOException {
        final String cityquery = cityEn;
        final String bookingStartDatequery = bookingStartDate;
        final String bookingEndDatequery = bookingEndDate;
        final int numOfAdultsquery = numOfAdults;
        final String url;
        DatabaseMng db = new DatabaseMng();


        final Document page = Jsoup.connect("http://hotels.holideals.gr/Hotels/Search?destination=place:"+ URLEncoder.encode(cityquery, "UTF-8") +"&checkin="+URLEncoder.encode(bookingStartDatequery, "UTF-8") +"&checkout="+URLEncoder.encode(bookingEndDatequery, "UTF-8")+ "&Rooms=1&adults_1="+ numOfAdultsquery+"&languageCode=EL&currencyCode=EUR").userAgent(USER_AGENT).timeout(10 * 1000).get();


        Elements namesOfHotel = page.getElementsByClass("hc-searchresultitem__hotelname");
        Elements hotelPrice = page.getElementsByClass("hc-searchresultitemdeal__currentrate ");
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
        System.out.println("Αποτελέσματα απο HolidealsGR");
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
