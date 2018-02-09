package com.brentlamarrayjr.butils;

public class Arrays {

    public static <T> T[] instance(T... values){
        if(values==null){ return null; }
        return java.util.Arrays.asList(values).toArray(values);
    }

    public static <T> T[] insert(T[] array, int index, T value){
        if(array == null){ return null; }
        if(array.length <= index){  return append(array, value); }

         array[index] = value;
        return array;
    }

    public static <T> T[] append(T[] array, T... values){
        if(array == null){ return null; }
        int index = array.length;
        array = java.util.Arrays.copyOf(array, array.length+values.length);
        for(int i = index, j = 0; i < array.length; i++, j++){
            array[i] = values[j];
        }

        return array;
    }

}
