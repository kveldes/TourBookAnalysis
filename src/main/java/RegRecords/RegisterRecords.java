package RegRecords;

public class RegisterRecords {
    private String hotelnames;
    private double hotelprices;

    public RegisterRecords(String hotelnames, double hotelprices) {
        this.hotelnames = hotelnames;
        this.hotelprices = hotelprices;
    }



    public String getHotelnames() {
        return hotelnames;
    }

    public void setHotelnames(String hotelnames) {
        this.hotelnames = hotelnames;
    }

    public double getHotelprices() {
        return hotelprices;
    }

    public void setHotelprices(double hotelprices) {
        this.hotelprices = hotelprices;
    }

}
