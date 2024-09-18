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
            } else if (className.equals("Car")) {
                clazz = Class.forName(Car.class.getName());
                object = (Car)clazz.newInstance();
            } else {
                throw new ClassNotFoundException();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        getFieldNames(object.getClass().getDeclaredFields());
        getMethodNames(object.getClass().getDeclaredMethods());
        creatingObject();
        changeField();
        callsMethod();

    }

    private static void getMethodNames (Method[] methods) {
        List<String> userMethods =  Arrays.stream(methods)
                .map(m -> m.getReturnType().getSimpleName() + " " + m.getName() + "(" + m.getReturnType().getSimpleName() + ")")
                .collect(Collectors.toList());
        System.out.println("methods:");
        userMethods.forEach(m-> System.out.println("\t" + m));
    }

    private static void getFieldNames (Field[] fields) {
        List<String> userFields =  Arrays.stream(fields)
                .map(f -> f.getType().getSimpleName() + " " + f.getName())
                .collect(Collectors.toList());
        System.out.println("fields:");
        userFields.forEach(f-> System.out.println("\t" + f));
        System.out.println("----------------------------");
    }

    private static void creatingObject() {
        try {
            System.out.println("Let's create an object");
            Class<?> clazz = Class.forName(object.getClass().getName());
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                System.out.println(f.getName() + ":" + "\n" + "->");
                f.setAccessible(true);
                setValue(f);
                f.setAccessible(false);
            }
            System.out.println("Object created:" + object);
            System.out.println("----------------------------");
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
            System.out.println("Enter name of the field for changing");
            scanner.skip("\n");
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
            field.setAccessible(false);
            System.out.println("Object updated:" + object);
            System.out.println("----------------------------");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void callsMethod() {
        try {
            System.out.println("Enter name of the method for call:");
            String methodForCall = scanner.nextLine();
            Method[] methods = object.getClass().getMethods();
            Method targetMethod = null;
            for (Method m : methods) {
                if (m.toString().substring(m.toString().lastIndexOf(".") + 1).equals(methodForCall)) {
                    targetMethod = m;
                    break;
                }
            }
            if (targetMethod == null) {
                throw new NoSuchMethodException();
            }
            System.out.println("Enter" + targetMethod.getReturnType().getSimpleName() + "value" + "\n" + "->");
            int newValue = scanner.nextInt();
            targetMethod.setAccessible(true);
            System.out.println("Method returned: " + targetMethod.invoke(object, newValue));
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}