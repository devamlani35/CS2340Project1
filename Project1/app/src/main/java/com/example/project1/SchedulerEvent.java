package com.example.project1;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SchedulerEvent implements Comparable<SchedulerEvent>{
    private int startTime;
    private int endTime;
    private String name;

    private String description;

    private ArrayList<String> days;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public SchedulerEvent(String name, String time, ArrayList<String> days, String description){
        if(name.length()==0){
            throw new IllegalArgumentException("Name String is empty.");
        }
        this.name = name;
        this.days = days;
        this.description = description;
        Pattern pattern = Pattern.compile("([0-9]*):([0-9]*)([AP])M-([0-9]*):([0-9]*)([AP])M", Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(time);
        if(match.find() && match.group(1)!=null&& match.group(2)!=null&& match.group(3)!=null&& match.group(4)!=null&& match.group(5)!=null&& match.group(6)!=null){
            int startHour = Integer.parseInt(match.group(1));
            int endHour = Integer.parseInt(match.group(4));
            int startMin = Integer.parseInt(match.group(2));
            int endMin = Integer.parseInt(match.group(5));
            if(startHour>12 || endHour >12 || startMin>=60 || endMin>=60){
                throw new IllegalArgumentException("Time is invalid");
            }
            startTime = (startHour * 100 + startMin) + (match.group(3).equals("P") || match.group(3).equals("p")?1200:0);
            endTime =  (endHour* 100 + endMin) + (match.group(6).equals("P") || match.group(6).equals("p") ?1200:0);
            if(startTime>endTime){
                throw new IllegalArgumentException("Time is invalid");
            }
        }else{
            throw new IllegalArgumentException("Time string is invalid.");
        }
    }
    public boolean checkOverlap(SchedulerEvent other){
        ArrayList<String> otherDays = other.getDays();
        ArrayList<String> overlap = new ArrayList<>();
        for(String day: otherDays){
            if(inDays(day)){
                overlap.add(day);
            }
        }
        if(overlap.size()==0){
            return false;
        }else{
            if((startTime<other.getStartTime() && endTime<other.getStartTime())||(startTime>other.getEndTime() && endTime>other.getEndTime())){
                return false;
            }
            return true;
        }
    }
    public boolean inDays(String other){
        for(String day: days){
            if(day.equals(other)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(SchedulerEvent o) {
        if(o.getStartTime()<startTime){
            return 1;
        }
        return -1;
    }
}
