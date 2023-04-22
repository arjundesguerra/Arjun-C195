package Models;

import java.time.LocalDateTime;

public class Appointment {

    int appointmentID;
    String appointmentTitle;
    String appointmentDescription;
    String appointmentLocation;
    String appointmentType;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    int customerID;
    int userID;
    int contactID;


    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType,
                       LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) {

        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;

    }

    public int getAppointmentID() { return appointmentID; }

    public String getAppointmentTitle() { return appointmentTitle; }

    public String getAppointmentDescription() { return appointmentDescription; }

    public String getAppointmentLocation() { return appointmentLocation; }

    public String getAppointmentType() { return appointmentType; }

    public LocalDateTime getStartDateTime() { return startDateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }

    public int getCustomerID() { return customerID; }

    public int getUserID() { return userID; }

    public int getContactID() { return contactID; }


}
