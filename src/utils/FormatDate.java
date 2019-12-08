package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

public class FormatDate {
    private final GregorianCalendar calendar;
    private final Date date;
    
    private final String day, month, year;
    
    public FormatDate(Date date) {
        this.date = date;
        calendar = new GregorianCalendar();
        calendar.setTime(date);
        
        day = formatDay(calendar.get(GregorianCalendar.DAY_OF_MONTH));
        month = formatMonth(calendar.get(GregorianCalendar.MONTH)+1);
        year =  calendar.get(GregorianCalendar.YEAR)+"";
    }    
   
    public FormatDate(int day, int month, int year){
        this.day = formatDay(day);
        this.month = formatMonth(month);
        this.year = String.valueOf(year);
        calendar = new GregorianCalendar(year, month, day);
        date = Date.valueOf(this.year + "-" + this.month + "-" + this.day);
    }
    
    private String formatDay(int diaCalendario){                 
        String v_dia = diaCalendario <= 9 ? "0"+diaCalendario : ""+diaCalendario;
        return v_dia;
    }
    
    private String formatMonth(int mesCalendario){               
        String v_mes = mesCalendario <= 9 ? "0"+mesCalendario : ""+mesCalendario; 
        return v_mes;
    }    
    
    public LocalDate getLocalDate(){
        return date.toLocalDate();
    }
    
    public Date getDate(){
        return date;
    }
    
    /**
     * Get a period time, in years. Since start period until nowday in the system.
     * It could be use for example for get an Age
     * @param p_startPeriod
     * @return 
     */
    public static int getPeriod(FormatDate p_startPeriod){
        DateTimeFormatter formatt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDay = LocalDate.parse(p_startPeriod.toString(), formatt);
        LocalDate actualDay = LocalDate.now();        
        Period periodo = Period.between(birthDay, actualDay);
        return periodo.getYears();
    }
    
    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    @Override
    public String toString(){
        return day + "/" + month + "/" + year;
    }
    
    
    
}
