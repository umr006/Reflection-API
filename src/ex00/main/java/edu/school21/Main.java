package ex00.main.java.edu.school21;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import ex00.main.java.edu.school21.Classes.Car;
import ex00.main.java.edu.school21.Classes.User;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    private static Object object;

    public static void main(String[] args) {

        Car car = null;
        User user = null;
        Class clazz;

        System.out.println("Classes:");
        System.out.println("User");
        System.out.println("Car");
        System.out.println("----------------------------");
        System.out.println("Enter class name");
        System.out.println("->");
        String className = scanner.nextLine();

        try {
            if(className.equals("User")) {
                clazz = Class.forName(User.class.getName());
                object = (User)clazz.newInstance();
            } else {
                clazz = Class.forName(Car.class.getName());
                object = (Car)clazz.newInstance();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        List<String> userFields = getFieldNames(object.getClass().getDeclaredFields());
        List<String> userMethods = getMethodNames(object.getClass().getDeclaredMethods());
        System.out.println("fields:");
        userFields.forEach(f-> System.out.println("\t" + f));
        System.out.println("methods:");
        userMethods.forEach(m-> System.out.println("\t" + m));
        System.out.println("----------------------------");
        System.out.println("Let's create an object");
        creatingObject();
        changeField();

    }

    private static List<String> getMethodNames (Method[] methods) {
        return Arrays.stream(methods)
                .map(m -> m.getReturnType().getSimpleName() + " " + m.getName() + "(" + m.getReturnType().getSimpleName() + ")")
                .collect(Collectors.toList());
    }

    private static List<String> getFieldNames (Field[] fields) {
        return Arrays.stream(fields)
                .map(f -> f.getType().getSimpleName() + " " + f.getName())
                .collect(Collectors.toList());
    }

    private static void creatingObject() {
        try {
            Class<?> clazz = Class.forName(object.getClass().getName());
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                System.out.println(f.getName() + ":" + "\n" + "->");
                f.setAccessible(true);
                setValue(f);
            }
            System.out.println("Object created:" + object);

        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    private static void setValue(Field field) throws IllegalAccessException {
        switch (field.getType().getSimpleName()) {
            case "int", "Integer" -> {
                int value = scanner.nextInt();
                field.setInt(object, value);
                break;
            }
            case "String" -> {
                String value = scanner.nextLine();
                field.set(object, value);
                break;
            }
            case "double", "Double" -> {
                Double value = scanner.nextDouble();
                field.set(object, value);
                break;
            }
        }
    }

    private static void changeField() {
        try {
            System.out.println("Enter name if the field for changing:" + "\n" + "->");
            String changeField = scanner.nextLine();
            Field field = object.getClass().getDeclaredField(changeField);
            field.setAccessible(true);
            System.out.println("Enter " + field.getType().getSimpleName() + " value");
            if (field.getType().getSimpleName().equals("String")) {
                field.set(object, scanner.nextLine());
            } else if (field.getType().getSimpleName().equals("int")) {
                field.set(object, scanner.nextInt());
            } else {
                field.set(object, scanner.nextDouble());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}