package Models;

import java.time.LocalDateTime;

public class Appointment {

    int appointmentID;
    String appointmentTitle;
    String appointmentDescription;
    String appointmentLocation;
    int contactID;
    String appointmentType;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    int customerID;
    int userID;

    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, int contactID,
                       String appointmentType, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID) {

        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.contactID = contactID;
        this.appointmentType = appointmentType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerID = customerID;
        this.userID = userID;

    }

    public int getAppointmentID() { return appointmentID; }

    public String getAppointmentTitle() { return appointmentTitle; }

    public String getAppointmentDescription() { return appointmentDescription; }

    public String getAppointmentLocation() { return appointmentLocation; }

    public int getContactID() { return contactID; }

    public String getAppointmentType() { return appointmentType; }

    public LocalDateTime getStartDateTime() { return startDateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }

    public int getCustomerID() { return customerID; }

    public int getUserID() { return userID; }

}
