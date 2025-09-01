package ticket.booking.entities;

import java.sql.Time;

public class Train {
    private String trainID;
    private String trainNo;
    private List<List<Integer>> seats;
    private Map<String,Time>stationTime;
    private List<String>stations;

}
