package ex00.main.java.edu.school21;
import java.lang.reflect.Field;
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
    public static void main(String[] args) {

        Car car = null;
        User user = null;

        System.out.println("Classes:");
        System.out.println("User");
        System.out.println("Car");
        System.out.println("----------------------------");
        System.out.println("Enter class name");
        System.out.println("->");
        String className = scanner.nextLine();
        if(className.equals("User")) {
            Object object = user;
            List<String> userFields = getFieldNames(User.class.getDeclaredFields());
            List<String> userMethods = getMethodNames(User.class.getDeclaredMethods());
            System.out.println("fields:");
            userFields.forEach(f-> System.out.println("\t" + f));
            System.out.println("methods:");
            userMethods.forEach(m-> System.out.println("\t" + m));
            System.out.println("----------------------------");
            System.out.println("Let's create an object");
            System.out.println("first name:" + "\n" + "->");
            System.out.println("last name:" + "\n" + "->");
            System.out.println("height:" + "\n" + "->");
        }


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

}