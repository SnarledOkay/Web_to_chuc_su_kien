package EventVolunteerMatcher.utils;

import EventVolunteerMatcher.data.DataBase;
import EventVolunteerMatcher.entities.Volunteer;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Utiles {
    public boolean checkUserNameExist(String username){
        for(Volunteer volunteer:DataBase.volunteerList){
            if(username.equals(volunteer.getUsername())){
                return true ;
            }
        }
        return false ;
    }

    public boolean checkTelephoneNumberExist(String telephoneNumber){
        for (Volunteer volunteer:DataBase.volunteerList){
            if(telephoneNumber.equals(volunteer.getPhoneNumber())){
                return true ;
            }
        }
        return false ;
    }

    public boolean checkGmailExist(String gmail){
        for(Volunteer volunteer:DataBase.volunteerList){
            if(gmail.equals(volunteer.getGmail())){
                return true ;
            }
        }
        return false ;
    }

    public Volunteer findVolunteerByUsername(String username){
        for(Volunteer volunteer:DataBase.volunteerList){
            if(username.equals(volunteer.getUsername())){
                return volunteer ;
            }
        }
        return null ;
    }

    public boolean checkTelephoneNumberValidity(String telephoneNumber){
        char firstNumber = telephoneNumber.charAt(0) ;
        if(firstNumber != '0' || telephoneNumber.length() != 10) return false ;
        for(int i = 0 ; i < 10 ; i++){
            if(telephoneNumber.charAt(i) > '9' || telephoneNumber.charAt(i) < '0') return false ;
        }
        return true ;
    }

    public boolean checkPasswordTrue(String password, Volunteer volunteer){
        return password.equals(volunteer.getPassword());
    }

    public boolean checkPasswordValidity(String password){
        if(password.length() < 8 || password.length() > 14) return false ;
        for(int i = 0 ; i < password.length() ; i++){
            if(password.charAt(i) <= '9' && password.charAt(i) >= '0') return true ;
        }
        return false ;
    }

    public boolean checkIDCodeValidity(String IDcode){
        int number = 0;
        if(IDcode.length() != 6) return false ;
        for(int i = 0 ; i < 6 ; i++){
            if(IDcode.charAt(i) >= '0' && IDcode.charAt(i) <= '9') number++ ;
            else if(IDcode.charAt(i) >= 'A' && IDcode.charAt(i) <= 'Z') ;
            else return false ;
        }
        return number == 3;
    }

    public LocalDate setDateForEvent(Scanner scanner){
        System.out.println("Enter date of event (you can't stop during this process): ");
        int day=0 , month=0 , year=0 ;
        while(true){
            System.out.print("Current date: ");
            if(day == DataBase.harborVariable) System.out.print("--/");
            else System.out.print(day + "/");
            if(month == DataBase.harborVariable) System.out.print("--/");
            else System.out.print(month + "/");
            if(year == DataBase.harborVariable) System.out.print("----\n");
            else System.out.print(year+"\n");
            System.out.println("1 - Set day");
            System.out.println("2 - Set month");
            System.out.println("3 - Set year");
            int choice =1;
            DataBase.inputValidity = false;
            while(!DataBase.inputValidity){
                try{
                    choice = Integer.parseInt(scanner.nextLine()) ;
                    DataBase.inputValidity = true ;
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid input, please enter again");
                }
            }
            switch (choice){
                case 1:
                    System.out.print("Enter day (invalid date will automatically be set to the last day of the month): ");
                    DataBase.inputValidity = false ;
                    while(!DataBase.inputValidity){
                        try{
                            day = Integer.parseInt(scanner.nextLine()) ;
                            DataBase.inputValidity = true ;
                        }
                        catch (NumberFormatException e){
                            System.out.println("Invalid input, please enter again");
                        }
                    }
                    break ;
                case 2:
                    System.out.print("Enter month: ");
                    do{
                        DataBase.inputValidity = false ;
                        while(!DataBase.inputValidity){
                            try{
                                month = Integer.parseInt(scanner.nextLine()) ;
                                DataBase.inputValidity = true ;
                            }
                            catch (NumberFormatException e){
                                System.out.println("Invalid input, please enter again");
                            }
                        }
                        if(month > 12 || month < 0){
                            System.out.println("Invalid month entered, please enter again: ");
                            System.out.print("Enter month: ");
                        }
                    }
                    while (month > 12||month<0) ;
                    break ;
                case 3:
                    boolean choose ;
                    System.out.print("Enter year: ");
                    do{
                        choose = true ;
                        DataBase.inputValidity = false ;
                        while(!DataBase.inputValidity){
                            try{
                                year = Integer.parseInt(scanner.nextLine()) ;
                                DataBase.inputValidity = true ;
                            }
                            catch (NumberFormatException e){
                                System.out.println("Invalid input, please enter again");
                            }
                        }
                        if(year > DataBase.currentYear+5) System.out.println("Are you sure your event will happen this year?");
                        else if(year < DataBase.currentYear){
                            System.out.println("You can't organize an event in the past");
                            System.out.print("Enter year: ");
                            choose = false ;
                        }
                    }
                    while(!choose) ;
                    break ;
            }
            if(day>DataBase.harborVariable && month>DataBase.harborVariable && year>DataBase.harborVariable){
//                LocalDate dateEvent = LocalDate.of(year,month,day) ;
                if(month == 2){
                    if(year % 4 == 0 && year % 100 != 0){
                        if(day > 29) day = 29 ;
                    }
                    else{
                        if(day > 28) day = 28 ;
                    }
                }
                else{
                    if(month == 1||month==3||month==5||month==7||month==8||month==10||month==12){
                        if(day > 31) day = 31 ;
                    }
                    else{
                        if(day > 30) day = 30 ;
                    }
                }
                System.out.print("Date has been set to " + day+"/"+month+"/"+year + "\n");
                System.out.println("Do you want to save the current date? Y/N");
                String choosing = scanner.nextLine() ;
                if(choosing.equalsIgnoreCase("Y")) break ;
            }
        }
        System.out.println("Successful! Date of event has been set to "+day+"/"+month+"/"+year);
        return LocalDate.of(year,month,day);
    }

    public void displayCommunityGuideline(){
        System.out.println("1 - No trolling: Don't pretend to join any event");
        System.out.println("2 - No harassment: Use inclusive language");
        System.out.println("3 - Be honest: Displayed information must be correct (You can be suspended\nif you spread misinformation)");
    }

    public boolean checkUsernameExistPending(String username){
        for(Volunteer volunteer:DataBase.pendingAccountList){
            if(username.equals(volunteer.getUsername())) return true ;
        }
        return false ;
    }

    public void displayTypeOfVolunteer(){
        System.out.println("Choose the category of your event: ");
        for(int i = 1 ; i < DataBase.volunteerWorkList.size();i++){
            System.out.println(i+". " + DataBase.volunteerWorkList.get(i));
        }
        System.out.println(DataBase.volunteerWorkList.size()+". " + DataBase.volunteerWorkList.get(0));
    }

    public void viewAllUser(){
        System.out.println("List of all users:");
        int number = 0 ;
        for(Volunteer volunteer:DataBase.volunteerList){
            number++ ;
            if(!volunteer.isAdmin()){
                System.out.println(number+" - "+volunteer.getUsername()+"     "+volunteer.getAge()+"     "+volunteer.getGmail()+"     "+volunteer.getPhoneNumber());
            }
        }
    }

    public void viewAllAdmin(){
        System.out.println("List of all admins:");
//        int number = 0 ;
        for(int i =1 ; i < DataBase.adminList.size();i++){
            Volunteer admin = DataBase.adminList.get(i) ;
            System.out.println(i+" - " + admin.getUsername()+"     "+admin.getAge()+"     "+admin.getGmail()+"     "+admin.getPhoneNumber());
        }
    }

    public void viewWarning(){
        for(int i = 0 ; i < DataBase.warningToUser.size() ; i++){
            System.out.println(i + "- " + DataBase.warningToUser.get(i));
        }
    }
}
