package Models;

public class Division {

    private int divisionID;
    private String divisionName;

    public Division(int divisionID, String divisionName) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }


    public int getDivisionID() { return divisionID; }

    public String getDivisionName() { return divisionName; }

    @Override
    public String toString() { return divisionName; }

}
