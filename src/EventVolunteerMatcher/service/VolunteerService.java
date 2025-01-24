package EventVolunteerMatcher.service;

import EventVolunteerMatcher.data.DataBase;
import EventVolunteerMatcher.entities.*;
import EventVolunteerMatcher.utils.Utiles;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class VolunteerService {
    Utiles utiles = new Utiles() ;
    EventService eventService = new EventService() ;
    public Volunteer createNewAccount(Scanner scanner){
        System.out.println("Enter information for new account (enter 'exit' to escape): ");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine() ;
        username = username.trim();
        if(username.equalsIgnoreCase("exit")){
            System.out.println("Account creation terminated");
            return null ;
        }
        while (utiles.checkUserNameExist(username)){
            System.out.print("An user is currently registering under this username\nEnter username: ");
            username = scanner.nextLine() ;
            username = username.trim() ;
            if(username.equalsIgnoreCase("exit")){
                System.out.println("Account creation terminated");
                return null ;
            }
        }
        System.out.print("Enter your age (age cannot be changed): ");
        int age = 6;
        while (true){
            age = utiles.enterInteger(scanner) ;
            if(age >= 100 || age < 6){
                System.out.println("Invalid age entered, please enter again");
            }
            else break;
        }
        System.out.print("Enter your telephone number: ");
        String telephoneNumber = scanner.nextLine();
        if(telephoneNumber.equalsIgnoreCase("exit")){
            System.out.println("Account creation terminated");
            return null ;
        }
        while (utiles.checkTelephoneNumberExist(telephoneNumber) || !utiles.checkTelephoneNumberValidity(telephoneNumber)){
            if(!utiles.checkTelephoneNumberValidity(telephoneNumber)) System.out.println("Invalid telephone number (Expected format: Start with '0', only consists of 10 numbers 0-9)");
            else System.out.println("An user is currently registering under this phone number");
            System.out.print("Enter your telephone number: ");
            telephoneNumber = scanner.nextLine() ;
            telephoneNumber = telephoneNumber.trim();
            if(telephoneNumber.equalsIgnoreCase("exit")){
                System.out.println("Account creation terminated");
                return null ;
            }
        }
        utiles.viewLocation();
        int chooseLocation=0 ;
        while(true){
            chooseLocation = utiles.enterInteger(scanner) ;
            if(chooseLocation <= 0 || chooseLocation>=DataBase.locationList.size()){
                System.out.println("Invalid choice, please choose again");
            }
            else{
                System.out.println("Location has been set to " + DataBase.locationList.get(chooseLocation));
                break;
            }
        }
        String location = DataBase.locationList.get(chooseLocation) ;
        System.out.print("Enter your email address: ");
        String email = scanner.nextLine() ;
        email = email.trim();
        if(email.equalsIgnoreCase("exit")){
            System.out.println("Account creation terminated");
            return null ;
        }
        while (utiles.checkGmailExist(email)){
            System.out.println("An user is currently registering under this email");
            System.out.print("Enter your email address: ");
            email = scanner.nextLine() ;
            email = email.trim();
            if(email.equalsIgnoreCase("exit")){
                System.out.println("Account creation terminated");
                return null ;
            }
        }
        System.out.print("Enter password (Requirement: 8-14 characters long, contains 1+ numbers): ");
        String password = scanner.nextLine() ;
        password = password.trim() ;
        if(password.equalsIgnoreCase("exit")){
            System.out.println("Account creation terminated");
            return null ;
        }
        while (!utiles.checkPasswordValidity(password)){
            System.out.println("Invalid password form\nExpected format: 8-14 characters long, contains 1+ numbers");
            System.out.print("Enter your password: ");
            password = scanner.nextLine() ;
            password = password.trim() ;
            if(password.equalsIgnoreCase("exit")){
                System.out.println("Account creation terminated");
                return null ;
            }
        }
        System.out.print("Confirm password: ");
        String againPassword = scanner.nextLine() ;
        againPassword = againPassword.trim() ;
        if(againPassword.equalsIgnoreCase("exit")){
            System.out.println("Account creation terminated");
            return null ;
        }
        while (!againPassword.equals(password)){
            System.out.println("Password doesn't match");
            System.out.print("Confirm password: ");
            againPassword = scanner.nextLine() ;
            againPassword = againPassword.trim();
            if(againPassword.equalsIgnoreCase("exit")){
                System.out.println("Account creation terminated");
                return null ;
            }
        }
        System.out.println("Password matched\n");
        System.out.println("An ID code is required");
        System.out.println("ID code is necessary if you want to change your password");
        System.out.println("Warning: this ID code is fixed and cannot be changed in the future");
        System.out.println("ID code requirement: 3 numbers, 3 characters in capital, NO special character");
        System.out.print("Enter ID code: ");
        String identificationCode = scanner.nextLine() ;
        identificationCode = identificationCode.trim() ;
        while (!utiles.checkIDCodeValidity(identificationCode)){
            System.out.println("Invalid ID code format");
            System.out.print("Enter ID code: ");
            identificationCode = scanner.nextLine() ;
            identificationCode = identificationCode.trim() ;
            if(identificationCode.equalsIgnoreCase("exit")){
                System.out.println("Account creation terminated");
                return null ;
            }
        }
        ArrayList<Event> yourEvent = new ArrayList<>() ;
        ArrayList<Event> pastEvent = new ArrayList<>() ;
        ArrayList<Event> pastEventCompleted = new ArrayList<>() ;
        ArrayList<Event> requestAccepted = new ArrayList<>() ;
        ArrayList<Event> requestRejected = new ArrayList<>() ;
        ArrayList<Pair<Volunteer,Event>> pendingRequest = new ArrayList<>() ;
        boolean isAdmin = false ;
        ArrayList<Integer> reasonEventRejected = new ArrayList<>() ;
        ArrayList<Event> yourEventRejected = new ArrayList<>() ;
        ArrayList<Event> yourEventAccepted = new ArrayList<>() ;
        ArrayList<String> violation = new ArrayList<>() ;
        boolean justReceiveViolation = false ;
        ArrayList<String> programNotification = new ArrayList<>() ;
        ArrayList<Event> currentEvent = new ArrayList<>() ;
        return new Volunteer(age,telephoneNumber,location,email,username,password,yourEvent, currentEvent,pastEvent,pastEventCompleted,requestAccepted,requestRejected,pendingRequest,isAdmin,identificationCode,yourEventRejected, reasonEventRejected, yourEventAccepted, violation, justReceiveViolation, programNotification) ;
    }

    public Volunteer SignInService(Scanner scanner){
        System.out.print("Enter username: ");
        String username = scanner.nextLine() ;
        username = username.trim();
        if(username.equalsIgnoreCase("exit")){
            System.out.println("Sign-in process terminated");
            return null ;
        }
        while (!utiles.checkUserNameExist(username)){
            if(utiles.checkUsernameExistPending(username)){
                System.out.println("Account has not been activated");
                return null ;
            }
            System.out.println("Account doesn't exist");
            System.out.print("Enter username (enter 'exit' to escape): ");
            username = scanner.nextLine() ;
            username = username.trim();
            if(username.equalsIgnoreCase("exit")){
                System.out.println("Sign-in process terminated");
                return null ;
            }
        }
        System.out.println("Username matched");
        System.out.print("Enter password: ");
        Volunteer volunteer = utiles.findVolunteerByUsername(username) ;
        String password = scanner.nextLine() ;
        password = password.trim();
        if(password.equalsIgnoreCase("exit")){
            System.out.println("Sign-in process terminated");
            return null ;
        }
        while(!utiles.checkPasswordTrue(password,volunteer)){
            System.out.println("Password not matched");
            System.out.print("Enter password: ");
            password = scanner.nextLine() ;
            password = password.trim() ;
            if(password.equalsIgnoreCase("exit")) {
                System.out.println("Sign-in process terminated");
                return null ;
            }
        }
        System.out.println("Password matched");
        return volunteer ;
    }

    public void signUpForEvent(Volunteer volunteer, Scanner scanner){
        if(DataBase.eventList.isEmpty()){
            System.out.println("There's no event to participate");
            return;
        }
        eventService.viewAllEvent();
        int option ;
        while(true){
            System.out.print("Select event (enter 0 to stop): ");
            option = utiles.enterInteger(scanner) ;
            if(option == 0){
                System.out.println("Process terminated");
                break;
            }
            else if(option >= DataBase.eventList.size() || option < 0){
                System.out.println("Invalid choice, please enter again");
            }
            else {
                Event chosenEvent = DataBase.eventList.get(option-1) ;
                if(chosenEvent.getMinimumAge() > volunteer.getAge()){
                    System.out.println("You do not meet the age requirement for this event");
                }
                else{
                    System.out.println("Do you confirm that you want to participate? Yes/No");
                    String choice ;
                    while(true){
                        choice = scanner.nextLine() ;
                        if(choice.equalsIgnoreCase("yes")){
                            System.out.println("Your request to participate has been sent to the organizer");
                            Volunteer mainOrganizer = DataBase.eventList.get(option - 1).getMainOrganizer();
                            mainOrganizer.getPendingRequest().add(new Pair<>(volunteer, DataBase.eventList.get(option - 1)));
                            break;
                        }
                        else if(choice.equalsIgnoreCase("no")){
                            System.out.println("Registration cancelled");
                            break;
                        }
                        else{
                            System.out.println("Invalid choice, please enter again");
                        }
                    }
                }

            }
        }
    }

    public void confirmParticipation(Scanner scanner, Volunteer volunteer){
        if(volunteer.getYourEvent().isEmpty()) System.out.println("You can't use this function because you are currently hosting no event");
        else{
            System.out.println("There are " + volunteer.getPendingRequest().size() + " requests pending: ");
            for (Pair<Volunteer,Event> pair:volunteer.getPendingRequest()){
                Volunteer targetVolunteer = pair.getVolunteer() ;
                Event targetEvent = pair.getEvent() ;
                System.out.println("Request to participate by: " + targetVolunteer.getUsername());
                System.out.println("Age: " + targetVolunteer.getAge());
                System.out.println("Place of living: " + targetVolunteer.getCurrentLocation());
                System.out.println("Contact: Phone: " + targetVolunteer.getPhoneNumber() + " / Email: " + targetVolunteer.getGmail());

                System.out.println("Request to participate: " + targetEvent.getEventName());
                System.out.println("Date: " + targetEvent.getEventDate());
                System.out.println("Age requirement: " + targetEvent.getMinimumAge() + "+");
                System.out.println("Location: " + targetEvent.getLocation());
                System.out.println("Description: " + targetEvent.getTypeOfVolunteerWork());

                System.out.println("Do you confirm request to participate?");
                System.out.println("Yes / No (Any other choice will be automatically considered 'no')");
                String choice = scanner.nextLine() ;
                choice = choice.trim() ;
                if(choice.equalsIgnoreCase("yes")){
                    System.out.println("You have accepted request to participate");
                    targetVolunteer.getRequestAccepted().add(targetEvent) ;
                    targetEvent.getParticipantList().add(targetVolunteer) ;
                    targetVolunteer.getCurrentEvent().add(targetEvent) ;
                }
                else{
                    System.out.println("You have rejected request to participate");
                    targetVolunteer.getRequestRejected().add(targetEvent) ;
                }
            }
        }
    }

    public void reportAnotherUser(Scanner scanner,Volunteer volunteerReporter){
        utiles.viewAllUser();
        while(true){
            int option = 1 ;
            System.out.print("Choose user to report (choose 0 to escape): ");
            option = utiles.enterInteger(scanner) ;
            if(option == 0){
                System.out.println("Returning to main menu...");
                break ;
            }
            else if(option < 0 || option >= DataBase.volunteerList.size()){
                System.out.println("Error, user doesn't exist");
            }
            else{
                Volunteer volunteer = DataBase.volunteerList.get(option-1) ;
                for(int i = 1 ; i < DataBase.warningToUser.size() ; i++){
                    System.out.println(i+" - " + DataBase.warningToUser.get(i));
                }
                while(true){
                    System.out.print("Choose reason for report: ");
                    DataBase.inputValidity = false;
                    int choice = utiles.enterInteger(scanner) ;
                    if(choice < 0 || choice >= DataBase.warningToUser.size()){
                        System.out.println("Invalid choice, please enter again");
                    }
                    else{
                        System.out.println("Your report has been sent to admins");
                        DataBase.pendingReport.add(new PairReport<>(volunteerReporter,volunteer,choice)) ;
                        break;
                    }
                }
                break;
            }
        }

    }

    public void reportAnAdmin(Scanner scanner,Volunteer volunteerReporter){
        utiles.viewAllAdmin();
        while(true){
            int option = 1 ;
            System.out.print("Choose admin to report (choose 0 to escape): ");
            option = utiles.enterInteger(scanner) ;
            if(option == 0){
                System.out.println("Returning to main menu...");
                break ;
            }
            else if(option < 0 || option >= DataBase.adminList.size()){
                System.out.println("Error, user doesn't exist");
            }
            else{
                Volunteer volunteer = DataBase.adminList.get(option-1) ;
                for(int i = 1 ; i < DataBase.warningToUser.size() ; i++){
                    System.out.println(i+" - " + DataBase.warningToUser.get(i));
                }
                while(true){
                    System.out.print("Choose reason to report: ");
                    int choice = utiles.enterInteger(scanner) ;
                    if(choice < 0 || choice >= DataBase.warningToUser.size()){
                        System.out.println("Invalid choice, please enter again");
                    }
                    else{
                        System.out.println("Your report has been sent to admins");
                        DataBase.pendingAdminReport.add(new PairReport<>(volunteerReporter,volunteer,choice)) ;
                        break;
                    }
                }
                break;
            }
        }
    }

    public void completeAnEvent(Scanner scanner,Event event){
        System.out.println("Do you confirm the event has finished? Yes/No/Exit");
        String choice ;
        while(true){
            choice = scanner.nextLine() ;
            if(choice.equalsIgnoreCase("exit")){
                System.out.println("Process terminated");
                return;
            }
            if(choice.equalsIgnoreCase("no")){
                System.out.println("Action has been cancelled");
                return;
            }
            if(choice.equalsIgnoreCase("yes")){
                System.out.println("Event has completed, please finish the remaining process");
                break;
            }
            else{
                System.out.println("Invalid choice, please enter again");
            }
        }
        if(event.getParticipantList().isEmpty()){
            System.out.println("Sorry, no one participated in this event :(");
        }
        else{
            System.out.println("Please confirm the participation of volunteers: ");
            ArrayList<Volunteer> participantList = event.getParticipantList() ;
            int number= 0;
            for (Volunteer participant: participantList) {
                number++;
                System.out.println(number + " - " + participant.getUsername() + "     " + participant.getAge() + "     " + participant.getCurrentLocation());
                System.out.println("Did this user participate in your event? Yes/No");
                String confirmation;
                while (true) {
                    confirmation = scanner.nextLine();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        participant.getPastEvent().add(event);
                        System.out.println("Participation confirmed!\n");
                        break;
                    } else if (confirmation.equalsIgnoreCase("no")) {
                        System.out.println("Confirm the action: Yes/No");
                        String confirmAgain;
                        while (true) {
                            confirmAgain = scanner.nextLine();
                            if (confirmAgain.equalsIgnoreCase("yes")) {
                                System.out.println("Participation denied!\n");
                                break;
                            } else if (confirmAgain.equalsIgnoreCase("no")) {
                                System.out.println("Process terminated\n");
                                break;
                            } else {
                                System.out.println("Invalid choice, please enter again");
                            }
                        }
                        if (!confirmAgain.equalsIgnoreCase("no")) break;
                    } else {
                        System.out.println("Invalid choice, please enter again");
                    }
                }
            }
            System.out.println("Process finished");
            System.out.println("Returning to main menu...");
        }
        int completedEventId=0 ;
        for(int i = 0 ; i < DataBase.eventList.size();i++){
            if(event.equals(DataBase.eventList.get(i))){
                completedEventId = i ;
            }
            if(i > completedEventId){
                int currentID = DataBase.eventList.get(i).getId() ;
                DataBase.eventList.get(i).setId(currentID-1);
            }
        }
        for(int i = 0 ; i < event.getMainOrganizer().getYourEvent().size() ; i++){
            if(event.getMainOrganizer().getYourEvent().get(i).equals(event)){
                event.getMainOrganizer().getYourEvent().remove(i) ;
                break;
            }
        }
        event.getMainOrganizer().getPastEventCompleted().add(event);
        DataBase.eventCompleted.add(DataBase.eventList.get(completedEventId)) ;
        DataBase.eventList.remove(completedEventId) ;
    }

    public void viewEventsYouCreated(Volunteer volunteer){
        System.out.println("You are hosting " + volunteer.getYourEvent().size() + " events: ");
        int number = 0 ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        for(int i = 0 ; i < volunteer.getYourEvent().size();i++){
            Event event = volunteer.getYourEvent().get(i) ;
            number++ ;
            System.out.println(number+" - " + event.getEventName()+"     "+event.getEventDate().format(formatter)+"     "+event.getLocation()+"     "+event.getParticipantList().size()+"/"+event.getVolunteerLimit());
        }
    }

    public void viewEventsYouParticipated(Volunteer volunteer){
        System.out.println("You have been confirmed to participate in " + volunteer.getPastEvent().size()+ " events: ");
        int number = 0 ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        for(Event event:volunteer.getPastEvent()){
            number++ ;
            System.out.println(number+" - " + event.getEventName()+"     "+event.getEventDate().format(formatter)+"     "+event.getLocation()+"     "+event.getMinimumAge()+"+");
        }
    }

    public void cancelParticipation(Volunteer volunteer, Event event){
        int number = 0 ;
        for(Volunteer participant:event.getParticipantList()){
            if(participant.equals(volunteer)){
                event.getParticipantList().remove(number) ;
                System.out.println("You have cancelled your participation in this event");
                Volunteer mainOrganizer = event.getMainOrganizer() ;
                mainOrganizer.getProgramNotification().add("User " + volunteer.getUsername()+" has cancelled participation in your event " + event.getEventName());
                for(int i = 0 ; i < volunteer.getCurrentEvent().size() ; i++){
                    if(volunteer.getCurrentEvent().get(i).equals(event)){
                        volunteer.getCurrentEvent().remove(i) ;
                        break;
                    }
                }
                return;
            }
            number++ ;
        }
    }

    public void cancelAnEvent(Scanner scanner, Event event){
        System.out.println("Do you confirm to cancel this event? Yes/No/Exit");
        String choice ;
        while(true){
            choice = scanner.nextLine() ;
            if(choice.equalsIgnoreCase("exit")){
                System.out.println("Action terminated");
                return;
            }
            if(choice.equalsIgnoreCase("no")){
                System.out.println("Action has been cancelled");
                return;
            }
            if(choice.equalsIgnoreCase("yes")){
                System.out.println("Event has been cancelled");
                break;
            }
            else{
                System.out.println("Invalid choice, please enter again");
            }
        }
        for(int i = 0 ; i < event.getParticipantList().size() ; i++){
            Volunteer volunteer = event.getParticipantList().get(i) ;
            for(int j = 0 ; j < volunteer.getCurrentEvent().size() ; j++){
                if(event.equals(volunteer.getCurrentEvent().get(j))){
                    volunteer.getCurrentEvent().remove(j) ;
                    break ;
                }
            }
        }
        int cancelledEventId=0 ;
        for(int i = 0 ; i < DataBase.eventList.size();i++){
            if(event.equals(DataBase.eventList.get(i))){
                cancelledEventId = i ;
            }
            if(i > cancelledEventId){
                int currentID = DataBase.eventList.get(i).getId() ;
                DataBase.eventList.get(i).setId(currentID-1);
            }
        }
        Volunteer organizer = event.getMainOrganizer() ;
        for(int i = 0 ; i < organizer.getYourEvent().size();i++){
            Event thisEvent = organizer.getYourEvent().get(i) ;
            if(thisEvent.equals(event)){
                organizer.getYourEvent().remove(i) ;
                break;
            }
        }
        DataBase.eventCancelled.add(DataBase.eventList.get(cancelledEventId)) ;
        DataBase.eventList.remove(cancelledEventId) ;
    }

    public void viewAccountInformation(Volunteer volunteer){
        System.out.println("User: " + volunteer.getUsername());
        System.out.println("Age: " + volunteer.getAge());
        System.out.println("Current location: " + volunteer.getCurrentLocation());
        System.out.println("Contact telephone: " + volunteer.getPhoneNumber());
        System.out.println("Contact email: " + volunteer.getGmail());
        System.out.println("You have participated in " + volunteer.getPastEvent().size() + " events");
        System.out.println("You are currently participating in " + volunteer.getCurrentEvent().size() + " events");
        System.out.println("You have hosted " + volunteer.getPastEventCompleted().size() + " events");
        System.out.println("You are currently hosting "+volunteer.getYourEvent().size() + " events");

        if(volunteer.getViolation().isEmpty()){
            System.out.println("You haven't violated any guideline");
        }
        else{
            System.out.println("You have violated our guidelines " + volunteer.getViolation().size() + " times");
            System.out.println("Please note that your account can be suspended if you violate too many times");
        }
    }

    public void viewEventYouAreJoining(Volunteer volunteer){
        System.out.println("\nYou are currently participating in " + volunteer.getCurrentEvent().size() + " events: ");
        for(int i = 0 ; i < volunteer.getCurrentEvent().size();i++){
            Event event = volunteer.getCurrentEvent().get(i) ;
            utiles.printEvent(event);
        }
    }

    public void viewYourEventCompleted(Volunteer volunteer){
        System.out.println("You have hosted and completed " + volunteer.getPastEventCompleted().size()+ " events:");
        for(int i = 0 ; i < volunteer.getPastEventCompleted().size() ; i++){
            Event event = volunteer.getPastEventCompleted().get(i) ;
            utiles.printEvent(event);
        }
    }
}
