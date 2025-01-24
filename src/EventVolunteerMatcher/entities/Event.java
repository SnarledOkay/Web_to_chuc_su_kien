package EventVolunteerMatcher.entities;

import java.time.LocalDate;
import java.util.ArrayList;

public class Event {
    private int id ;
    private static int autoID ;
    private String eventName ;
    private LocalDate eventDate ;
    private String location ;
    private int typeOfVolunteerWork ;
    private int minimumAge ;
    private Volunteer mainOrganizer ;
    private ArrayList<Volunteer> co_organizerList ;
    private int volunteerLimit ;
    private String mainOrganizerTelephone ;
    private String mainOrganizerGmail ;
    private ArrayList<Volunteer> participantList ;

    public Event(int id, String eventName, LocalDate eventDate, String location, int typeOfVolunteerWork, int minimumAge, Volunteer mainOrganizer, ArrayList<Volunteer> co_organizerList, int volunteerLimit, String mainOrganizerTelephone, String mainOrganizerGmail, ArrayList<Volunteer> participantList) {
        this.id = id;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.location = location;
        this.typeOfVolunteerWork = typeOfVolunteerWork;
        this.minimumAge = minimumAge;
        this.mainOrganizer = mainOrganizer;
        this.co_organizerList = co_organizerList;
        this.volunteerLimit = volunteerLimit;
        this.mainOrganizerTelephone = mainOrganizerTelephone;
        this.mainOrganizerGmail = mainOrganizerGmail;
        this.participantList = participantList;
    }

    public int getDifference(){
        return volunteerLimit - participantList.size() ;
    }
    public ArrayList<Volunteer> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(ArrayList<Volunteer> participantList) {
        this.participantList = participantList;
    }

//    public Event(int id, String eventName, LocalDate eventDate, String location, int typeOfVolunteerWork, int minimumAge, Volunteer mainOrganizer, ArrayList<Volunteer> co_organizerList, int volunteerLimit, String mainOrganizerTelephone, String mainOrganizerGmail) {
//        this.id = id ;
//        this.eventName = eventName;
//        this.eventDate = eventDate;
//        this.location = location;
//        this.typeOfVolunteerWork = typeOfVolunteerWork;
//        this.minimumAge = minimumAge;
//        this.mainOrganizer = mainOrganizer;
//        this.co_organizerList = co_organizerList;
//        this.volunteerLimit = volunteerLimit;
//        this.mainOrganizerTelephone = mainOrganizerTelephone;
//        this.mainOrganizerGmail = mainOrganizerGmail;
//    }

    public int getVolunteerLimit() {
        return volunteerLimit;
    }

    public void setVolunteerLimit(int volunteerLimit) {
        this.volunteerLimit = volunteerLimit;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTypeOfVolunteerWork() {
        return typeOfVolunteerWork;
    }

    public void setTypeOfVolunteerWork(int typeOfVolunteerWork) {
        this.typeOfVolunteerWork = typeOfVolunteerWork;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Volunteer getMainOrganizer() {
        return mainOrganizer;
    }

    public void setMainOrganizer(Volunteer mainOrganizer) {
        this.mainOrganizer = mainOrganizer;
    }

    public ArrayList<Volunteer> getCo_organizerList() {
        return co_organizerList;
    }

    public void setCo_organizerList(ArrayList<Volunteer> co_organizerList) {
        this.co_organizerList = co_organizerList;
    }

    public String getMainOrganizerTelephone() {
        return mainOrganizerTelephone;
    }

    public void setMainOrganizerTelephone(String mainOrganizerTelephone) {
        this.mainOrganizerTelephone = mainOrganizerTelephone;
    }

    public String getMainOrganizerGmail() {
        return mainOrganizerGmail;
    }

    public void setMainOrganizerGmail(String mainOrganizerGmail) {
        this.mainOrganizerGmail = mainOrganizerGmail;
    }

    @Override
    public String toString() {
        return  "\n" + eventName +
                "\nID: " + id +
                "\nDate: " + eventDate +
                "\nLocation: " + location +
                "\nDescription: " + typeOfVolunteerWork +
                "\nAge Requirement: " + minimumAge + "+" +
                "\nMain organizer: " + mainOrganizer.getUsername() +
                "\nNumber of co-organizers: " + co_organizerList.size() +
                "\nVolunteers required: " + volunteerLimit +
                "\nContact number: " + mainOrganizerTelephone +
                "\nEmail: " + mainOrganizerGmail ;
    }
}

