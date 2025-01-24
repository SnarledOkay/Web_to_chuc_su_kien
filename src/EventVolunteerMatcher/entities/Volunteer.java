package EventVolunteerMatcher.entities;

import java.util.ArrayList;

public class Volunteer {
    private int age ; // age-restricted events
    private String phoneNumber ;
    private String currentLocation ;
    private String gmail ;
    private String username ;
    private String password ;
    private ArrayList<Event> yourEvent ;
    private ArrayList<Event> currentEvent ;
    private ArrayList<Event> pastEvent ;
    private ArrayList<Event> pastEventCompleted ;
    private ArrayList<Event> requestAccepted ;
    private ArrayList<Event> requestRejected ;
    private ArrayList<Pair<Volunteer,Event>> pendingRequest ;
    private boolean isAdmin ;
    private String secretIdentification ;
    private ArrayList<Event> yourEventRejected ;
    private ArrayList<Integer> reasonForRejection ;
    private ArrayList<Event> yourEventAccepted ;
    private ArrayList<String> violation ;
    private boolean justReceiveWarning ;
    private ArrayList<String> programNotification ;

    public Volunteer(int age, String phoneNumber, String currentLocation, String gmail, String username, String password, ArrayList<Event> yourEvent, ArrayList<Event> currentEvent, ArrayList<Event> pastEvent, ArrayList<Event> pastEventCompleted, ArrayList<Event> requestAccepted, ArrayList<Event> requestRejected, ArrayList<Pair<Volunteer, Event>> pendingRequest, boolean isAdmin, String secretIdentification, ArrayList<Event> yourEventRejected, ArrayList<Integer> reasonForRejection, ArrayList<Event> yourEventAccepted, ArrayList<String> violation, boolean justReceiveWarning, ArrayList<String> programNotification) {
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.currentLocation = currentLocation;
        this.gmail = gmail;
        this.username = username;
        this.password = password;
        this.yourEvent = yourEvent;
        this.currentEvent = currentEvent;
        this.pastEvent = pastEvent;
        this.pastEventCompleted = pastEventCompleted;
        this.requestAccepted = requestAccepted;
        this.requestRejected = requestRejected;
        this.pendingRequest = pendingRequest;
        this.isAdmin = isAdmin;
        this.secretIdentification = secretIdentification;
        this.yourEventRejected = yourEventRejected;
        this.reasonForRejection = reasonForRejection;
        this.yourEventAccepted = yourEventAccepted;
        this.violation = violation;
        this.justReceiveWarning = justReceiveWarning;
        this.programNotification = programNotification;
    }

//    public Volunteer(int age, String phoneNumber, String currentLocation, String gmail, String username, String password, ArrayList<Event> yourEvent, ArrayList<Event> currentEvent, ArrayList<Event> pastEvent, ArrayList<Event> requestAccepted, ArrayList<Event> requestRejected, ArrayList<Pair<Volunteer, Event>> pendingRequest, boolean isAdmin, String secretIdentification, ArrayList<Event> yourEventRejected, ArrayList<Integer> reasonForRejection, ArrayList<Event> yourEventAccepted, ArrayList<String> violation, boolean justReceiveWarning, ArrayList<String> programNotification) {
//        this.age = age;
//        this.phoneNumber = phoneNumber;
//        this.currentLocation = currentLocation;
//        this.gmail = gmail;
//        this.username = username;
//        this.password = password;
//        this.yourEvent = yourEvent;
//        this.currentEvent = currentEvent;
//        this.pastEvent = pastEvent;
//        this.requestAccepted = requestAccepted;
//        this.requestRejected = requestRejected;
//        this.pendingRequest = pendingRequest;
//        this.isAdmin = isAdmin;
//        this.secretIdentification = secretIdentification;
//        this.yourEventRejected = yourEventRejected;
//        this.reasonForRejection = reasonForRejection;
//        this.yourEventAccepted = yourEventAccepted;
//        this.violation = violation;
//        this.justReceiveWarning = justReceiveWarning;
//        this.programNotification = programNotification;
//    }

