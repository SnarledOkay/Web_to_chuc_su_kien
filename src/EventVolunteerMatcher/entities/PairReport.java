package EventVolunteerMatcher.entities;

public class PairReport<Volunteer,Integer> {
    private Volunteer volunteer1st ;
    private Volunteer volunteer2nd ;
    private Integer integer ;

    public PairReport(Volunteer volunteer1st, Volunteer volunteer2nd, Integer integer) {
        this.volunteer1st = volunteer1st;
        this.volunteer2nd = volunteer2nd;
        this.integer = integer;
    }

    public Volunteer getVolunteer1st() {
        return volunteer1st;
    }

    public void setVolunteer1st(Volunteer volunteer1st) {
        this.volunteer1st = volunteer1st;
    }

    public Volunteer getVolunteer2nd() {
        return volunteer2nd;
    }

    public void setVolunteer2nd(Volunteer volunteer2nd) {
        this.volunteer2nd = volunteer2nd;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }
}
