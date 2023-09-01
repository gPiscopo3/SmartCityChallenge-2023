package rec.planner.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/** Classe che gestisce le unita di tempo e le relative conversioni in tempo locale*/
public class Day {

    public static final int MTU_NUMBER = 24;
    public static final LocalTime start = LocalTime.of(5, 0);

    /** Restituisce vero se il numero inserito puo corrispondere all'unita di tempo*/
    public static boolean isMTU(int mtu){
        return mtu >= 0 && mtu < MTU_NUMBER;
    }

    /** Restituisce l'unita di tempo al tempo passato come argomento*/
    public static int getMTU(LocalTime time){
        time = time.minusHours(start.getHour());
        time = time.minusMinutes(start.getMinute());
        return (int) (((double)(time.getHour()*60 + time.getMinute()))/1440* MTU_NUMBER);
    }

    /** converte mtu in ora*/
    public static LocalTime getStartTime(int mtu) throws IllegalArgumentException {
        if(!isMTU(mtu))
            throw new IllegalArgumentException();
        return LocalTime.ofSecondOfDay(((long) ((((double)mtu)/MTU_NUMBER)*86400 + start.toSecondOfDay()))%86400);
    }

    /** Restituisce l'unita di tempo al tempo passato come argomento*/
    public static int getMTU(LocalDateTime dateTime){
        return getMTU(dateTime.toLocalTime());
    }

    /** Restituisce l'unita di tempo corrente*/
    public static int getMTU(){
        return getMTU(LocalDateTime.now());
    }

    /** Restituisce l'unita di tempo corrispondente all'ora*/
    public static int getMTU(int hour){
        return getMTU(LocalTime.of(hour,0));
    }

    /** Restituisce l'unita di tempo precendente al tempo passato come argomento*/
    public static int getLastMTU(LocalDateTime dateTime){
        if((getMTU(dateTime)) > 0)
            return getMTU(dateTime) - 1;
        else
            return MTU_NUMBER;
    }

    /** Restituisce l'unita precedente al tempo corrente*/
    public static int getLastMTU(){
        if((getMTU()) > 0)
            return getMTU() - 1;
        else
            return MTU_NUMBER;
    }

    /** Restituisce l'unita di tempo precendente corrispondente all'ora*/
    public static int getLastMTU(int hour){
        if((getMTU(hour)) > 0)
            return getMTU(hour) - 1;
        else
            return MTU_NUMBER;
    }


    /** Restituisce l'ora di inizio corrispondente all'mtu'*/
    public static int getHour(int mtu){
        return getStartTime(mtu).getHour();
    }


    /** converte in MTU un indice dato il numero di unita e l'MTU corrispondete al primo elemento*/
    public static int convertToSystemMTU(int index, int totalElements, int offset) throws IllegalArgumentException {
        if(index >= totalElements)
            throw new IllegalArgumentException();
        double fraction = ((double)index) / totalElements;
        return (int) (fraction *MTU_NUMBER  + offset) % MTU_NUMBER;
    }

    /** converte in MTU un indice dato il numero di unita e se iniziano allo stesso momento*/
    public static int convetToSystemMTU(int index, int totalElements) throws IllegalArgumentException{
        return convertToSystemMTU(index,totalElements,0);
    }

    /** converte in MTU un indice dato il numero di unita e l'orario relativo al primo elemento*/
    public static int convertToSystemMTU(int index, int totalElements, LocalTime start) throws IllegalArgumentException {
        return  convertToSystemMTU(index, totalElements, getMTU(start) - getMTU(Day.start));
    }


    /** converte in un indice dato l'MTU, in insieme di indici e l'elemento corrispondente al primo*/
    public static int convertMTUtoIndex(int mtu, int totalElements, int offset) throws IllegalArgumentException {
        if(mtu >= MTU_NUMBER)
            throw new IllegalArgumentException();
        double fraction = ((double) mtu) / MTU_NUMBER;
        return (int) (fraction *totalElements + offset) % totalElements;
    }

    /** converte in un indice dato l'MTU, in insieme di indici se iniziano allo stesso momento*/
    public static int convertMTUtoIndex(int mtu, int totalElements) throws IllegalArgumentException {
        return convertMTUtoIndex(mtu, totalElements, 0);
    }

    /** converte in un indice dato l'MTU, in insieme di indici e l'orario realtivo al primo indice*/
    public static int convertMTUtoIndex(int mtu, int totalElements, LocalTime start) throws IllegalArgumentException {
        return convertMTUtoIndex(mtu, totalElements, getMTU(Day.start) - getMTU(start));
    }


    /** Restituisce il giorno della REC*/
    public static LocalDate getDay(LocalDateTime dateTime){
        if(dateTime.toLocalTime().isBefore(start))
            return dateTime.toLocalDate().minusDays(1);
        return dateTime.toLocalDate();
    }

    /** Restituisce il giorno della REC*/
    public static LocalDate getDay(){
        return getDay(LocalDateTime.now());
    }

    /** Restituisce il prossimo giorno della REC*/
    public static LocalDate getNextDay(LocalDateTime dateTime){
        return getDay(dateTime.plusDays(1));
    }

    /** Restituisce il prossimo giorno della REC*/
    public static LocalDate getNextDay(){
        return getNextDay(LocalDateTime.now());
    }


    /** Restituisce data e ora dell'inizio della prossima giornata REC*/
    public static LocalDateTime getNextDayStart(LocalDateTime dateTime){
        LocalDate date = getNextDay(dateTime);
        return LocalDateTime.of(date, start);
    }

    /** Restituisce data e ora dell'inizio della prossima giornata REC*/
    public static LocalDateTime getNextDayStart(){
        return getNextDayStart(LocalDateTime.now());
    }

    /** Resituisce il giorno corrispondente alla passata MTU */
    public static LocalDate getLastMTUDay(LocalDateTime dateTime){
        if(getMTU(dateTime) > 0)
            return getDay(dateTime);
        else
            return getDay(dateTime).minusDays(1);
    }

    /** Resituisce il giorno corrispondente alla passata MTU */
    public static LocalDate getLastMTUDay(){
        return getLastMTUDay(LocalDateTime.now());
    }


}
