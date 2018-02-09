import com.brentlamarrayjr.butils.Arrays;
import com.brentlamarrayjr.butils.NullNotSupportedException;
import com.brentlamarrayjr.butils.Reflection;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Suite {

    public Suite(){}

    public static void main(String[] args) throws Exception {

        assert Object.class.isAssignableFrom(Integer.class);

        Suite suite = new Suite();
        suite.integerObjectTest();
        suite.doubleObjectTest();
        suite.floatObjectTest();
        suite.arraysTest();
        suite.reflectionTest();

    }

    public void reflectionTest() throws Exception {


        Objects objects = new Objects();

        assert Reflection.isType(null);
        assert Reflection.isType(null, null);

        assert Reflection.isType(objects, true, Objects.class);
        assert Reflection.isType(Reflection.instanceOf(Objects.class, null),true, Objects.class);
        assert Reflection.getTypeName("").equals(String.class.getTypeName());
        assert Reflection.isAnyType(objects, LocalDateTime.class, Objects.class);
        assert !Reflection.isAnyType(objects, LocalDateTime.class, LocalTime.class);
        assert Reflection.getField(objects, "integerObject").equals(objects.getClass().getDeclaredField("integerObject"));
        assert Reflection.getName(Reflection.getField(objects, "integerObject")).equals("integerObject");
        assert Reflection.getMethod(Objects.class, "getIntegerObject").equals(objects.getClass().getDeclaredMethod("getIntegerObject"));
        assert Reflection.getTypeName("").equals(String.class.getTypeName());
        assert Reflection.getTypeName(Reflection.getField(objects, "integerObject")).equals(Integer.class.getTypeName());
        assert Reflection.getHierarchyFields(Objects2.class).size() == 8;
        assert Reflection.getHierarchyFields(new Objects2()).size() == 8;
        assert Reflection.getHierarchyFields(new Objects2(), Object.class).size() == 8;
        assert Reflection.getHierarchyMethods(Objects2.class, true).size() == 16;
        assert Reflection.getHierarchyMethods(Objects2.class, Object.class, true).size() == 16;
        assert Reflection.getHierarchyMethods(new Objects2(), true).size() == 16;
        assert Reflection.getHierarchyMethods(new Objects2(), Object.class, true).size() == 16;
        assert Reflection.getHierarchyConstructors(Objects2.class, true).size() == 3;
        assert Reflection.getHierarchyConstructors(Objects2.class, Object.class, true).size() == 3;
        assert Reflection.getHierarchyConstructors(new Objects2(), true).size() == 3;
        assert Reflection.getHierarchyConstructors(new Objects2(), Object.class, true).size() == 3;





    }

    public void arraysTest(){

        String[] strings = Arrays.instance("zero");
        assert strings.length == 1;
        assert strings[0].equals("zero");

        strings = Arrays.insert(strings, 1, "1");
        assert strings.length==2;
        assert strings[1].equals("1");

        strings = Arrays.insert(strings, 1, "one");
        assert strings.length==2;
        assert strings[1].equals("one");

        strings = Arrays.append(strings, "two");
        assert strings.length == 3;
        assert strings[2].equals("two");

    }

    public void integerObjectTest() throws Exception {

        Objects objects = new Objects();

        assert Reflection.isType(objects.getIntegerObject().getClass(), Integer.class);
        assert !Reflection.isType(objects.getIntegerObject().getClass(), Long.class, Double.class, Float.class);

        assert Reflection.isType(Reflection.getField(objects, "integerObject"), true, Integer.class);
        assert !Reflection.isType(Reflection.getField(objects,"integerObject"),true, Double.class, int.class);

        assert Reflection.isAnyType(Reflection.getField(objects,"integerObject"), Integer.class, boolean.class);
        assert !Reflection.isAnyType(Reflection.getField(objects,"integerObject"), Double.class, int.class);

        try { Reflection.runSetter(objects, "integer", 1); }
        catch (NoSuchMethodException e){
            System.out.println("NoSuchMethodException caught!");
        }

        try { Reflection.runGetter(objects, "integer", true); }
        catch (NoSuchFieldException e){
            System.out.println("NoSuchFieldException caught!");
        }

        try { Reflection.runGetter(null, "integer", true); }
        catch (NullNotSupportedException e){
            System.out.println("NullNotSupportedException caught!");
        }

        Reflection.runSetter(objects, "integerObject", 1);
        assert Reflection.runGetter(objects, "integerObject", true).equals(1);

        Reflection.run(objects, "setIntegerObject", new Class[]{Integer.class}, 2);
        assert Reflection.run(objects, "getIntegerObject", null).equals(2);

    }

    public void doubleObjectTest() throws Exception {

        Objects objects = new Objects();

        assert Reflection.isType(objects.getDoubleObject().getClass(), Double.class);
        assert !Reflection.isType(objects.getDoubleObject().getClass(), Long.class, Double.class, Float.class);

        assert Reflection.isType(Reflection.getField(objects,"doubleObject"),true, Double.class);
        assert !Reflection.isType(Reflection.getField(objects,"doubleObject"),true, double.class);

        assert Reflection.isAnyType(Reflection.getField(objects,"doubleObject"), Integer.class, Double.class);
        assert !Reflection.isAnyType(Reflection.getField(objects,"doubleObject"), double.class);

        try { Reflection.runSetter(objects, "double", 1.0); }
        catch (NoSuchMethodException e){
            System.out.println("NoSuchMethodException caught!");
        }

        try { Reflection.runGetter(objects, "double", true); }
        catch (NoSuchFieldException e){
            System.out.println("NoSuchFieldException caught!");
        }

        try { Reflection.runGetter(null, "double", true); }
        catch (NullNotSupportedException e){
            System.out.println("NullNotSupportedException caught!");
        }

        Reflection.runSetter(objects, "doubleObject", 1.0);
        assert Reflection.runGetter(objects, "doubleObject", true).equals(1.0);

        Reflection.run(objects, "setDoubleObject", new Class[]{Double.class}, 2.0);
        assert Reflection.run(objects, "getDoubleObject", null).equals(2.0);

    }

    public void floatObjectTest() throws Exception {

        Objects objects = new Objects();

        assert Reflection.isType(objects.getFloatObject().getClass(), Float.class);
        assert !Reflection.isType(objects.getFloatObject().getClass(), Long.class, float.class, Float.class);

        assert Reflection.isType(Reflection.getField(objects,"floatObject"),true, Float.class);
        assert !Reflection.isType(Reflection.getField(objects,"floatObject"),true, Float.class, long.class);

        assert Reflection.isAnyType(Reflection.getField(objects,"floatObject"), Float.class, Long.class);
        assert !Reflection.isAnyType(Reflection.getField(objects,"floatObject"), long.class);

        try { Reflection.runSetter(objects, "float", 1F); }
        catch (NoSuchMethodException e){
            System.out.println("NoSuchMethodException caught!");
        }

        try { Reflection.runGetter(objects, "float", true); }
        catch (NoSuchFieldException e){
            System.out.println("NoSuchFieldException caught!");
        }

        try { Reflection.runGetter(null, "float", true); }
        catch (NullNotSupportedException e){
            System.out.println("NullNotSupportedException caught!");
        }

        Reflection.runSetter(objects, "floatObject", 1F);
        assert Reflection.runGetter(objects, "floatObject", true).equals(1F);

        Reflection.run(objects, "setFloatObject", new Class[]{Float.class}, 2F);
        assert Reflection.run(objects, "getFloatObject", null).equals(2F);

    }

}
