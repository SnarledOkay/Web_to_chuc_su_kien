package EventVolunteerMatcher.entities;

import java.security.KeyPair;
import java.util.ArrayList;

public class Admin{
    private String username ;
    private String firstPassword ;
    private String email ;

//    public Admin(int age, String phoneNumber, String currentLocation, String gmail, String username, String password, ArrayList<Event> pastEvent, ArrayList<Event> yourEvent, ArrayList<Event> requestAccepted, ArrayList<Event> requestRejected, ArrayList<Pair<Volunteer, Event>> pendingRequest, String secretIdentification) {
//        super(age, phoneNumber, currentLocation, gmail, username, password, pastEvent, yourEvent, requestAccepted, requestRejected, pendingRequest, secretIdentification);
//    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstPassword() {
        return firstPassword;
    }

    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
