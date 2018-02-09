package com.brentlamarrayjr.butils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Reflection {

    public static Object runGetter(Object object, String fieldName, boolean useIsForBoolean) throws IllegalAccessException, InvocationTargetException, NullNotSupportedException, NoSuchFieldException, NoSuchMethodException {

        if(object == null){
            throw new NullNotSupportedException();
        }

        String name = (useIsForBoolean&& isAnyType(getField(object, fieldName), boolean.class, Boolean.class)) ? String.format("is%s", Strings.capitalize(fieldName)):String.format("get%s", Strings.capitalize(fieldName));

        Method method = getMethod(object.getClass(), name);
        return method.invoke(object);
    }

    public static <T> T instanceOf(Class<T> objectClass, Class<?>[] parameterClasses, Object... values) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NullNotSupportedException {

        if(objectClass == null){ throw new NullNotSupportedException(); }
        if(parameterClasses != null){ for(Class<?> parameterClass: parameterClasses){ if(parameterClass==null){ throw new NullNotSupportedException(); } } }

        Constructor<T> constructor = (parameterClasses==null) ? getConstructor(objectClass):getConstructor(objectClass, parameterClasses);
        return constructor.newInstance(values);

    }

    public static <T> boolean isType(T object, boolean catchField, Class<?>... classes){

        if(catchField&&object instanceof Field){ return isType(((Field) object).getType(), classes); }

       return isType(object.getClass(), classes);
    }

    public static boolean isType(Class<?> clazz, Class<?>... classes){

        if(clazz==null&&classes!=null&&classes.length==0){ return true; }
        if(clazz==null&&classes==null){ return true; }

        for(Class<?> superclass: classes){

            if((clazz == null&&superclass==null)){
                return true;
            }

            if(superclass==null||clazz == null){
                return false;
            }

            if(!superclass.isAssignableFrom(clazz)){
                return false;
            }
        }

        return true;
    }

    public static <T> boolean isAnyType(T object, Class<?>... classes){
        return isAnyType(object.getClass(), classes);
    }

    public static boolean isAnyType(Class<?> clazz, Class<?>... classes){

        for(Class<?> superclass: classes){

            if((clazz == null&&superclass==null)){
                return true;
            }

            if(superclass==null||clazz == null){
                return false;
            }

            if(superclass.isAssignableFrom(clazz)){
                return true;
            }
        }


        return false;
    }

    public static Object run(Object object, String name, Class<?>[] parameterClasses, Object... values) throws Exception {

        if(object == null){
            return null;
        }

        if(parameterClasses != null){ for(Class<?> parameterClass: parameterClasses){ if(parameterClass==null){ throw new NullNotSupportedException(); } } }

        Method method = getMethod(object.getClass(), name, parameterClasses);
        return method.invoke(object, values);

    }

    public static <O, V> void runSetter(O object, String fieldName, V value) throws NullNotSupportedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(object == null){ return; }
        String name = String.format("set%s", Strings.capitalize(fieldName));
        Method method = getMethod(object.getClass(), name, value.getClass());
        method.invoke(object, (V) value);
    }

    public static boolean isAnyType(Field field, Class<?>... classes){
       return isAnyType(field.getType(), classes);
    }

    public static String getName(Field field){
        if(field == null){ return ""; }
        return field.getName();
    }

    public static String getTypeName(Field field){
        if(field == null){ return ""; }
        return field.getType().getTypeName();
    }

    public static String getTypeName(Object value){
        if(value == null){ return ""; }
        return value.getClass().getTypeName();
    }

    public static Field getField(Object object, String name, Class<?>... parameters) throws NullNotSupportedException, NoSuchFieldException {

        if(object==null){ throw new NullNotSupportedException(); }

        for(Class<?> parameter: parameters){ if(parameter==null){ throw new NullNotSupportedException(); } }

        for(Field field: getHierarchyFields(object.getClass(), Object.class)){
            if(field.getName().equals(name)){
                return field;
            }
        }
        throw new NoSuchFieldException();
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameters) throws NullNotSupportedException, NoSuchMethodException {

        if(clazz==null){ return null; }

        if(parameters!= null) {for(Class<?> parameter: parameters){ if(parameter==null){ throw new NullNotSupportedException(); } }}

        try { return (parameters == null) ? clazz.getDeclaredMethod(name):clazz.getDeclaredMethod(name, parameters); }
        catch (NoSuchMethodException e) {
            Class<?> parent = clazz.getSuperclass();
            if(parent == null){throw e;}
            return getMethod(parent, name, parameters);
        }

    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameters){

        if(clazz==null){ return null; }

        for(Class<?> parameter: parameters){ if(parameter==null){ return null; } }

        try { return clazz.getDeclaredConstructor(parameters); }
        catch (NoSuchMethodException e) {
            return null;
        }

    }

    public static List<Field> getHierarchyFields(Class<?> start, Class<?> end) {

        if(start == null){ return Collections.emptyList(); }

        List<Field> fields = new ArrayList<>();
        Class<?> parent = start.getSuperclass();

        if (parent != null && (end == null || !(parent.equals(end)))) {
            fields.addAll(getHierarchyFields(parent, end));
        }

        fields.addAll(Arrays.asList(start.getDeclaredFields()));

        return fields;
    }

    public static List<Field> getHierarchyFields(Class<?> start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyFields(start, null);
    }

    public static List<Field> getHierarchyFields(Object start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyFields(start.getClass(), null);
    }

    public static List<Field> getHierarchyFields(Object start, Class<?> end) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyFields(start.getClass(), end);
    }


    public static List<Method> getHierarchyMethods(Class<?> start, Class<?> end, boolean skipObjectClass) {

        if(start == null || (start==Object.class&&skipObjectClass)){ return Collections.emptyList(); }

        List<Method> methods = new ArrayList<>();
        Class<?> parent = start.getSuperclass();

        methods.addAll(Arrays.asList(start.getDeclaredMethods()));


        if (parent != null && (end == null || !(parent.equals(end)))) {
            methods.addAll(getHierarchyMethods(parent, end, skipObjectClass));
        }



        return methods;
    }

    public static List<Method> getHierarchyMethods(Class<?> start, boolean skipObjectClass) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyMethods(start, null,skipObjectClass);
    }

    public static List<Method> getHierarchyMethods(Object start, boolean skipObjectClass) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyMethods(start.getClass(), null, skipObjectClass);
    }

    public static List<Method> getHierarchyMethods(Object start, Class<?> end, boolean skipObjectClass) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyMethods(start.getClass(), end, skipObjectClass);
    }
    

    public static List<Constructor> getHierarchyConstructors(Class<?> start, Class<?> end, boolean skipObjectClass) {

        if(start == null || (start==Object.class&&skipObjectClass)){ return Collections.emptyList(); }

        List<Constructor> constructors = new ArrayList<>();
        Class<?> parent = start.getSuperclass();

        if (parent != null && (end == null || !(parent.equals(end)))) {
            constructors.addAll(getHierarchyConstructors(parent, end, skipObjectClass));
        }

        constructors.addAll(Arrays.asList(start.getDeclaredConstructors()));

        return constructors;
    }

    public static List<Constructor> getHierarchyConstructors(Class<?> start, boolean skipObjectClass) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyConstructors(start, null, skipObjectClass);
    }

    public static List<Constructor> getHierarchyConstructors(Object start, boolean skipObjectClass) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyConstructors(start.getClass(), null, skipObjectClass);
    }

    public static List<Constructor> getHierarchyConstructors(Object start, Class<?> end, boolean skipObjectClass) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyConstructors(start.getClass(), end,skipObjectClass);
    }

}