    //    public Volunteer(int age, String phoneNumber, String currentLocation, String gmail, String username, String password, ArrayList<Event> pastEvent, ArrayList<Event> yourEvent, ArrayList<Event> requestAccepted, ArrayList<Event> requestRejected, ArrayList<Pair<Volunteer, Event>> pendingRequest, boolean isAdmin, String secretIdentification, ArrayList<Event> yourEventRejected, ArrayList<Integer> reasonForRejection, ArrayList<Event> yourEventAccepted, ArrayList<String> violation, boolean justReceiveWarning, ArrayList<String> programNotification) {
//        this.age = age;
//        this.phoneNumber = phoneNumber;
//        this.currentLocation = currentLocation;
//        this.gmail = gmail;
//        this.username = username;
//        this.password = password;
//        this.pastEvent = pastEvent;
//        this.yourEvent = yourEvent;
//        this.requestAccepted = requestAccepted;
//        this.requestRejected = requestRejected;
//        this.pendingRequest = pendingRequest;
//        this.isAdmin = isAdmin;
//        this.secretIdentification = secretIdentification;
//        this.yourEventRejected = yourEventRejected;
//        this.reasonForRejection = reasonForRejection;
//        this.yourEventAccepted = yourEventAccepted;
//        this.violation = violation;
//        this.justReceiveWarning = justReceiveWarning;
//        this.programNotification = programNotification;
//    }


    public ArrayList<Event> getPastEventCompleted() {
        return pastEventCompleted;
    }

    public void setPastEventCompleted(ArrayList<Event> pastEventCompleted) {
        this.pastEventCompleted = pastEventCompleted;
    }

    public ArrayList<Event> getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(ArrayList<Event> currentEvent) {
        this.currentEvent = currentEvent;
    }

    public ArrayList<String> getProgramNotification() {
        return programNotification;
    }

    public void setProgramNotification(ArrayList<String> programNotification) {
        this.programNotification = programNotification;
    }

    public boolean isJustReceiveWarning() {
        return justReceiveWarning;
    }

    public void setJustReceiveWarning(boolean justReceiveWarning) {
        this.justReceiveWarning = justReceiveWarning;
    }

    public ArrayList<String> getViolation() {
        return violation;
    }

    public void setViolation(ArrayList<String> violation) {
        this.violation = violation;
    }

    public ArrayList<Event> getYourEventAccepted() {
        return yourEventAccepted;
    }

    public void setYourEventAccepted(ArrayList<Event> yourEventAccepted) {
        this.yourEventAccepted = yourEventAccepted;
    }

    public ArrayList<Event> getYourEventRejected() {
        return yourEventRejected;
    }

    public void setYourEventRejected(ArrayList<Event> yourEventRejected) {
        this.yourEventRejected = yourEventRejected;
    }

    public ArrayList<Integer> getReasonForRejection() {
        return reasonForRejection;
    }

    public void setReasonForRejection(ArrayList<Integer> reasonForRejection) {
        this.reasonForRejection = reasonForRejection;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public ArrayList<Pair<Volunteer, Event>> getPendingRequest() {
        return pendingRequest;
    }

    public void setPendingRequest(ArrayList<Pair<Volunteer, Event>> pendingRequest) {
        this.pendingRequest = pendingRequest;
    }

    public ArrayList<Event> getRequestAccepted() {
        return requestAccepted;
    }

    public void setRequestAccepted(ArrayList<Event> requestAccepted) {
        this.requestAccepted = requestAccepted;
    }

    public ArrayList<Event> getRequestRejected() {
        return requestRejected;
    }

    public void setRequestRejected(ArrayList<Event> requestRejected) {
        this.requestRejected = requestRejected;
    }

    public String getSecretIdentification() {
        return secretIdentification;
    }

    public void setSecretIdentification(String secretIdentification) {
        this.secretIdentification = secretIdentification;
    }

    public ArrayList<Event> getYourEvent() {
        return yourEvent;
    }

    public void setYourEvent(ArrayList<Event> yourEvent) {
        this.yourEvent = yourEvent;
    }

    public ArrayList<Event> getPastEvent() {
        return pastEvent;
    }

    public void setPastEvent(ArrayList<Event> pastEvent) {
        this.pastEvent = pastEvent;
    }

    public Volunteer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
