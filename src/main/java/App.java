import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import sqlDatabase.DatabaseMng;
import sqlDatabase.InitialDB;
import targetWebPages.*;



public class App {
    public static int userChoice;
    public static String desCountry;
    public static String city;
    public static String cityEN;
    public static String bookingStartDate;
    public static String bookingEndDate;
    public static int    numOfAdults;
    public static int    numOfChildren;

    static DatabaseMng db = new DatabaseMng();





    public static void main(String[] args) throws IOException {
        boolean quit = false;
        db.dropTable();
        db.createTable();


        do {
            Scanner input = new Scanner(System.in);

            /***************************************************/

            System.out.println("Please Choose from these choices");
            System.out.println("-------------------------\n");
            System.out.println("1 - Start the Toutism Booking Analysis App");
            System.out.println("2 - Exit from the App");

            userChoice = input.nextInt();

//-------------------------------------------------------------------------------------------------------------------------------------------------------------/
            switch (userChoice) {
                case 1:
                    System.out.println("\nYou've chosen item #1 - Starting  App");
                    //Λαμβανουμε απο τον χρηστη την Χωρα
                    System.out.println("\nPlease give Destination Country in Greek Language");
                    Scanner scan1 = new Scanner(System.in);
                    boolean validData = false;
                    do {

                        try{
                            desCountry = scan1.next(); //tries to get data. Goes to catch if invalid data
                            validData = true;//if gets data successfully, sets boolean to true
                        }catch(InputMismatchException e){
                            //executes when this exception occurs
                            System.out.println("Input has to be a text not a number. ");
                        }
                    }while(validData==false);//loops until validData is true
                    System.out.println(desCountry);

                    //Λαμβανουμε απο τον χρηστη την Πολη στα Ελληνικα
                    System.out.println("\nPlease give City in Greek Language");
                    Scanner scan2 = new Scanner(System.in);
                    boolean validData2 = false;
                    do {

                        try{
                            city = scan2.next(); //tries to get data. Goes to catch if invalid data
                            validData2 = true;//if gets data successfully, sets boolean to true
                        }catch(InputMismatchException e){
                            //executes when this exception occurs
                            System.out.println("Input has to be a text not a number. ");
                        }
                    }while(validData2==false);//loops until validData is true
                    System.out.println(city);


                    //Λαμβανουμε απο τον χρηστη την Πολη στα Αγγλικα
                    System.out.println("\nPlease give City in English Language");
                    Scanner scan10 = new Scanner(System.in);
                    boolean validData10 = false;
                    do {

                        try{
                            cityEN = scan10.next(); //tries to get data. Goes to catch if invalid data
                            validData10 = true;//if gets data successfully, sets boolean to true
                        }catch(InputMismatchException e){
                            //executes when this exception occurs
                            System.out.println("Input has to be a text not a number. ");
                        }
                    }while(validData10==false);//loops until validData is true
                    System.out.println(cityEN);
                    //--------------------------------------------------------------------------------

                    //Λαμβανουμε απο τον χρηστη την Start-Date of Booking
                    System.out.println("\nPlease give Start-Date of Booking in the following format dd/MM/yyyy\"");
                    Scanner scan3 = new Scanner(System.in);
                    boolean validData3 = false;
                    do {

                        try{
                            bookingStartDate = scan3.nextLine(); //tries to get data. Goes to catch if invalid data
                                if (isValidFormat("dd/MM/yyyy",bookingStartDate)==false){
                                System.out.println("Wrong Format of Date Please give the right Date Format");
                                continue;
                            }
                                validData3 = true;//if gets data successfully, sets boolean to true

                        }catch(InputMismatchException e){
                            //executes when this exception occurs
                            System.out.println("Input has to be a text not a number. ");
                        }
                    }while(validData3==false);//loops until validData is true
                    System.out.println(bookingStartDate);

                    //Λαμβανουμε απο τον χρηστη το Number of Adults
                    System.out.println("\nPlease give Number of Adults");
                    Scanner scan4 = new Scanner(System.in);
                    boolean validData4 = false;
                    do {

                        try{
                           numOfAdults = scan4.nextInt(); //tries to get data. Goes to catch if invalid data

                            validData4 = true;//if gets data successfully, sets boolean to true

                        }catch(InputMismatchException e){
                            //executes when this exception occurs
                            System.out.println("Input has to be a number not a Text. ");
                        }
                    }while(validData4==false);//loops until validData is true
                    System.out.println(numOfAdults);

                    //Λαμβανουμε απο τον χρηστη το Number of Children
                    System.out.println("\nPlease give Number of Children");
                    Scanner scan5 = new Scanner(System.in);
                    boolean validData5 = false;
                    do {

                        try{
                            numOfChildren = scan5.nextInt(); //tries to get data. Goes to catch if invalid data

                            validData5 = true;//if gets data successfully, sets boolean to true

                        }catch(InputMismatchException e){
                            //executes when this exception occurs
                            System.out.println("Input has to be a number not Text. ");
                        }
                    }while(validData5==false);//loops until validData is true
                    System.out.println(numOfChildren);
                    bookingEndDate=getBookingEndDateMethod(bookingStartDate);
                    System.out.println("You Choose the following Country: " + desCountry);
                    System.out.println("You Choose the following City: " + city);
                    System.out.println("You Choose Booking Start Day: " + bookingStartDate);
                    System.out.println("You Choose Booking End Day: " + bookingEndDate);
                    System.out.println("You Choose Convert Booking Start  Day: " + convertDatemmddyyyy(bookingStartDate));
                    System.out.println("You Choose Convert Booking End Day: " + convertDatemmddyyyy(bookingEndDate));
                    System.out.println("You Choose the following Number of Adults: "+numOfAdults );
                    System.out.println("You Choose the following Number of Children: "+numOfChildren );


                    //[1]----HotelsGR--------------------------------------------------------------------------------------------------------------------------------------
                    System.out.println("======================================");
                    HotelsGR hotelsGR = new HotelsGR();
                    String bookingStartDateHotelsGR = convertDateyyyymmdd(bookingStartDate);
                    String bookingEndDateHotelsGR = convertDateyyyymmdd(bookingEndDate);
                    try {
                        hotelsGR.print(city,bookingStartDateHotelsGR,bookingEndDateHotelsGR,numOfAdults,numOfChildren);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //[2]----EkdormiGR--------------------------------------------------------------------------------------------------------------------------------------
                    System.out.println("======================================");
                    EkdromiGR ekkdromiGR = new EkdromiGR();

                    try {
                        ekkdromiGR.print(city, bookingStartDate,bookingEndDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //[3]----HolidealsGR--------------------------------------------------------------------------------------------------------------------------------------
                    System.out.println("======================================");
                    HolidealsGR holidealsGR = new HolidealsGR();
                    String bookingStartDateHolidealsGR = convertDateyyyymmdd(bookingStartDate);
                    String bookingEndDateHolidealsGR = convertDateyyyymmdd(bookingEndDate);

                    try {
                        holidealsGR.print(cityEN,bookingStartDateHolidealsGR,bookingEndDateHolidealsGR,numOfAdults);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //[4]----DealsafariGR--------------------------------------------------------------------------------------------------------------------------------------
                    System.out.println("======================================");
                    DealsafariGR dealsafariGR = new DealsafariGR();

                    try {
                        dealsafariGR.print(city);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //--------Teliko Vima Emfanish apo Vash Twn a)Synolikoy arithmoy Dwmatiwn & 2)Synolikh mesh timh Dwmatiwn
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("Αποτελεσμα Συνολικού αριθμόυ δωματίων μετά από την Αναζητηση ");
                    System.out.println(db.countRecords());
                    System.out.println("Αποτελεσμα της μέσης συνολικής τιμής σε δωματία");
                    System.out.println(db.AveragePrice());
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");





                    break;

                case 2:
                    quit = true;
                    break;

                default:
                    System.out.println("\nInvalid choice.");

            }


        }while (!quit) ;
        System.out.println("Bye-bye!");
    }

    //Method that validate if the given Date is in the right Format
    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    //Method that return the day after the bookingStartDate
    public static String getBookingEndDateMethod (String bookingStartDate){
        String dt = bookingStartDate;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        return dt;

    }


    //Method  mm-dd-yyyy that revert date to the apropriate format of web sites
    public static String convertDatemmddyyyy(String date){
        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        Date dateValue = null;
        try {
            dateValue = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Αλλαγή του format απο dd/MM/yyyy σε MM-dd-yyyy
        SimpleDateFormat output = new SimpleDateFormat("MM-dd-yyyy");
        String convertdate = output.format(dateValue);

        return  convertdate;
    }

    //Method  yyyy-mm-dd that revert date to the apropriate format of web sites
    public static String convertDateyyyymmdd(String date){
        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        Date dateValue = null;
        try {
            dateValue = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Αλλαγή του format απο dd/MM/yyyy σε yyyy-mm-dd
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        String convertdate = output.format(dateValue);

        return  convertdate;
    }
}










