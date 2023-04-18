package Models;

public class Customer {
    int customerID;
    String customerName;
    String customerPhoneNumber;
    String customerAddress;
    String customerDivision;
    String customerCountry;
    String customerPostalCode;

    public Customer(int customerID, String customerName, String customerPhoneNumber, String customerAddress, String customerDivision, String customerCountry, String customerPostalCode) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.customerDivision = customerDivision;
        this.customerCountry = customerCountry;
        this.customerPostalCode = customerPostalCode;
    }

    public int getCustomerID() { return customerID; }

    public String getCustomerName() { return customerName; }

    public String getCustomerPhoneNumber() { return customerPhoneNumber; }

    public String getCustomerAddress() { return customerAddress; }

    public String getCustomerDivision() { return customerDivision; }

    public String getCustomerCountry() { return customerCountry; }

    public String getCustomerPostalCode() { return customerPostalCode; }

}
