package io.hashimati.utils;


/**
 *
 * @author Ahmed Al Hashmi (@hashimati)
 */

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Randomizer<T> {

    private Class<T> tClass ;
    private Random rnd;
    public Randomizer(Class<T> t)
    {
        this.tClass = t;
        this.rnd = new Random();
    }

    public HashMap<String, ?> getRandomInstance() throws NoSuchFieldException, IllegalAccessException {
       HashMap<String, Object > randomObject = new HashMap<>();
       List<Field> fields  = Arrays.asList(tClass.getDeclaredFields())
               .stream().filter(f-> (!f.getName().equalsIgnoreCase("id")
                       && !f.getName().equalsIgnoreCase("datecreated")
                       && !f.getName().equalsIgnoreCase("dateupdated")))
               .collect(Collectors.toList());
       for (Field f : fields) {

           if (String.class.equals(f.getType())) {

                   randomObject.put(f.getName(), "Test"+new Random().nextInt(100));

           } else if (Date.class.equals(f.getType())){
                   randomObject.putIfAbsent(f.getName(), new Date());


           } else if (Character.TYPE.equals(f.getType())) {
                   randomObject.putIfAbsent(f.getName(), (char) rnd.nextInt(100));

           } else if (Byte.TYPE.equals(f.getType())) {

                   randomObject.putIfAbsent(f.getName(), (byte) rnd.nextInt(100));

           } else if (Short.TYPE.equals(f.getType())) {

                   randomObject.putIfAbsent(f.getName(), (short) rnd.nextInt(100));

           } else if (Integer.TYPE.equals(f.getType())) {

                   randomObject.putIfAbsent(f.getName(), rnd.nextInt(100));

           } else if (Long.TYPE.equals(f.getType().getName())) {

                   randomObject.putIfAbsent(f.getName(), rnd.nextLong());

           } else if (Boolean.TYPE.equals(f.getType())) {

               randomObject.putIfAbsent(f.getName(), rnd.nextBoolean());

           } else if (Double.TYPE.equals(f.getType())) {

                   randomObject.putIfAbsent(f.getName(), rnd.nextDouble());

           } else if (Float.TYPE.equals(f.getType())) {

               randomObject.putIfAbsent(f.getName(), rnd.nextFloat());

           }
           else if(f.getType().isEnum())
           {

                   String value = getEnumValues(f.getType())[0].name();
                   randomObject.put(f.getName(), Enum.valueOf((Class<Enum>) f.getType(),  value));

           }
           System.gc();
       }
       return randomObject;
   }
    private static <E extends Enum> E[] getEnumValues(Class<?> enumClass)
            throws NoSuchFieldException, IllegalAccessException {
        Field f = enumClass.getDeclaredField(enumClass.getDeclaredFields()[0].getName());
        f.setAccessible(true);
        Object o = f.get(null);
        return (E[]) o;
    }
}

