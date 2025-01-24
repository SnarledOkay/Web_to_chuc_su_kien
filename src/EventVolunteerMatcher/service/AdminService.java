package EventVolunteerMatcher.service;

import EventVolunteerMatcher.data.DataBase;
import EventVolunteerMatcher.entities.Event;
import EventVolunteerMatcher.entities.Pair;
import EventVolunteerMatcher.entities.Volunteer;
import EventVolunteerMatcher.utils.Utiles;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class AdminService {
//    public void confirmParticipation(Scanner scanner){
//        if(DataBase.acceptParticipation.isEmpty()) System.out.println("There's currently no participation request pending");
//        else{
//            for(Pair<Volunteer, Event> pair:DataBase.acceptParticipation){
//                Volunteer pendingVolunteer = pair.getVolunteer() ;
//                Event targetEvent = pair.getEvent() ;
//                System.out.println("Volunteer: " + pendingVolunteer.getUsername());
//                System.out.println("Age: " + pendingVolunteer.getAge());
//                System.out.println("Events participated: " + pendingVolunteer.getPastEvent().size());
//                System.out.println("Event: " + targetEvent.getEventName());
//                System.out.println("Age requirement: " + targetEvent.getMinimumAge() + "+");
//                System.out.println("Description: " + targetEvent.getTypeOfVolunteerWork());
//                System.out.println("Current number of participants: " + targetEvent.getParticipantList().size() + "/" + targetEvent.getVolunteerLimit());
//                System.out.println("\nDo you confirm this request to participate? Y/N");
//                String confirmation = scanner.nextLine() ;
//                if(confirmation.equalsIgnoreCase("Y")){
//                    System.out.println("You have confirmed request to participate");
//                    pendingVolunteer.getRequestAccepted().add(targetEvent) ;
//                    targetEvent.getParticipantList().add(pendingVolunteer) ;
//                }
//                else{
//                    System.out.println("You have denied request to participate");
//                    pendingVolunteer.getRequestRejected().add(targetEvent) ;
//                }
//                System.out.println("Do you want to continue? Press 'Y' to confirm or press any key to escape");
//                confirmation = scanner.nextLine();
//                if(!confirmation.equalsIgnoreCase("Y")) break ;
//            }
//            System.out.println("All ");
//        }
//    }

    Utiles utiles = new Utiles() ;
    private VolunteerService volunteerService ;
    //chap nhan yeu cau tao tai khoan moi
    public void checkPendingAccount(Scanner scanner)    {
        if(DataBase.pendingAccountList.isEmpty()){
            System.out.println("There's no request pending");
            return;
        }
        else{
            System.out.println();
            while(!DataBase.pendingAccountList.isEmpty()){
                System.out.println("There are " + DataBase.pendingAccountList.size() + " requests of account activation left");
                Volunteer currentAccount = DataBase.pendingAccountList.get(0) ;
                System.out.println("New account requested by: " + currentAccount.getUsername());
                System.out.println("Age: " + currentAccount.getAge());
                System.out.println("Location: " + currentAccount.getCurrentLocation());
                System.out.println("Email: " + currentAccount.getGmail());
                System.out.println("\nDo you approve of activating this account?  Yes/No/Exit");
                String confirm ;
                while(true){
                    confirm = scanner.nextLine() ;
                    confirm = confirm.trim() ;
                    if(confirm.equalsIgnoreCase("exit")){
                        System.out.println("Process terminated");
//                        System.out.println("Returning to main menu...\n");
                        return ;
                    }
                    if(confirm.equalsIgnoreCase("yes")){
                        System.out.println("Account activated\n");
                        currentAccount.getProgramNotification().add("Your account has been created successfully!") ;
                        DataBase.volunteerList.add(currentAccount) ;
                        DataBase.pendingAccountList.remove(0) ;
                        break;
                    }
                    else if(confirm.equalsIgnoreCase("no")){
                        System.out.println("Account rejected\n");
                        DataBase.pendingAccountList.remove(0) ;
                        break;
                    }
                    else{
                        System.out.println("Invalid choice, please enter again: ");
                    }
                }
            }
            System.out.println("There's no more request pending");
        }
    }
    //chap nhan yeu cau tao su kien moi
    public void checkPendingEvent(Scanner scanner){
        if(DataBase.pendingEventList.isEmpty()) System.out.println("No request pending");
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
            while (!DataBase.pendingEventList.isEmpty()){
                System.out.println("There's " + DataBase.pendingEventList.size() + " requests left\n");
                Event event = DataBase.pendingEventList.get(0) ;
                System.out.println("Event requested by " + event.getMainOrganizer().getUsername()+":");
                System.out.println("Name: " + event.getEventName());
                System.out.println("Location: " + event.getLocation());
                System.out.println("Date: " + event.getEventDate().format(formatter));
                System.out.println("Work involved: " + event.getTypeOfVolunteerWork());
                System.out.println("Age requirement: " + event.getMinimumAge()+"+");
                System.out.println("Number of volunteer needed: " + event.getVolunteerLimit() + "\n") ;
                System.out.println("Do you approve of this event? Yes/No/Exit");
                String choice ;
                boolean checker = false ;
                Volunteer volunteer = event.getMainOrganizer() ;
                while(true){
                    choice = scanner.nextLine() ;
                    choice = choice.trim() ;
                    if(choice.equalsIgnoreCase("exit")){
                        checker = true ;
                        break;
                    }
                    else if(choice.equalsIgnoreCase("yes")){
                        System.out.println("Request approved\n");
                        volunteer.getYourEventAccepted().add(event) ;
                        volunteer.getRequestAccepted().add(event) ;
                        volunteer.getYourEvent().add(event) ;
                        DataBase.eventList.add(event) ;
                        DataBase.pendingEventList.remove(0) ;
                        break;
                    }
                    else if(choice.equalsIgnoreCase("no")){
                        System.out.println("Request rejected");
                        displayReasonForRejectionEvent();
                        int option=1 ;
                        do{
                            System.out.println("Add reason for rejection: ");
                            option = utiles.enterInteger(scanner);
                            if(option >= DataBase.rejectEventReason.size() || option < 1) System.out.print("Invalid choice, please choose again: ");
                        }
                        while(option < 1 && option >= DataBase.rejectEventReason.size()) ;
                        volunteer.getYourEventRejected().add(event) ;
                        volunteer.getReasonForRejection().add(option) ;
                        DataBase.pendingEventList.remove(0) ;
                        break;
                    }
                    else{
                        System.out.println("Invalid choice, please enter again");
                    }
                }
                if(checker){
                    System.out.println("Process terminated");
                    return;
                }
            }
        }
        if(DataBase.pendingEventList.isEmpty()) System.out.println("There's no more request pending");
    }
    //chon ly do tu choi 1 su kien
    public void displayReasonForRejectionEvent(){
        System.out.println("Choose why event is rejected: ");
        System.out.println("1 - Suspected of trolling");
        System.out.println("2 - Suspicious activity detected");
        System.out.println("3 - Discrimination detected");
        System.out.println("4 - Unsuitable age requirement");
        System.out.println("5 - Event deemed as dangerous/risky");
        System.out.println("6 - Spamming behaviour detected");
    }
    //bien 1 nguoi dung thanh admin
    public void assignUserAsAdmin(Scanner scanner){
        utiles.viewAllUser();
        System.out.println("Choose user to assign as admin: ");
        System.out.println("You can only assign 1 person as user once at a time");
        DataBase.inputValidity = false ;
        int userChoice = utiles.enterInteger(scanner);
        if(userChoice < 0 || userChoice > DataBase.volunteerList.size()) System.out.println("No user has been assigned as admin");
        else{
            Volunteer volunteer = DataBase.volunteerList.get(userChoice-1) ;
            volunteer.setAdmin(true);
            System.out.println("Successfully assigned user as Admin");
            System.out.println("This user will be able to use Admin functions");
            DataBase.adminList.add(volunteer) ;
        }
    }
    //gui canh cao toi nguoi dung
    public void sendWarningToUser(Scanner scanner){
        utiles.viewAllUser();
        int option=1 ;
        while(true){
            System.out.print("Choose user to send warning to (enter '0' to escape): ");
            option = utiles.enterInteger(scanner);
            if(option == 0){
                System.out.println("Returning to admin menu...");
                break;
            }
            else if(option < 0 || option >= DataBase.volunteerList.size()){
                System.out.println("User doesn't exist");
            }
            else{
                Volunteer volunteer = DataBase.volunteerList.get(option-1) ;
                if(volunteer.isAdmin()) System.out.println("You can't send a warning to another admin");
                else{
                    utiles.viewWarning();
                    System.out.print("Choose reason for warning: ");
                    int reason = utiles.enterInteger(scanner);
                    if(reason < 0 || reason >= DataBase.warningToUser.size()) reason = 0 ;
                    System.out.println("Warning sent");
                    volunteer.getViolation().add(DataBase.warningToUser.get(reason)) ;
                    volunteer.setJustReceiveWarning(true);
                    break ;
                }
            }
        }



    }
    //xem xet cac don to cao tu nguoi dung
    public void checkReportFromUser(Scanner scanner){
        if(DataBase.pendingReport.isEmpty()){
            System.out.println("There's no report from user currently");
            return;
        }
        System.out.println("Enter 'stop' at inputs to escape");
        System.out.println("Displaying reports from users: ");
        while (!DataBase.pendingReport.isEmpty()){
            Volunteer reporter = DataBase.pendingReport.get(0).getVolunteer1st() ;
            Volunteer allegedViolator = DataBase.pendingReport.get(0).getVolunteer2nd() ;
            int reason = DataBase.pendingReport.get(0).getInteger() ;
            System.out.println("Name: " + allegedViolator.getUsername());
            System.out.println("Reported by: " + reporter.getUsername());
            System.out.println("Reported for: " + DataBase.warningToUser.get(reason));
            System.out.println("\nDo you validate this report? Yes/No");
            String choice ;
            while (true){
                choice = scanner.nextLine() ;
                choice = choice.trim() ;
                if(choice.equalsIgnoreCase("yes")){
                    System.out.println("A warning has been sent to the user");
                    allegedViolator.setJustReceiveWarning(true);
                    allegedViolator.getViolation().add(DataBase.warningToUser.get(reason)) ;
                    break;
                }
                else if(choice.equalsIgnoreCase("no")){
                    System.out.println("The report has been turned down");
                    System.out.println("Do you want to send a warning to the reporter? Yes/No");
                    String option ;
                    while (true){
                        option = scanner.nextLine() ;
                        option = option.trim() ;
                        if(option.equalsIgnoreCase("yes")){
                            System.out.println("A warning has been sent to the user");
                            reporter.setJustReceiveWarning(true);
                            reporter.getViolation().add("Suspected of false report") ;
                            break;
                        }
                        else if(option.equalsIgnoreCase("no")){
                            System.out.println("No warning will be sent");
                            break;
                        }
                        else{
                            System.out.println("Invalid choice, please enter again");
                        }
                    }
                    break;
                }
                else if(choice.equalsIgnoreCase("stop")) {
                    System.out.println("Process terminated");
                    System.out.println("Returning to main menu...");
                    return;
                }
                else System.out.println("Invalid choice, please enter again");
            }
            DataBase.pendingReport.remove(0) ;
        }
        System.out.println("There's no more report from any user");
        System.out.println("Returning to main menu...");
    }
    //canh cao admin
    public void sendWarningToAdmin(Scanner scanner){
        if(DataBase.adminList.size()==1) System.out.println("There's no admin to send warning to");
        else{
            System.out.println("List of admins: ");
            for(int i = 1 ; i < DataBase.adminList.size() ; i++){
                Volunteer admin = DataBase.adminList.get(i) ;
                System.out.println(i+ ". " + admin.getUsername());
            }
            DataBase.inputValidity = false ;
            System.out.println("Choose admin you want to send warning to (enter '-1' to escape): ");
            int chooseAdmin = utiles.enterInteger(scanner) ;
            if(chooseAdmin==-1){
                System.out.println("Cancelling the action...");
                System.out.println("Returning to main menu...");
                return;
            }
            System.out.println("Select reason for warning (enter '-1' to escape): ");
            utiles.viewWarning();
            while(true){
                int chooseReason = utiles.enterInteger(scanner);
                if(chooseReason==-1){
                    System.out.println("Action cancelled");
                    System.out.println("Returning to admin menu...");
                    return;
                }
                else if(chooseReason <= 0 || chooseReason>= DataBase.warningToUser.size()){
                    System.out.println("Invalid choice, please enter again");
                }
                else{
                    System.out.println("Warning has been sent to admin");
                    Volunteer admin = DataBase.adminList.get(chooseAdmin) ;
                    admin.setJustReceiveWarning(true);
                    admin.getViolation().add(DataBase.warningToUser.get(chooseReason)) ;
                    break;
                }
                if(chooseReason >= 0 && chooseReason < DataBase.warningToUser.size()) break ;
            }
        }
    }
    //xem xet don to cao toi admin
    public void checkReportTowardAdmin(Scanner scanner){
        if(DataBase.pendingAdminReport.isEmpty()){
            System.out.println("There's no report toward Admins currently");
            return;
        }
        System.out.println("Enter 'stop' at inputs to escape");
        System.out.println("Displaying reports from users: ");
        while (!DataBase.pendingAdminReport.isEmpty()){
            Volunteer reporter = DataBase.pendingAdminReport.get(0).getVolunteer1st() ;
            Volunteer allegedViolator = DataBase.pendingReport.get(0).getVolunteer2nd() ;
            int reason = DataBase.pendingReport.get(0).getInteger() ;
            System.out.println("Name: " + allegedViolator.getUsername());
            System.out.println("Reported by: " + reporter.getUsername());
            System.out.println("Reported for: " + DataBase.warningToUser.get(reason));
            System.out.println("\nDo you validate this report? Yes/No");
            String choice ;
            while (true){
                choice = scanner.nextLine() ;
                choice = choice.trim() ;
                if(choice.equalsIgnoreCase("yes")){
                    System.out.println("A warning has been sent to the admin");
                    reporter.getProgramNotification().add("The admin you reported has received a warning");
                    allegedViolator.setJustReceiveWarning(true);
                    allegedViolator.getViolation().add(DataBase.warningToUser.get(reason)) ;
                    break;
                }
                else if(choice.equalsIgnoreCase("no")){
                    System.out.println("The report has been turned down");
                    System.out.println("Do you want to send a warning to the reporter? Enter 'Yes' to confirm");
                    String option ;
                    while (true){
                        option = scanner.nextLine() ;
                        option = option.trim() ;
                        if(option.equalsIgnoreCase("yes")){
                            System.out.println("A warning has been sent to the user");
                            reporter.setJustReceiveWarning(true);
                            reporter.getViolation().add("Suspected of false report") ;
                            break;
                        }
                        else if(option.equalsIgnoreCase("stop")){
                            System.out.println("Returning to admin menu...");
                            break;
                        }
                        else{
                            System.out.println("No warning will be sent");
                            break;
                        }
                    }
                    if(option.equalsIgnoreCase("stop")){
                        choice = "stop" ;
                        break;
                    }
                    break;
                }
                else if(choice.equalsIgnoreCase("stop")) break;
                else System.out.println("Invalid choice, please enter again");
            }
            if(choice.equalsIgnoreCase("stop")) break ;
            DataBase.pendingAdminReport.remove(0) ;
        }
        if(DataBase.pendingAdminReport.isEmpty()){
            System.out.println("There's no more report from any user");
        }
        System.out.println("Returning to main menu...");
    }
}
