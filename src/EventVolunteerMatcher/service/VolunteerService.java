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
    public Volunteer createNewAccount(Scanner scanner){
        System.out.println("Enter your information: ");
        System.out.println("Enter your username: ");
        String username = scanner.nextLine() ;
        while (utiles.checkUserNameExist(username)){
            System.out.println("Username already existed, please enter another one or press 'exit' to escape");
            username = scanner.nextLine() ;
            if(username.equalsIgnoreCase("exit")) return null ;
        }
        System.out.println("Enter your age (age can not be changed and will be updated automatically); ");
        int age = 6;
        boolean inputValidity = false ;
        while (!inputValidity) {
            try {
                age = Integer.parseInt(scanner.nextLine());
                inputValidity = true ;
            }
            catch (NumberFormatException e){
                System.out.println("Invalid input, please enter again");
            }
        }
        System.out.println("Enter your telephone number: ");
        String telephoneNumber = scanner.nextLine();
        while (utiles.checkTelephoneNumberExist(telephoneNumber) || !utiles.checkTelephoneNumberValidity(telephoneNumber)){
            if(!utiles.checkTelephoneNumberValidity(telephoneNumber)) System.out.println("Invalid telephone number (Expected format: Start with '0', only consists of 10 numbers 0-9)");
            else System.out.println("One user is already using this number, please check again or press 'exit' to escape");
            telephoneNumber = scanner.nextLine() ;
            if(telephoneNumber.equalsIgnoreCase("exit")) return null ;
        }
        System.out.println("Enter your current place of living: ");
        String location = scanner.nextLine() ;
        System.out.println("Enter your email address: ");
        String email = scanner.nextLine() ;
        while (utiles.checkGmailExist(email)){
            System.out.println("One user is already using this email, please check again or press 'exit' to escape");
            email = scanner.nextLine() ;
            if(email.equalsIgnoreCase("exit")) return null ;
        }
        System.out.println("Enter your password (password needs to be 8-14 characters long and contains at least 1 number): ");
        String password = scanner.nextLine() ;
        while (!utiles.checkPasswordValidity(password)){
            System.out.println("Invalid password form, please enter again (8-14 characters long, contains at least 1 number)");
            password = scanner.nextLine() ;
            if(password.equalsIgnoreCase("exit")) return null ;
        }
        System.out.println("Confirm your password: ");
        String againPassword = scanner.nextLine() ;
        while (!againPassword.equals(password)){
            System.out.println("Password doesn't match, please enter again");
            againPassword = scanner.nextLine() ;
            if(againPassword.equalsIgnoreCase("exit")) return null ;
        }
        System.out.println("Password matched");
        System.out.println("Enter your identification code (this code will be used when you want to change password and CAN NOT be changed in the future)");
        System.out.println("Requirement: 6 characters long, 3 number, 3 character in capital, NO special character");
        String identificationCode = scanner.nextLine() ;
        while (!utiles.checkIDCodeValidity(identificationCode)){
            System.out.println("Invalid ID code form, please re-enter or press exit to escape");
            identificationCode = scanner.nextLine() ;
            if(identificationCode.equalsIgnoreCase("exit")) return null ;
        }
        ArrayList<Event> yourEvent = new ArrayList<>() ;
        ArrayList<Event> pastEvent = new ArrayList<>() ;
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
        return new Volunteer(age,telephoneNumber,location,email,username,password,pastEvent,yourEvent,requestAccepted,requestRejected,pendingRequest,isAdmin,identificationCode,yourEventRejected, reasonEventRejected, yourEventAccepted, violation, justReceiveViolation, programNotification) ;
    }

    public Volunteer SignInService(Scanner scanner){
        System.out.println("Enter username: ");
        String username = scanner.nextLine() ;
        while (!utiles.checkUserNameExist(username)){
            if(utiles.checkUsernameExistPending(username)){
                System.out.println("This account hasn't been activated yet");
                return null ;
            }
            System.out.println("User doesn't exist, please check and re-enter or press 'exit' to escape: ");
            username = scanner.nextLine() ;
            if(username.equalsIgnoreCase("exit")) return null ;
        }
        System.out.println("Username exists, please enter your password");
        Volunteer volunteer = utiles.findVolunteerByUsername(username) ;
        String password = scanner.nextLine() ;
        while(!utiles.checkPasswordTrue(password,volunteer)){
            System.out.println("Wrong password, please check and enter again or press 'exit' to escape");
            password = scanner.nextLine() ;
            if(password.equalsIgnoreCase("exit")) return  null ;
        }
        return volunteer ;
    }

    public void viewEventsYouParticipated(Volunteer volunteer){
        System.out.print("You have participated " + volunteer.getPastEvent().size() + " events: ");
        for(Event event:volunteer.getPastEvent()){
            System.out.println(event.getEventName() + " - " + event.getEventDate() + " - " + event.getLocation());
        }
    }

    public void signUpForEvent(Volunteer volunteer, Scanner scanner){
        if(DataBase.eventList.isEmpty()){
            System.out.println("There's currently no event to sign up for");
            return;
        }
        System.out.println("List of events: ");
        int number = 0 ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        for(Event event: DataBase.eventList){
            number++ ;
            System.out.println(number+ ". " + event.getEventName() + "    " + event.getEventDate().format(formatter) +
                    "    " + event.getLocation() + "    "+event.getMinimumAge()+"+"+"    "+event.getParticipantList().size()+"/"+event.getVolunteerLimit());
        }
        int option=1;
        boolean checker = false ;
        do{
            System.out.print("Select event you want to participate (type 0 to escape): ");
            DataBase.inputValidity = false ;
            while (!DataBase.inputValidity){
                try{
                    option = Integer.parseInt(scanner.nextLine()) ;
                    DataBase.inputValidity = true ;
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid input, please enter again");
                }
            }
            if(option > number || option < 0){
                System.out.println("Invalid choice. Do you want to return to main menu?");
                System.out.println("Press 'y' to confirm or any other key to cancel");
                String choice = scanner.nextLine() ;
                if(choice.equalsIgnoreCase("y")){
                    System.out.println("Returning to main menu...");
                    return;
                }
                else System.out.println("Action cancelled. Please enter your choice again");
            }
            else if(option == 0){
                System.out.println("Confirm action: Press 'Yes' to return to main menu");
                String choice = scanner.nextLine() ;
                if(choice.equalsIgnoreCase("yes")) {
                    System.out.println("Returning to main menu...");
                    return;
                }
                else System.out.println("Action cancelled");
            }
            else {
                System.out.println("Do you confirm you want to participate?");
                System.out.println("Press 'Y' to confirm or any other key to cancel");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("y")) {
                    System.out.println("Your request to participate in this event has been sent to the organizer");
                    Volunteer mainOrganizer = DataBase.eventList.get(option - 1).getMainOrganizer();
                    mainOrganizer.getPendingRequest().add(new Pair<>(volunteer, DataBase.eventList.get(option - 1)));
                } else {
                    System.out.println("Action cancelled");
                }
            }
        }
        while(true) ;
    }

    public void confirmParticipation(Scanner scanner, Volunteer volunteer){
        if(volunteer.getYourEvent().isEmpty()) System.out.println("You can't use this function because you are currently hosting no event");
        else{
            System.out.println("There are " + volunteer.getPendingRequest().size() + " requests pending: ");
            for (Pair<Volunteer,Event> pair:volunteer.getPendingRequest()){
                Volunteer targetVolunteer = pair.getVolunteer() ;
                Event targetEvent = pair.getEvent() ;
                System.out.println("Requested by: " + targetVolunteer.getUsername());
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
                if(choice.equalsIgnoreCase("yes")){
                    System.out.println("You have accepted request to participate");
                    targetVolunteer.getRequestAccepted().add(targetEvent) ;
                    targetEvent.getParticipantList().add(targetVolunteer) ;
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
            DataBase.inputValidity = false ;
            while (!DataBase.inputValidity){
                try{
                    option = Integer.parseInt(scanner.nextLine()) ;
                    DataBase.inputValidity = true ;
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid input, please enter again");
                }
            }
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
                    System.out.print("Choose reason to report: ");
                    DataBase.inputValidity = false;
                    int choice = 1 ;
                    while (!DataBase.inputValidity){
                        try{
                            choice = Integer.parseInt(scanner.nextLine()) ;
                            DataBase.inputValidity = true ;
                        }
                        catch (NumberFormatException e){
                            System.out.println("Invalid input, please enter again");
                        }
                    }
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
            while (!DataBase.inputValidity){
                try{
                    option = Integer.parseInt(scanner.nextLine()) ;
                    DataBase.inputValidity = true ;
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid input, please enter again");
                }
            }
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
                    int choice = 1 ;
                    while (!DataBase.inputValidity){
                        try{
                            choice = Integer.parseInt(scanner.nextLine()) ;
                            DataBase.inputValidity = true ;
                        }
                        catch (NumberFormatException e){
                            System.out.println("Invalid input, please enter again");
                        }
                    }
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
}
