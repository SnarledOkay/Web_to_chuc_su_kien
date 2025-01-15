package EventVolunteerMatcher;

import EventVolunteerMatcher.data.DataBase;
import EventVolunteerMatcher.menu.Menu;
import EventVolunteerMatcher.service.AdminService;
import EventVolunteerMatcher.service.EventService;
import EventVolunteerMatcher.service.VolunteerService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in) ;
        Menu menu = new Menu() ;
        VolunteerService volunteerService = new VolunteerService() ;
        EventService eventService = new EventService() ;
        AdminService adminService = new AdminService() ;
        int choice=1 ;
        do{
            menu.displayDefaultMenu();
            System.out.print("What do you want to do? ");
            DataBase.inputValidity = false ;
            while (!DataBase.inputValidity){
                try{
                    choice = Integer.parseInt(scanner.nextLine()) ;
                    DataBase.inputValidity = true ;
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid choice, please enter again");
                }
            }
            menu.chooseDefaultMenu(choice);
        }
        while (choice==1||choice==2) ;
    }
}
