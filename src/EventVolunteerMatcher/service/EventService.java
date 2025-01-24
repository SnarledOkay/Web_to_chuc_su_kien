package EventVolunteerMatcher.service;

import EventVolunteerMatcher.data.DataBase;
import EventVolunteerMatcher.entities.Event;
import EventVolunteerMatcher.entities.Volunteer;
import EventVolunteerMatcher.utils.Utiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EventService {
    Utiles utiles = new Utiles() ;
    //xem danh sach tat ca su kien dg dien ra
    public void viewAllEvent(){
        if(DataBase.eventList.isEmpty()){
            System.out.println("There's currently no event happening");
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
            System.out.println("ID    Name       Date        Location        Category        Age Requirement        Volunteers needed");
            for(Event events:DataBase.eventList){
                //ArrayList<Volunteer> participants = events.getParticipantList() ;
                //System.out.println(events.getId()+". " + events.getEventName()+"     "+events.getEventDate().format(formatter)+"     "+events.getLocation()+"     "+events.getTypeOfVolunteerWork()+"     "+events.getMinimumAge()+"+"+"     "+participants.size()+"/"+events.getVolunteerLimit());
                utiles.printEvent(events);
            }
        }
    }
    //tao su kien moi
    public Event createNewEvent(Scanner scanner, Volunteer volunteer){
        System.out.println("Enter information of event (enter 'exit' to escape)");
        System.out.print("Enter name of event: ");
        String eventName = scanner.nextLine() ;
        eventName = eventName.trim() ;
        if(eventName.equalsIgnoreCase("exit")) return null ;
        LocalDate eventDate = utiles.setDateForEvent(scanner) ;
        int chooseLocation ;
        utiles.viewLocation();
        while (true){
            chooseLocation = utiles.enterInteger(scanner);
            if(chooseLocation <= 0 || chooseLocation >= DataBase.locationList.size()){
                System.out.println("Invalid choice, please enter again");
            }
            else{
                System.out.println("Location has been set to " + DataBase.locationList.get(chooseLocation));
                break;
            }
        }
        String location = DataBase.locationList.get(chooseLocation);
        utiles.displayTypeOfVolunteer();
        int chooseCategory = utiles.enterInteger(scanner);
        if(chooseCategory < 0 || chooseCategory >= DataBase.volunteerWorkList.size()){
            System.out.println("Category has been set to 'Miscellaneous'");
            chooseCategory = 0 ;
        }
        else{
            System.out.println("Category has been set to " + DataBase.volunteerWorkList.get(chooseCategory));
        }

        System.out.print("Enter minimum age required: ");
        int minAge , volunteerLimit ;
        while(true){
            minAge = utiles.enterInteger(scanner) ;
            if(minAge < 0){
                System.out.println("Event is open to anyone above 6-year-old");
                minAge = 6 ;
                break;
            }
            else if(minAge > 100){
                System.out.println("Unrealistic age requirement");
            }
            else{
                System.out.println("Age requirement has been set to " + minAge +"+");
                break;
            }
        }
        System.out.print("Enter number of volunteers required: ");
        volunteerLimit = utiles.enterInteger(scanner) ;
        if(volunteerLimit < 0){
            System.out.println("No limit has been set");
            volunteerLimit = 0 ;
        }
        ArrayList<Volunteer> participantList = new ArrayList<>();
        ArrayList<Volunteer> co_organizerList = new ArrayList<>();
        DataBase.maxEventName = Math.max(DataBase.maxEventName,eventName.length()) ;
        DataBase.maxEventAge = Math.max(DataBase.maxEventAge,Integer.toString(minAge).length()) ;
        DataBase.maxEventLocation = Math.max(DataBase.maxEventLocation,location.length()) ;
        DataBase.maxEventVolunteerLimit = Math.max(DataBase.maxEventVolunteerLimit,Integer.toString(volunteerLimit).length()) ;
        Event newEvent = new Event(DataBase.eventList.size()+1,eventName,eventDate,location,chooseCategory,minAge,volunteer,co_organizerList,volunteerLimit,volunteer.getPhoneNumber(),volunteer.getGmail(),participantList) ;
        while(true){
            System.out.println("Do you want to save event information? Yes/No/Exit");
            String choice ;
            while(true){
                choice = scanner.nextLine() ;
                if(choice.equalsIgnoreCase("yes")){
                    System.out.println("Confirmed, event request created");
                    break;
                }
                else if(choice.equalsIgnoreCase("exit")){
                    System.out.println("Event creation cancelled");
                    return null ;
                }
                else if(choice.equalsIgnoreCase("no")){
                    break;
                }
                else{
                    System.out.println("Invalid choice, please enter again");
                }
            }
            if(choice.equalsIgnoreCase("yes")) break ;
            else if(choice.equalsIgnoreCase("no")){
                while(true){
                    displayChangeEventInformation();
                    System.out.print("Choose what you want to change: ");
                    int selection = utiles.enterInteger(scanner) ;
                    if(selection == 7) break ;
                    if(selection > 7 || selection < 0){
                        System.out.println("Invalid choice, please enter again");
                    }
                    else{
                        chooseChangeEventInformation(newEvent,scanner,selection);
                    }
                }
            }
        }
        if(volunteer.isAdmin()) System.out.println("Event has been created");
        else System.out.println("Your request to create this event has been sent to Admin");
        return new Event(DataBase.eventList.size()+1,eventName,eventDate,location,chooseCategory,minAge,volunteer,co_organizerList,volunteerLimit,volunteer.getPhoneNumber(),volunteer.getGmail(),participantList) ;
    }
    //tim kiem theo dia diem
    public void searchEventByLocation(String location){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        int number = 0 ;
        for(Event event:DataBase.eventList){
            if(event.getLocation().equalsIgnoreCase(location)){
                number++ ;
                DataBase.eventSatisfyCondition.add(event) ;
            }
        }
        System.out.println("There are " + number + " events that will be organized in this city");
        for(int i = 0 ; i < number ; i++){
            Event event = DataBase.eventSatisfyCondition.get(i) ;
            System.out.println(event.getId()+". " + event.getEventName() + "     "+event.getEventDate().format(formatter)+"     "+event.getMinimumAge()+"+"+"     "+event.getParticipantList().size()+"/"+ event.getVolunteerLimit());
        }
        DataBase.eventSatisfyCondition.clear();
    }
    //tim kiem theo ngay dien ra (dien ra sau ngay nao do)
    public void searchEventByDate(LocalDate dateEvent){
        int number = 0 ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
        for(Event event:DataBase.eventList){
            if(event.getEventDate().isAfter(dateEvent) || event.getEventDate().isEqual(dateEvent)){
                number++ ;
                DataBase.eventSatisfyCondition.add(event) ;
            }
        }
        System.out.println("There are " + number + " events organized on this day");
        for(int i = 0 ; i < number ; i++){
            Event event = DataBase.eventSatisfyCondition.get(i) ;
            System.out.println(event.getId()+". " + event.getEventName() + "     "+event.getEventDate().format(formatter)+ "     " + event.getLocation()+"     "+event.getMinimumAge()+"+"+"     "+event.getParticipantList().size()+"/"+ event.getVolunteerLimit());
        }
        DataBase.eventSatisfyCondition.clear();
    }
    //tim kiem theo phan loai su kien
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
        System.out.println("There are " + number+" events categorized as " + DataBase.volunteerWorkList.get(option));
        for(int i = 0 ; i < number ; i++){
            Event event = DataBase.eventSatisfyCondition.get(i) ;
            System.out.println(event.getId()+". " + event.getEventName() + "     "+event.getEventDate().format(formatter)+ "     " + event.getLocation()+"     "+event.getMinimumAge()+"+"+"     "+event.getParticipantList().size()+"/"+ event.getVolunteerLimit());
        }
    }
    //tim kiem theo dia diem + ngay dien ra
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
    //tim kiem theo dia diem + phan loai
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
    //tim kiem theo ngay dien ra + phan loai
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
    //tim kiem theo ca 3 phan loai
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
    //sap xep tat ca su kien theo dia diem
    public void sortEventByLocation(){
        ArrayList<Event> allEvent = DataBase.eventList ;
        allEvent.sort(Comparator.comparing(Event::getLocation));
        int number = 0 ;
        for(Event event:allEvent){
            number++ ;
            System.out.print(number + " - ");
            utiles.printEvent(event);
        }
    }
    //sap xep tât ca su kien theo ngay dien ra
    public void sortEventByDate(){
        ArrayList<Event> allEvent = DataBase.eventList ;
        allEvent.sort(Comparator.comparing(Event::getEventDate));
        for(Event event:allEvent){
            utiles.printEvent(event);
        }
    }
    //sap xep tat ca su kien theo yeu cau về tuoi
    public void sortEventByAge(){
        ArrayList<Event> allEvent = DataBase.eventList ;
        allEvent.sort(Comparator.comparing(Event::getMinimumAge));
        for(Event event:allEvent){
            utiles.printEvent(event);
        }
    }
    //sap xep tat ca su kien theo so slot còn lai
    public void sortEventByAvailability(){
        ArrayList<Event> allEvent = DataBase.eventList ;
        allEvent.sort(Comparator.comparing(Event::getDifference));
        for(Event event:allEvent){
            utiles.printEvent(event);
        }
    }

    //man hinh thay doi thong tin ve su kien
    public void displayChangeEventInformation(){
        System.out.println("1 - Change event name");
        System.out.println("2 - Change date of event");
        System.out.println("3 - Change location");
        System.out.println("4 - Change age limit");
        System.out.println("5 - Change category");
        System.out.println("6 - Change number of volunteers needed");
        System.out.println("7 - Save information");
    }

    //chon thay doi thong tin ve su kien
    public void chooseChangeEventInformation(Event event, Scanner scanner, int choice){
        switch (choice){
            case 1:
                System.out.print("Enter new event name: ");
                String eventName = scanner.nextLine() ;
                event.setEventName(eventName);
                break;
            case 2:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") ;
                while(true){
                    System.out.println("Current date: "+event.getEventDate().format(formatter));
                    System.out.println("1 - Change day");
                    System.out.println("2 - Change month");
                    System.out.println("3 - Change year");
                    System.out.println("4 - Save date");
                    System.out.print("Choose your next step: ");
                    int option = utiles.enterInteger(scanner) ;
                    int day=event.getEventDate().getDayOfMonth() , month=event.getEventDate().getMonthValue(), year=event.getEventDate().getYear() ;
                    switch (option){
                        case 1:
                            System.out.print("Enter day (invalid date will be set to last or first day of month): ");
                            day = utiles.enterInteger(scanner) ;
                            if(day < 0) day = 1 ;
                            event.setEventDate(LocalDate.of(year,month,day));
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
                                        System.out.print("Enter month: ");
                                    }
                                }
                                if(month > 12 || month < 0){
                                    System.out.println("Invalid month entered, please enter again: ");
                                    System.out.print("Enter month: ");
                                }
                            }
                            while (month > 12||month<0) ;
                            event.setEventDate(LocalDate.of(year,month,day));
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
                                        System.out.print("Enter year: ");
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
                            event.setEventDate(LocalDate.of(year,month,day));
                            break ;
                        case 4:
                            if(day>DataBase.harborVariable && month>DataBase.harborVariable && year>DataBase.harborVariable){
                                if(month == 2){
                                    if(year % 4 == 0 && year % 100 != 0){
                                        if(day > 29) day = 29 ;
                                    }
                                    else{
                                        if(day > 28) day = 28 ;
                                    }
                                }
                                else {
                                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                                        if (day > 31) day = 31;
                                    } else {
                                        if (day > 30) day = 30;
                                    }
                                }
                                System.out.println("Date has been set to " + day + "/"+month+"/"+year);
                                event.setEventDate(LocalDate.of(year,month,day));
                            }
                    }
                    if(option == 4){
                        break;
                    }
                }
                break ;
            case 3:
                System.out.print("Enter new location: ");
                String location = scanner.nextLine() ;
                event.setLocation(location);
                break;
            case 4:
                System.out.print("Enter new age limit: ");
                int newAgeLimit = utiles.enterInteger(scanner) ;
                if(newAgeLimit<6){
                    newAgeLimit = 6 ;
                }
                System.out.println("Age requirement has been set to anyone above " + newAgeLimit + "+");
                event.setMinimumAge(newAgeLimit);
                break;
            case 5:
                utiles.displayTypeOfVolunteer();
                int category ;
                System.out.print("Choose new category: ");
                category = utiles.enterInteger(scanner) ;
                if(category < 0 || category >= DataBase.volunteerWorkList.size()) category = 0 ;
                System.out.println("Category has been set to " + DataBase.volunteerWorkList.get(category));
                event.setTypeOfVolunteerWork(category);
                break;
            case 6:
                System.out.print("Enter number of volunteers needed: ");
                int volunteerLimit = utiles.enterInteger(scanner) ;
                if(volunteerLimit < 0){
                    volunteerLimit = 0 ;
                    System.out.println("No limit for the number of volunteer needed");
                }
                event.setVolunteerLimit(volunteerLimit);
                break;
            case 7:
                return;
            default:
                System.out.println("Invalid choice, please enter again");
                break;
        }
    }
//    public Event
}
