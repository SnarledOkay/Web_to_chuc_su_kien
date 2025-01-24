package EventVolunteerMatcher.data;

import EventVolunteerMatcher.entities.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class DataBase {
    public static ArrayList<Volunteer> volunteerList = new ArrayList<>() ;
    public static ArrayList<Event> eventList = new ArrayList<>() ;
    public static ArrayList<Organizer> co_organizerList = new ArrayList<>() ;
    public static ArrayList<Event> searchEventList = new ArrayList<>() ;
    public static ArrayList<Pair<Volunteer,Event>> acceptParticipation= new ArrayList<>() ;
    public static ArrayList<Volunteer> participantList = new ArrayList<>() ;
    public static int maxEventName=0, maxEventLocation=0, maxEventTypeWork=30, maxEventAge=0, maxEventVolunteerLimit=0;
    public static int currentYear = 2025 , harborVariable=0 ;
    public static ArrayList<Event> pendingEventList = new ArrayList<>() ;
    public static ArrayList<Volunteer> pendingAccountList = new ArrayList<>() ;
    public static ArrayList<String> volunteerWorkList = new ArrayList<>() ;
    public static ArrayList<String> pendingCategoryList = new ArrayList<>() ;
    public static ArrayList<Event> eventSatisfyCondition = new ArrayList<>() ;
    public static ArrayList<String> rejectEventReason = new ArrayList<>() ;
    public static ArrayList<String> warningToUser = new ArrayList<>() ;
    public static ArrayList<Volunteer> adminList = new ArrayList<>() ;
    public static ArrayList<PairReport<Volunteer,Integer>> pendingReport = new ArrayList<>() ;
    public static boolean inputValidity ;
    public static ArrayList<PairReport<Volunteer,Integer>> pendingAdminReport = new ArrayList<>() ;
    public static ArrayList<String> volunteerMenu = new ArrayList<>() ;
    public static ArrayList<String> adminMenu = new ArrayList<>() ;
    public static ArrayList<String> mainAdminMenu = new ArrayList<>() ;
    public static ArrayList<String> locationList = new ArrayList<>() ;
    public static ArrayList<Event> eventCancelled = new ArrayList<>() ;
    public static ArrayList<Event> eventCompleted = new ArrayList<>() ;

    static {
        locationList.add("N/A") ;
        locationList.add("Hà Nội") ;
        locationList.add("Hồ Chí Minh/Sài gòn") ;
        locationList.add("Hải Phòng") ;
        locationList.add("Đà Nẵng") ;
        locationList.add("Cần Thơ") ;
        locationList.add("Hue") ;
        locationList.add("Hạ Long") ;
        locationList.add("Nha Trang") ;
        locationList.add("Vũng Tàu") ;
        locationList.add("Bến Tre") ;
        locationList.add("Cà Mau") ;
    }
    static {
        mainAdminMenu.add("1 - View lists of admins");
        mainAdminMenu.add("2 - Assign user as admin");
        mainAdminMenu.add("3 - Remove Admin role of an admin");
        mainAdminMenu.add("4 - Send Warning to an Admin");
        mainAdminMenu.add("5 - View reports towards admins");
        mainAdminMenu.add("6 - Return to admin menu") ;
    }
    static {
        adminMenu.add("0 - Access Main Admin functions");
        adminMenu.add("1 - View accounts waiting to be activated");
        adminMenu.add("2 - View events waiting to be approved");
        adminMenu.add("3 - View reports from users");
        adminMenu.add("4 - Send a warning to an user");
        adminMenu.add("5 - Return to main menu");
    }
    static {
        volunteerMenu.add("0 - Access admin functions");
        volunteerMenu.add("1 - View events") ;
        volunteerMenu.add("2 - Create new events");
        volunteerMenu.add("3 - Search event");
        volunteerMenu.add("4 - Sign up for an event");
        volunteerMenu.add("5 - View past events you have joined");
        volunteerMenu.add("6 - View events that you host");
        volunteerMenu.add("7 - View past events you have joined") ;
        volunteerMenu.add("8 - View requests to participate in your events");
        volunteerMenu.add("9 - Complete an event of yours") ;
        volunteerMenu.add("10 - Cancel an event") ;
        volunteerMenu.add("11 - Change personal information");
        volunteerMenu.add("12 - Report an user");
        volunteerMenu.add("13 - View community guidelines");
        volunteerMenu.add("14 - Log out");
    }

    static {
        int adminAge = 18 ;
        String adminTelephone = "0902466966" ;
        String currentLocation = "Ha Noi" ;
        String gmail = "huync0603@gmail.com" ;
        String username = "rosmontis" ;
        String password = "15081945" ;
        boolean isMainAdmin = true ;
        ArrayList<Event> pastEvent = new ArrayList<>() ;
        ArrayList<Event> yourEvent = new ArrayList<>() ;
        ArrayList<Event> pastEventCompleted  = new ArrayList<>() ;
        ArrayList<Event> requestAccepted = new ArrayList<>() ;
        ArrayList<Event> requestRejected = new ArrayList<>();
        ArrayList<Pair<Volunteer,Event>> pendingRequest = new ArrayList<>();
        String secretID = "06032008" ;
        ArrayList<Event> yourEventRejected = new ArrayList<>();
        ArrayList<Integer> reasonForRejection = new ArrayList<>();
        ArrayList<Event> yourEventAccepted = new ArrayList<>() ;
        ArrayList<String> violation = new ArrayList<>();
        boolean neverReceiveWarning = false ;
        ArrayList<String> programNotification = new ArrayList<>() ;
        ArrayList<Event> currentEvent = new ArrayList<>();
        Volunteer newVolunteer = new Volunteer(adminAge,adminTelephone,currentLocation,gmail,username,password,yourEvent, currentEvent,pastEvent,pastEventCompleted,requestAccepted,requestRejected,pendingRequest,isMainAdmin,secretID,yourEventRejected,reasonForRejection,yourEventAccepted,violation,neverReceiveWarning,programNotification);
        volunteerList.add(newVolunteer) ;
        adminList.add(newVolunteer) ;
    }
    static {
        warningToUser.add("Suspected of Admin role exploitation") ;
        warningToUser.add("Suspected of trolling") ;
        warningToUser.add("Suspicion activity detected");
        warningToUser.add("Discrimination detected") ;
        warningToUser.add("Spamming behavior detected") ;
        warningToUser.add("Failure to provide correct information");
        warningToUser.add("Suspected of false report") ;
    }

    static {
        rejectEventReason.add("General violation of Community Guidelines");
        rejectEventReason.add("Suspected of trolling");
        rejectEventReason.add("Suspicion activity detected");
        rejectEventReason.add("Discrimination detected");
        rejectEventReason.add("Unsuitable age requirement");
        rejectEventReason.add("Event deemed as dangerous/risky");
        rejectEventReason.add("Spamming behaviour detected");
    }
    static {
        volunteerWorkList.add("Miscellaneous") ;
        volunteerWorkList.add("Community service") ;
        volunteerWorkList.add("Education and Mentoring") ;
        volunteerWorkList.add("Health and Wellness") ;
        volunteerWorkList.add("Environmental Conservation") ;
        volunteerWorkList.add("Event preparation");
        volunteerWorkList.add("Animal Welfare") ;
        volunteerWorkList.add("Anniversary Celebration") ;
        volunteerWorkList.add("Charity") ;
    }


}
