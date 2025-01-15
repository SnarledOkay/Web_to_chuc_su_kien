package EventVolunteerMatcher.service;

import EventVolunteerMatcher.data.DataBase;
import EventVolunteerMatcher.entities.Event;
import EventVolunteerMatcher.entities.Organizer;
import EventVolunteerMatcher.entities.Volunteer;
import EventVolunteerMatcher.utils.Utiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class EventService {
    Utiles utiles = new Utiles() ;
    public Event findEventByName(String eventName){
        for(Event event:DataBase.eventList){
            if(eventName.equals(event.getEventName())){
                return event ;
            }
        }
        return null ;
    }

    public void viewAllEvent(){
        if(DataBase.eventList.isEmpty()){
            System.out.println("There's currently no event happening");
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
            System.out.println("ID    Name    Date    Location    Category    Age Requirement    Volunteers needed");
            for(Event events:DataBase.eventList){
                ArrayList<Volunteer> participants = events.getParticipantList() ;
                System.out.println(events.getId()+". " + events.getEventName()+"     "+events.getEventDate().format(formatter)+"     "+events.getLocation()+"     "+events.getTypeOfVolunteerWork()+"     "+events.getMinimumAge()+"+"+"     "+participants.size()+"/"+events.getVolunteerLimit());
            }
        }
    }

    public Event createNewEvent(Scanner scanner, Volunteer volunteer){
        System.out.println("Enter information of new event (or choose 'stop' to escape)");
        System.out.print("Enter name of event: ");
        String eventName = scanner.nextLine() ;
        if(eventName.equalsIgnoreCase("stop")) return null ;
        LocalDate eventDate = utiles.setDateForEvent(scanner) ;
        System.out.print("Enter location of event: ");
        String location = scanner.nextLine() ;
        if(location.equalsIgnoreCase("stop")) return null ;
        int chooseCategory=1 ;
        do{
            System.out.println("Choose the category of your event: ");
            utiles.displayTypeOfVolunteer();
            DataBase.inputValidity = false ;
            while(!DataBase.inputValidity){
                try{
                    chooseCategory = Integer.parseInt(scanner.nextLine()) ;
                    DataBase.inputValidity = true ;
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid input, please enter again");
                }
            }
            if(chooseCategory < 0 || chooseCategory >= DataBase.volunteerWorkList.size()){
                System.out.println("Category has been set to 'Miscellaneous'");
            }
            else{
                System.out.println("Category has been set to " + DataBase.volunteerWorkList.get(chooseCategory));
                break ;
            }
        }
        while(true) ;

        System.out.print("Enter minimum age required: ");
        boolean retry = true ;
        int minAge = 0 , volunteerLimit = 1 ;
        while(retry){
            try {
                minAge = Integer.parseInt(scanner.nextLine()) ;
                if(minAge < 0){
                    System.out.println("Event is open to anyone above 6 years old") ;
                    minAge = 6 ;
                }
                retry = false ;
            }
            catch (NumberFormatException e) {
                System.out.println("Do you want to exit? Enter 'Y' to continue or any other key to escape");
                String choice = scanner.nextLine() ;
                if(!choice.equalsIgnoreCase("Y"))return null;
            }
        }
        retry = true ;
        System.out.print("Enter number of volunteers required: ");
        while(retry){
            try{
                volunteerLimit = Integer.parseInt(scanner.nextLine()) ;
                if(volunteerLimit < 0){
                    System.out.println("Limit will be set to 1");
                    volunteerLimit = 1 ;
                }
                retry = false ;
            }
            catch (NumberFormatException e){
                System.out.println("Do you want to exit? Enter 'Y' to continue or any other key to escape");
                String choice = scanner.nextLine() ;
                if(!choice.equalsIgnoreCase("Y")) return null ;
            }
        }
        ArrayList<Volunteer> participantList = new ArrayList<>();
        ArrayList<Volunteer> co_organizerList = new ArrayList<>();
        DataBase.maxEventName = Math.max(DataBase.maxEventName,eventName.length()) ;
        DataBase.maxEventAge = Math.max(DataBase.maxEventAge,Integer.toString(minAge).length()) ;
        DataBase.maxEventLocation = Math.max(DataBase.maxEventLocation,location.length()) ;
        DataBase.maxEventVolunteerLimit = Math.max(DataBase.maxEventVolunteerLimit,Integer.toString(volunteerLimit).length()) ;
        if(volunteer.isAdmin()) System.out.println("Event has been created");
        else System.out.println("Your request to create this event has been sent to Admin");
        return new Event(DataBase.eventList.size()+1,eventName,eventDate,location,chooseCategory,minAge,volunteer,co_organizerList,volunteerLimit,volunteer.getPhoneNumber(),volunteer.getGmail(),participantList) ;
    }

    public void searchEventByLocation(String location){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        int number = 0 ;
        for(Event event:DataBase.eventList){
            if(event.getLocation().equalsIgnoreCase("location")){
                number++ ;
                DataBase.eventSatisfyCondition.add(event) ;
            }
        }
        System.out.println("There are " + number + "events that will be organized in this city");
        for(int i = 0 ; i < number ; i++){
            Event event = DataBase.eventSatisfyCondition.get(i) ;
            System.out.println(event.getId()+". " + event.getEventName() + "     "+event.getEventDate().format(formatter)+"     "+event.getMinimumAge()+"+"+"     "+event.getParticipantList().size()+"/"+ event.getVolunteerLimit());
        }
        DataBase.eventSatisfyCondition.clear();
    }

    public void searchEventByDate(LocalDate dateEvent){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        int number = 0 ;
        for(Event event:DataBase.eventList){
            if(event.getEventDate().isAfter(dateEvent) || event.getEventDate().isEqual(dateEvent)){
                number++ ;
                DataBase.eventSatisfyCondition.add(event) ;
            }
        }
        System.out.println("There are " + number + "events organized on this day");
        for(int i = 0 ; i < number ; i++){
            Event event = DataBase.eventSatisfyCondition.get(i) ;
            System.out.println(event.getId()+". " + event.getEventName() + "     "+event.getEventDate().format(formatter)+ "     " + event.getLocation()+"     "+event.getMinimumAge()+"+"+"     "+event.getParticipantList().size()+"/"+ event.getVolunteerLimit());
        }
        DataBase.eventSatisfyCondition.clear();
    }

    public void searchEventByCategory(int option){
        int number = 0 ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        for(Event event:DataBase.eventList){
            int category = event.getTypeOfVolunteerWork() ;
            if(category==option){
                number++ ;
                DataBase.eventSatisfyCondition.add(event) ;
            }
        }
        System.out.println("There are " + number+"events categorized as " + DataBase.volunteerWorkList.get(option));
        for(int i = 0 ; i < number ; i++){
            Event event = DataBase.eventSatisfyCondition.get(i) ;
            System.out.println(event.getId()+". " + event.getEventName() + "     "+event.getEventDate().format(formatter)+ "     " + event.getLocation()+"     "+event.getMinimumAge()+"+"+"     "+event.getParticipantList().size()+"/"+ event.getVolunteerLimit());
        }
    }

    public void searchEventLocationDate(String location, LocalDate dateEvent){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        int number = 0 ;
        for(Event event:DataBase.eventList){
            LocalDate date = event.getEventDate() ;
            if(event.getLocation().equalsIgnoreCase(location) && !date.isBefore(dateEvent)){
                number++ ;
                DataBase.eventSatisfyCondition.add(event) ;
            }
        }
        System.out.println("There are " + number + " events that satisfy the condition");
        for(int i = 0 ; i < DataBase.eventSatisfyCondition.size() ; i++){
            Event event = DataBase.eventSatisfyCondition.get(i) ;
            System.out.println(event.getId()+". " + event.getEventName()+"     "+event.getLocation()+"     "+event.getEventDate().format(formatter)+"     "+DataBase.volunteerWorkList.get(event.getTypeOfVolunteerWork())+"     "+event.getMinimumAge()+"+"+"     "+event.getParticipantList().size()+"/"+event.getVolunteerLimit());
        }
        DataBase.eventSatisfyCondition.clear();
    }

    public void searchEventLocationCategory(String location, int volunteerCategory){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        int number = 0 ;
        for(Event event:DataBase.eventList){
            if(event.getLocation().equalsIgnoreCase(location) && event.getTypeOfVolunteerWork() == volunteerCategory){
                number++ ;
                DataBase.eventSatisfyCondition.add(event) ;
            }
        }
        System.out.println("There are " + number + " events that satisfy the condition");
        for(int i = 0 ; i < DataBase.eventSatisfyCondition.size() ; i++){
            Event event = DataBase.eventSatisfyCondition.get(i) ;
            System.out.println(event.getId()+". " + event.getEventName()+"     "+event.getLocation()+"     "+event.getEventDate().format(formatter)+"     "+DataBase.volunteerWorkList.get(event.getTypeOfVolunteerWork())+"     "+event.getMinimumAge()+"+"+"     "+event.getParticipantList().size()+"/"+event.getVolunteerLimit());
        }
        DataBase.eventSatisfyCondition.clear();
    }

    public void searchEventDateCategory(LocalDate dateEvent, int eventCategory){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        int number = 0 ;
        for(Event event:DataBase.eventList){
            LocalDate date = event.getEventDate() ;
            if(event.getTypeOfVolunteerWork()== eventCategory && !date.isBefore(dateEvent)){
                number++ ;
                DataBase.eventSatisfyCondition.add(event) ;
            }
        }
        System.out.println("There are " + number + " events that satisfy the condition");
        for(int i = 0 ; i < DataBase.eventSatisfyCondition.size() ; i++){
            Event event = DataBase.eventSatisfyCondition.get(i) ;
            System.out.println(event.getId()+". " + event.getEventName()+"     "+event.getLocation()+"     "+event.getEventDate().format(formatter)+"     "+DataBase.volunteerWorkList.get(event.getTypeOfVolunteerWork())+"     "+event.getMinimumAge()+"+"+"     "+event.getParticipantList().size()+"/"+event.getVolunteerLimit());
        }
        DataBase.eventSatisfyCondition.clear();
    }

    public void searchEventLocationDateCategory(String location, LocalDate dateEvent , int eventCategory){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        int number = 0 ;
        for(Event event:DataBase.eventList){
            LocalDate date = event.getEventDate() ;
            if(event.getTypeOfVolunteerWork()== eventCategory && !date.isBefore(dateEvent) && event.getLocation().equalsIgnoreCase(location)){
                number++ ;
                DataBase.eventSatisfyCondition.add(event) ;
            }
        }
        System.out.println("There are " + number + " events that satisfy the condition");
        for(int i = 0 ; i < DataBase.eventSatisfyCondition.size() ; i++){
            Event event = DataBase.eventSatisfyCondition.get(i) ;
            System.out.println(event.getId()+". " + event.getEventName()+"     "+event.getLocation()+"     "+event.getEventDate().format(formatter)+"     "+DataBase.volunteerWorkList.get(event.getTypeOfVolunteerWork())+"     "+event.getMinimumAge()+"+"+"     "+event.getParticipantList().size()+"/"+event.getVolunteerLimit());
        }
        DataBase.eventSatisfyCondition.clear();
    }
//    public Event


}
/*cao huy beo
ksdfkgl;kr sdfg;lkrs;lfgkr;k;sdfg r;lksdsfglr
 */
