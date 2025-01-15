package EventVolunteerMatcher.entities;

public class Pair<Volunteer,Event> {
    private Volunteer volunteer ;
    private Event event ;
    private int reason ;

    public Pair(Volunteer volunteer, Event event) {
        this.volunteer = volunteer;
        this.event = event;
    }

    public Pair(Volunteer volunteer, int reason) {
        this.volunteer = volunteer;
        this.reason = reason;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}



