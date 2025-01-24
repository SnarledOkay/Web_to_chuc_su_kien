package EventVolunteerMatcher.menu;

import EventVolunteerMatcher.data.DataBase;
import EventVolunteerMatcher.entities.Event;
import EventVolunteerMatcher.entities.Volunteer;
import EventVolunteerMatcher.service.AdminService;
import EventVolunteerMatcher.service.EventService;
import EventVolunteerMatcher.service.VolunteerService;
import EventVolunteerMatcher.utils.Utiles;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    EventService eventService = new EventService() ;
    Utiles utiles = new Utiles() ;
    Scanner scanner = new Scanner(System.in) ;
    VolunteerService volunteerService = new VolunteerService() ;
    AdminService adminService = new AdminService() ;
    public void displayDefaultMenu(){
        System.out.println();
        System.out.println("1 - Sign in");
        System.out.println("2 - Sign up");
        System.out.println("Press any other number to escape");
    }

    public void chooseDefaultMenu(int choice){
        switch (choice){
            case 1:
                Volunteer signInAccount = volunteerService.SignInService(scanner) ;
                if(signInAccount==null) return;
                displayVolunteerMenuSignIn(signInAccount);
                int menuChoice=0 ;
                do{
                    displayVolunteerMenu(signInAccount);
                    System.out.print("Choose your next step: ");
                    menuChoice = utiles.enterInteger(scanner) ;
                    chooseVolunteerMenu(scanner,signInAccount,menuChoice);
                }
                while(menuChoice != DataBase.volunteerMenu.size()-1) ;
                break ;
            case 2:
                Volunteer newAccount = volunteerService.createNewAccount(scanner) ;
                if(newAccount != null){
                    DataBase.pendingAccountList.add(newAccount) ;
                    System.out.println("Account registration completed");
                    System.out.println("Please wait until an Admin accepts your request");
                }
                break ;
            default:
                System.out.println("Goodbye");
                System.exit(0);
                break ;
        }
    }

    public void displayVolunteerMenu(Volunteer volunteer){
        System.out.println();
        if(volunteer.isAdmin()) System.out.println("0 - Access admin functions");
        System.out.println("1 - View events") ;
        System.out.println("2 - Create new events");
        System.out.println("3 - Search event");
        System.out.println("4 - Sign up for an event");
        System.out.println("5 - Cancel participation for event");
        System.out.println("6 - View events you are joining");
        System.out.println("7 - View events that you had joined");
        System.out.println("8 - View events that you are hosting");
        System.out.println("9 - View events that you had hosted");
        System.out.println("10 - View requests to participate in your events");
        System.out.println("11 - Complete an event of yours");
        System.out.println("12 - Cancel an event");
        System.out.println("13 - Change personal information");
        System.out.println("14 - Report an user");
        System.out.println("15 - View community guidelines");
        System.out.println("16 - View account information");
        System.out.println("17 - Log out");
    }

    public void displayVolunteerMenuSignIn(Volunteer volunteer){
        ArrayList<Event> accept = volunteer.getRequestAccepted() ;
        ArrayList<Event> reject = volunteer.getRequestRejected() ;
        ArrayList<Event> eventAccepted = volunteer.getYourEventAccepted() ;
        ArrayList<Event> eventRejected = volunteer.getYourEventRejected() ;
        int numberNotification = accept.size()+reject.size()+eventRejected.size()+eventAccepted.size() ;
        if(!volunteer.getProgramNotification().isEmpty()){
            for(int i = 0 ; i < volunteer.getProgramNotification().size();i++){
                System.out.println(volunteer.getProgramNotification().get(i));
            }
            volunteer.getProgramNotification().clear();
        }
        if(volunteer.isAdmin()){
            System.out.println("Welcome, Admin " + volunteer.getUsername());
            int sumNotification = DataBase.pendingAccountList.size()+DataBase.pendingEventList.size() +DataBase.pendingCategoryList.size() + numberNotification ;
            System.out.println("You have " + sumNotification + " new notification");
            System.out.println("There are " + DataBase.pendingAccountList.size() + " accounts waiting to be activated");
            System.out.println("There are " + DataBase.pendingEventList.size() + " events waiting to be accepted");
            System.out.println("There are " + DataBase.pendingReport.size() + " reports of violation");
            if(!DataBase.pendingCategoryList.isEmpty()){
                System.out.println("Some admins have requested for " + DataBase.pendingCategoryList.size() + " new event categories");
            }
        }
        else{
            System.out.println("Welcome, User " + volunteer.getUsername());
            if(volunteer.isJustReceiveWarning()){
                System.out.println("You just received a warning from an admin!");
                System.out.println("Reason: " + volunteer.getViolation().get(volunteer.getViolation().size()-1) + "\n");
                volunteer.setJustReceiveWarning(false);
            }
            System.out.println("You have " + numberNotification + " new notification(s)");
        }
        for(Event event:accept){
            System.out.println("Your request to participate the event " + event.getEventName() + " has been accepted");
        }
        volunteer.getRequestAccepted().clear();
        for (Event event : reject) {
            System.out.println("Your request to participate the event " + event.getEventName() + " has been rejected");
        }
        volunteer.getRequestRejected().clear();
        for(Event event:eventAccepted){
            System.out.println("Your request to create the event " + event.getEventName() + " has been accepted");
        }
        volunteer.getYourEventAccepted().clear();
        for(int i = 0 ; i < eventRejected.size() ; i++){
            Event event = eventRejected.get(i) ;
            System.out.println("Your request to create the event " + event.getEventName()+ " has been rejected");
            System.out.println("Reason: " + DataBase.rejectEventReason.get(volunteer.getReasonForRejection().get(i)));
        }
        volunteer.getReasonForRejection().clear();
        volunteer.getYourEventRejected().clear();
    }


    public void chooseVolunteerMenu(Scanner scanner, Volunteer volunteer, int choice){
        int option=0 ;
        switch (choice){
            case 0:
                if(volunteer.isAdmin()){
                    int selection ;
                    do{
                        displayAdminMenu(volunteer);
                        System.out.print("Choose your next step: ");
                        DataBase.inputValidity = false ;
                        selection = utiles.enterInteger(scanner) ;
                        chooseAdminMenu(scanner,volunteer,selection);
                    }
                    while (selection != DataBase.adminMenu.size()-1) ;
                }
                else System.out.println("Invalid choice, please choose again");
                break;
            case 1:
                int nextStep ;
                do{
                    viewEventMenu();
                    System.out.print("Choose your next step: ");
                    nextStep = utiles.enterInteger(scanner) ;
                    chooseViewEventMenu(scanner,nextStep);
                }
                while(nextStep != 6) ;
                break ;
            case 2:
                Event newEvent = eventService.createNewEvent(scanner, volunteer) ;
                if(volunteer.isAdmin()) DataBase.eventList.add(newEvent) ;
                else DataBase.pendingEventList.add(newEvent) ;
                break;
            case 3:
                if(DataBase.eventList.isEmpty()){
                    System.out.println("Function unavailable because there's no event");
                    break;
                }
                do{
                    displaySearchEventMenu();
                    System.out.print("Choose how you'd like to search: ");
                    option = utiles.enterInteger(scanner) ;
                    chooseSearchEventMenu(scanner,option);
                }
                while (option != 8) ;
                System.out.println("Returning to main menu...");
                break ;
            case 4:
                if(DataBase.eventList.isEmpty()){
                    System.out.println("Function unavailable because there's no event happening");
                    break;
                }
                volunteerService.signUpForEvent(volunteer,scanner);
                break;
            case 5:
                volunteerService.viewEventsYouParticipated(volunteer);
                while(true){
                    System.out.print("Choose the event you want to cancel participation (press -1 to exit): ");
                    int chooseEvent = utiles.enterInteger(scanner) ;
                    if(chooseEvent == -1){
                        System.out.println("Returning to main menu...");
                        break ;
                    }
                    if(chooseEvent <= 0 || chooseEvent >= volunteer.getCurrentEvent().size()){
                        System.out.println("Invalid choice, please enter again");
                    }
                    else{
                        System.out.println("Confirm that you want to cancel your participation: Yes/No");
                        String confirm  ;
                        while(true){
                            confirm = scanner.nextLine() ;
                            if(confirm.equalsIgnoreCase("yes")){
                                System.out.println("Action confirmed");
                                Event chosenEvent = volunteer.getCurrentEvent().get(chooseEvent) ;
                                volunteerService.cancelParticipation(volunteer,chosenEvent);
                                return;
                            }
                            else if(confirm.equalsIgnoreCase("no")){
                                System.out.println("Action cancelled");
                                break;
                            }
                            else{
                                System.out.println("Invalid choice, please enter again");
                            }
                        }
                        break ;
                    }
                }
                break;
            case 6:
                volunteerService.viewEventYouAreJoining(volunteer);
                break;
            case 7:
                volunteerService.viewEventsYouParticipated(volunteer);
                break;
            case 8:
                volunteerService.viewEventsYouCreated(volunteer);
                break;
            case 9:
                volunteerService.viewYourEventCompleted(volunteer);
                break;
            case 10:
                volunteerService.confirmParticipation(scanner,volunteer);
                break ;
            case 11:
                volunteerService.viewEventsYouCreated(volunteer);
                System.out.print("Choose event that has finished: ");
                int chooseEvent=0 ;
                while(true){
                    chooseEvent = utiles.enterInteger(scanner) ;
                    if(chooseEvent < 0 || chooseEvent >= volunteer.getYourEvent().size()){
                        System.out.println("Invalid choice, please enter again");
                    }
                    else break ;
                }
                Event eventCompleted = volunteer.getYourEvent().get(chooseEvent) ;
                volunteerService.completeAnEvent(scanner,eventCompleted);
                break;
            case 12:
                volunteerService.viewEventsYouCreated(volunteer);
                System.out.print("Select event that has finished: ");
                int chooseCancelEvent=0 ;
                while(true){
                    chooseCancelEvent = utiles.enterInteger(scanner) ;
                    if(chooseCancelEvent < 0 || chooseCancelEvent >= volunteer.getYourEvent().size()){
                        System.out.println("Invalid choice, please enter again");
                    }
                    else break ;
                }
                Event eventCancelled = volunteer.getYourEvent().get(chooseCancelEvent) ;
                volunteerService.cancelAnEvent(scanner,eventCancelled);
                break;
            case 13:
                displayChangeInformationMenu();
                do{
                    System.out.print("Choose what you want to change: ");
                    DataBase.inputValidity = false ;
                    option = utiles.enterInteger(scanner) ;
                    chooseChangeInformationMenu(scanner,option,volunteer);
                }
                while(option >= 1 && option <= 5) ;
                System.out.println("Returning to main menu...");
                break ;
            case 14:
                System.out.println("Choose how you want to report: ");
                System.out.println("1 - Report an user");
                System.out.println("2 - Report an admin");
                while (true){
                    DataBase.inputValidity = false;
                    option = utiles.enterInteger(scanner);
                    if(option==1){
                        volunteerService.reportAnotherUser(scanner,volunteer);
                        break;
                    }
                    else if(option == 2){
                        volunteerService.reportAnAdmin(scanner,volunteer);
                        break;
                    }
                    else System.out.println("Invalid choice, please enter again");
                }
                System.out.println("Returning to main menu...");
                break ;
            case 15:
                utiles.displayCommunityGuideline();
                break ;
            case 16:
                volunteerService.viewAccountInformation(volunteer);
                break;
            case 17:
                System.out.println("Logging out...");
                return;
            default:
                System.out.println("Invalid choice, please choose again:");
                break;
        }
    }

    public void displayChangeInformationMenu(){
        System.out.println();
        System.out.println("1 - Change username");
        System.out.println("2 - Change telephone number");
        System.out.println("3 - Change current place of living");
        System.out.println("4 - Change email");
        System.out.println("5 - Change password");
        System.out.println("Press any other number to escape");
    }

    public void displaySearchEventMenu(){
        System.out.println();
        System.out.println("Search event by: ");
        System.out.println("1 - Search by Location");
        System.out.println("2 - Search by Date");
        System.out.println("3 - Search by Category");
        System.out.println("4 - Search by Location and Date");
        System.out.println("5 - Search by Location and Category");
        System.out.println("6 - Search by Date and Category");
        System.out.println("7 - Search by Location, Date and Category");
        System.out.println("8 - Return to main menu");
    }

    public void chooseSearchEventMenu(Scanner scanner, int option){
        String location ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        LocalDate date ;
        int chooseLocation = 1;
        int category=0 ;
        switch (option){
            case 1:
                utiles.viewLocation();
                while (true){
                    chooseLocation = utiles.enterInteger(scanner) ;
                    if(chooseLocation < 0 || chooseLocation >= DataBase.locationList.size()){
                        System.out.println("Invalid choice, please enter again");
                    }
                    else break ;
                }
                location = DataBase.locationList.get(chooseLocation);
                eventService.searchEventByLocation(location);
                break ;
            case 2:
                date = utiles.setDateForEvent(scanner) ;
                eventService.searchEventByDate(date);
                break ;
            case 3:
                displaySearchEventMenu();
                System.out.println("Enter category: ");
                category = utiles.enterInteger(scanner);
                if(category>=DataBase.volunteerWorkList.size()) category = 0 ;
                if(category==0) System.out.println("Category will be set to 'Miscellaneous'");
                eventService.searchEventByCategory(category);
                break ;
            case 4:
                utiles.viewLocation();
                while (true){
                    chooseLocation = utiles.enterInteger(scanner) ;
                    if(chooseLocation < 0 || chooseLocation >= DataBase.locationList.size()){
                        System.out.println("Invalid choice, please enter again");
                    }
                    else break ;
                }
                location = DataBase.locationList.get(chooseLocation);
                date = utiles.setDateForEvent(scanner) ;
                eventService.searchEventLocationDate(location,date);
                break;
            case 5:
                date = utiles.setDateForEvent(scanner) ;
                utiles.displayTypeOfVolunteer();
                category = utiles.enterInteger(scanner);
                if(category >= DataBase.volunteerWorkList.size()) category = 0 ;
                eventService.searchEventDateCategory(date,category);
                break;
            case 6:
                utiles.viewLocation();
                while (true){
                    chooseLocation = utiles.enterInteger(scanner) ;
                    if(chooseLocation < 0 || chooseLocation >= DataBase.locationList.size()){
                        System.out.println("Invalid choice, please enter again");
                    }
                    else break ;
                }
                location = DataBase.locationList.get(chooseLocation);
                utiles.displayTypeOfVolunteer();
                category = utiles.enterInteger(scanner) ;
                if(category >= DataBase.volunteerWorkList.size()) category = 0 ;
                if(category==0) System.out.println("Category has been set to 'Miscellaneous'");
                eventService.searchEventLocationCategory(location,category);
                break;
            case 7:
                utiles.viewLocation();
                while (true){
                    chooseLocation = utiles.enterInteger(scanner) ;
                    if(chooseLocation < 0 || chooseLocation >= DataBase.locationList.size()){
                        System.out.println("Invalid choice, please enter again");
                    }
                    else break ;
                }
                location = DataBase.locationList.get(chooseLocation);
                date = utiles.setDateForEvent(scanner) ;
                utiles.displayTypeOfVolunteer();
                category = utiles.enterInteger(scanner) ;
                if(category >= DataBase.volunteerWorkList.size()){
                    category = 0 ;
                    System.out.println("Category has been set to 'Miscellaneous");
                }
                eventService.searchEventLocationDateCategory(location,date,category);
                break;
            case 8:
                System.out.println("Process terminated");
                return;
            default:
                System.out.println("Invalid choice, please choose again");
                break;
        }
    }

    public void chooseChangeInformationMenu(Scanner scanner, int choice, Volunteer volunteer){
        switch (choice){
            case 1:
                System.out.println("Enter new username (enter 'exit' to escape): ");
                String username = scanner.nextLine() ;
                username = username.trim() ;
                while(utiles.checkUserNameExist(username)){
                    if(username.equals(volunteer.getUsername())) System.out.println("New username can't be similar to current username");
                    else System.out.println("Another user is registering under this username");
                    System.out.println("Please enter again (or press exit to escape)");
                    username = scanner.nextLine() ;
                    username = username.trim() ;
                    if(username.equalsIgnoreCase("exit")) break ;
                }
                if(username.equalsIgnoreCase("exit")){
                    System.out.println("Action cancelled");
                    return;
                }
                else{
                    volunteer.setUsername(username) ;
                    System.out.println("New username has been saved");
                }
                break ;
            case 2:
                System.out.println("Enter new telephone number (enter 'exit' to escape): ");
                String telephoneNumber = scanner.nextLine() ;
                telephoneNumber = telephoneNumber.trim() ;
                while (!utiles.checkTelephoneNumberExist(telephoneNumber)){
                    if(telephoneNumber.equals(volunteer.getPhoneNumber())) System.out.println("New phone number can't be similar to current one");
                    else System.out.println("Another user is already using this number");
                    System.out.println("Please enter again (or press exit to escape): ");
                    telephoneNumber = scanner.nextLine() ;
                    telephoneNumber = telephoneNumber.trim() ;
                    if(telephoneNumber.equalsIgnoreCase("exit")) break ;
                }
                if(telephoneNumber.equalsIgnoreCase("exit")) break ;
                else{
                    volunteer.setPhoneNumber(telephoneNumber);
                    System.out.println("New telephone number has been saved");
                }
                break ;
            case 3:
                System.out.println("Enter new place of living (enter 'exit' to escape): ");
                String newPlaceOfLiving = scanner.nextLine() ;
                if(newPlaceOfLiving.equalsIgnoreCase("exit")){
                    System.out.println("Action cancelled");
                    return;
                }
                newPlaceOfLiving = newPlaceOfLiving.trim() ;
                volunteer.setCurrentLocation(newPlaceOfLiving);
                System.out.println("New place of living has been saved");
                break;
            case 4:
                System.out.println("Enter new email (enter 'exit' to escape): ");
                String newGmail = scanner.nextLine() ;
                newGmail = newGmail.trim();
                while (!utiles.checkGmailExist(newGmail)){
                    if(newGmail.equalsIgnoreCase("exit")) break ;
                    if(newGmail.equals(volunteer.getGmail())) System.out.println("New email cannot be similar to current email");
                    else System.out.println("Another user is already using this email");
                    System.out.println("Enter new email again: ");
                    newGmail = scanner.nextLine() ;
                    newGmail = newGmail.trim() ;
                }
                if(newGmail.equalsIgnoreCase("exit")){
                    System.out.println("Action cancelled");
                    return;
                }
                else{
                    volunteer.setGmail(newGmail);
                    System.out.println("New email has been saved");
                }
                break ;
            case 5:
                System.out.println("Enter current password: ");
                String password ;
                boolean checker ;
                do{
                    checker = false ;
                    password = scanner.nextLine() ;
                    password = password.trim() ;
                    if(password.equalsIgnoreCase("exit")) {
                        System.out.println("Process terminated");
                        break;
                    }
                    if(!utiles.checkPasswordTrue(password,volunteer)){
                        System.out.println("Wrong password, please enter again");
                    }
                    else checker = true ;
                }
                while(!checker) ;
                if(password.equalsIgnoreCase("exit")) break ;
                System.out.println("Password correct, enter new password: ");
                do{
                    password = scanner.nextLine() ;
                    password = password.trim() ;
                    if(password.equals(volunteer.getPassword())) System.out.println("New password has to be different from former password");
                    else{
                        System.out.println("Password accepted");
                        volunteer.setPassword(password);
                    }
                }
                while (password.equals(volunteer.getPassword())) ;
                break;
        }

    }

    public void displayAdminMenu(Volunteer admin){
        System.out.println();
        if(admin.getUsername().equals(DataBase.adminList.get(0).getUsername())) System.out.println("0 - Access Main Admin functions");
        System.out.println("1 - View accounts waiting to be activated");
        System.out.println("2 - View events waiting to be approved");
        System.out.println("3 - View reports from users");
        System.out.println("4 - Send a warning to an user");
        System.out.println("5 - Return to main menu");
    }

    public void chooseAdminMenu(Scanner scanner,Volunteer volunteer,int choice){
        switch (choice){
            case 0:
                if(DataBase.adminList.get(0).getUsername().equals(volunteer.getUsername())){
                    displayMainAdminMenu();
                    int nextStep = 1 ;
                    do{
                        System.out.println("Choose your next step: ");
                        nextStep = utiles.enterInteger(scanner) ;
                        chooseMainAdminMenu(scanner,nextStep);
                    }
                    while (nextStep != DataBase.mainAdminMenu.size()) ;
                }
                else{
                    System.out.println("Invalid choice, please enter again");
                }
            case 1:
                adminService.checkPendingAccount(scanner);
                System.out.println("Returning to admin menu...");
                break;
            case 2:
                adminService.checkPendingEvent(scanner);
                System.out.println("Returning to admin menu...");
                break;
            case 3:
                adminService.checkReportFromUser(scanner);
                break ;
            case 4:
                adminService.sendWarningToUser(scanner);
                break;
            case 5:
                System.out.println("Returning to main menu...");
                break;
        }
    }

    public void displayMainAdminMenu(){
        System.out.println();
        System.out.println("1 - View lists of admins");
        System.out.println("2 - Assign user as admin");
        System.out.println("3 - Remove Admin role of an admin");
        System.out.println("4 - Send Warning to an Admin");
        System.out.println("5 - View reports towards admins");
        System.out.println("6 - Return to admin menu");
    }

    public void chooseMainAdminMenu(Scanner scanner,int choice){
        switch (choice){
            case 1:
                utiles.viewAllAdmin();
                break ;
            case 2:
                adminService.assignUserAsAdmin(scanner);
                break ;
            case 3:
                utiles.viewAllAdmin();
                System.out.print("Choose the admin you want to demote: ");
                DataBase.inputValidity = false ;
                int option = utiles.enterInteger(scanner) ;
                Volunteer demotedAdmin = DataBase.adminList.get(option) ;
                demotedAdmin.setAdmin(false);
            case 4:
                adminService.sendWarningToAdmin(scanner);
                break;
            case 5:
                adminService.checkReportTowardAdmin(scanner);
                break;
            case 6:
                System.out.println("Returning to admin menu...");
                return;
        }
    }

    public void viewEventMenu(){
        System.out.println("1 - View all events");
        System.out.println("2 - Sort by Location");
        System.out.println("3 - Sort by Date ");
        System.out.println("4 - Sort by Age Requirement");
        System.out.println("5 - Sort by Availability");
        System.out.println("6 - Return to main menu");
    }

    public void chooseViewEventMenu(Scanner scanner, int choice){
        switch (choice){
            case 1:
                eventService.viewAllEvent();
                break;
            case 2:
                eventService.sortEventByLocation();
                break;
            case 3:
                eventService.sortEventByDate();
                break;
            case 4:
                eventService.sortEventByAge();
                break;
            case 5:
                eventService.sortEventByAvailability();
                break;
            case 6:
                System.out.println("Returning to main menu...");
                return;
            default:
                System.out.println("Invalid choice, please enter again");
        }
    }

}