package rec.planner.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

import static rec.planner.model.Day.*;


/** Rappresenta un array di un numero di elementi stabilito pari al numero di unità di tempo della giornata, uguali per tutte le sue istanza*/
public class MTUArray<T> implements Iterable<T>, Serializable {





    private List<T> values = new LinkedList<>();

    public MTUArray() {
        for(int i = 0; i < MTU_NUMBER; i++)
        {
            values.add(null);
        }
    }

    private MTUArray(List<T> values) throws IllegalArgumentException {
        if(values.size()!=MTU_NUMBER)
            throw new IllegalArgumentException();
        this.values = values;
    }

    private MTUArray(T... values) throws IllegalArgumentException
    {
        if(values.length != MTU_NUMBER)
            throw new IllegalArgumentException();
        this.values = List.of(values);
    }

    /** Restituisce una giornata dato in input una lista adattando al modello delle unita di tempo */
    public static <T> MTUArray<T> ofValues(List<T> values){
        List<T> mtuValues = new ArrayList<>();

        for(int i = 0; i < MTU_NUMBER; i++){
            mtuValues.add(values.get(convertMTUtoIndex(i, values.size())));
        }

        return new MTUArray<>(mtuValues);
    }

    /** Restituisce una giornata dato in input valori */
    public static <T> MTUArray<T> ofValues(T... values){
        return ofValues(Arrays.asList(values));
    }

    /** Restituisce una giornata inserendo in input il valore da inserire negli MTU indicati successivamente. Gli altri MTU rimarranno a valore di default*/
    public static <T> MTUArray<T> inMTU(T values, int... mtu) throws ArrayIndexOutOfBoundsException{
        MTUArray<T> MTUArray = new MTUArray<>();
        MTUArray.setValuesInMTU(values, mtu);
        return MTUArray;
    }

    public T getValue(int mtu) throws IllegalArgumentException{
        if(!isMTU(mtu))
            throw new IllegalArgumentException();
        return values.get(mtu);
    }

    public void setValue(int mtu, T value) throws IllegalArgumentException{
        if(!isMTU(mtu))
            throw new IllegalArgumentException();
        values.set(mtu, value);
    }

    public T getValue(LocalTime time){
        time = time.minusHours(start.getHour());
        time = time.minusMinutes(start.getMinute());
        return getValue(getMTU(time));
    }

    public void setValue(LocalTime time, T value){
        time = time.minusHours(start.getHour());
        time = time.minusMinutes(start.getMinute());
        setValue(getMTU(time), value);
    }

    public List<Integer> getValuesEquals(T value){
        List<Integer> mtu = new LinkedList<>();
        for(int i = 0; i < MTU_NUMBER; i++)
            if(values.get(i).equals(value))
                mtu.add(i);
        return mtu;
    }

    public void setValuesInMTU(T value, int... mtu) throws ArrayIndexOutOfBoundsException
    {
        for(int e: mtu)
            values.set(e,value);

    }

    public void setValues(T... values) throws IllegalArgumentException{
        if(values.length != MTU_NUMBER)
            throw new IllegalArgumentException();
        this.values = List.of(values);
    }

    public List<T> getValues() {
        List<T> values = new ArrayList<>();
        for(int i = 0; i < MTU_NUMBER; i++)
            values.add(this.values.get(convertMTUtoIndex(i, this.values.size())));
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    @Override
    public Iterator<T> iterator() {
        return getValues().iterator();
    }

    @Override
    public String toString() {
        return "MTUArray{" +
                "values=" + getValues() +
                '}';
    }
}