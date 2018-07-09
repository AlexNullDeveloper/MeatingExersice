package com.talismanov;

import com.talismanov.beans.Order;
import com.talismanov.beans.WorkingHours;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    private DateTimeFormatter formatterWithSeconds = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private DateTimeFormatter formatterWithoutSeconds = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private WorkingHours workingHours;
    private List<Order> inputList = new ArrayList<>();
    private List<Order> finalList = new ArrayList<>();

    public static void main(String[] args) {
        new Main().performWork(args);
//        boolean twoDatesOverlap = new Main().isTwoDatesOverlap(LocalDateTime.of(1990, 1, 10, 6, 00, 00),
//                LocalDateTime.of(1990, 1, 10, 7, 00, 00),
//                LocalDateTime.of(1990, 1, 10, 7, 00, 00),
//                LocalDateTime.of(1990, 1, 10, 8, 00, 00));
//        System.out.println("twoDatesOverlap=" + twoDatesOverlap);

//        boolean twoDatesOverlap = new Main().isTwoDatesOverlap(LocalDateTime.of(1990, 1, 10, 6, 00, 00),
//                LocalDateTime.of(1990, 1, 10, 6, 59, 59),
//                LocalDateTime.of(1990, 1, 10, 7, 00, 00),
//                LocalDateTime.of(1990, 1, 10, 8, 00, 00));
//        System.out.println("twoDatesOverlap=" + twoDatesOverlap);


    }

    private void performWork(String[] args) {

        String fileName = args[0];
        String input = getInputToWorkWithFromFile(fileName);
        System.out.println(input);

//        String input = "0900 1730\n" +
//                "2011-03-17 10:17:06 EMP001\n" +
//                "2011-03-21 09:00 2\n" +
//                "2011-03-16 12:34:56 EMP002\n" +
//                "2011-03-21 09:00 2\n" +
//                "2011-03-16 09:28:23 EMP003\n" +
//                "2011-03-22 14:00 2\n" +
//                "2011-03-17 11:23:45 EMP004\n" +
//                "2011-03-22 16:00 1\n" +
//                "2011-03-15 17:29:12 EMP005\n" +
//                "2011-03-21 16:00 3";
//        System.out.println(input);

        System.out.println("----------->\n");
        String[] lines = input.split("\n");
        String firstLine = lines[0];
        workingHours = getWorkingHours(firstLine);
//        System.out.println(workingHours);



        fillInputList(lines, inputList);
        //sort by date of registration
        Collections.sort(inputList);

        System.out.println("after sort");
        inputList.forEach(System.out::println);


        /*
Ограничения
● Никакая часть собрания не может выходить за пределы рабочего времени. +
● Встречи могут не совпадать.
● Система подачи заявок на бронирование допускает только одно представление одновременно, поэтому время подачи гарантировано будет уникальным.
● Заказы должны обрабатываться в хронологическом порядке, в котором они были отправлены. +
● При заказе заявок на бронирование в прилагаемом входе не гарантируется.
Заметки
● Текущие требования не предусматривают предупреждения пользователей о неудачных заказах; пользователь должен подтвердить, что их бронирование было успешным.
● Хотя система, которую вы создаете, может открывать и анализировать текстовый файл для ввода,
это не является частью требований. Пока текст ввода находится в правильном формате, метод ввода зависит от разработчика.*/

        for (int i = 0; i < inputList.size(); i++) {
            Order order = inputList.get(i);
            LocalDateTime orderedTime = order.getOrderedTime();
            LocalTime orderLocalTime = orderedTime.toLocalTime();
            if (!isOutsideOfficeHours(order, orderLocalTime)) {

                System.out.println("pizdec");

                //TODO check if overlap with any from finalList


                isOverlapWithList(order, finalList);

                if (!isOverlapWithList(order, finalList)) {
                    finalList.add(order);
                }
//                for (Order finalListOrder : finalList) {
//                    LocalDateTime finalListOrderOrderedTime = finalListOrder.getOrderedTime();
//                    if (!isOverlap(order, finalListOrder)) {
//                        System.out.println("bad order " + order);
//                    } else {
////                        finalList.add(order);
//                    }
//                }
            }
        }
        printResult();

    }

    private boolean isOverlapWithList(Order order, List<Order> finalList) {
        List<Order> copy = new ArrayList<>(finalList);
        for (Order item : copy) {
            if (isOverlap(order, item)) {
                return true;
            }
        }
        return false;
    }

    /*  2011-03-21
        09:00 11:00 EMP002
        2011-03-22
        14:00 16:00 EMP003
        16:00 17:00 EMP004
        */
    private void printResult() {

        //Sort List by time of meeting to print
        finalList.sort(Comparator.comparing(Order::getOrderedTime));

        String dateToPrint = "";
        for (Order order : finalList) {
            LocalDateTime orderedTime = order.getOrderedTime();
            String newDateToPrint = orderedTime.toLocalDate().toString();

            if (!newDateToPrint.equals(dateToPrint)) {
                dateToPrint = newDateToPrint;
                System.out.println(dateToPrint);
            }
            LocalTime localTime = orderedTime.toLocalTime();
            System.out.println(localTime + " " + localTime.plusHours(order.getHoursOfMeeting()) + " " +order.getUserRegistered());
        }
    }

    private void fillInputList(String[] lines, List<Order> inputList) {
        Order currentOrder = new Order();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (i % 2 == 1) {
                //line where employee made an order
//                System.out.println(line);
                int emp = line.indexOf("EMP");

                String employee = line.substring(emp);
                currentOrder.setUserRegistered(employee);
//                String str = "1986-04-08 12:30";

                String dateWhenRegisteredAsString = line.substring(0, emp).trim();
//                System.out.println(dateWhenRegisteredAsString);
                LocalDateTime dateTime = LocalDateTime.parse(dateWhenRegisteredAsString, formatterWithSeconds);
                currentOrder.setRegisteredAt(dateTime);
//                System.out.println(employee);

            } else {
                //line where is a date of meeting and hours of meeting
                String dateWhenMeetingStartsAsString = line.substring(0, line.length() - 2);
//                System.out.println(dateWhenMeetingStartsAsString);
                currentOrder.setOrderedTime(LocalDateTime.parse(dateWhenMeetingStartsAsString, formatterWithoutSeconds));
                String hours = line.substring(line.length() - 1);
//                System.out.println(hours);
                currentOrder.setHoursOfMeeting(Integer.parseInt(hours));
//                System.out.println(currentOrder);

                inputList.add(new Order(currentOrder.getRegisteredAt(), currentOrder.getUserRegistered(), currentOrder.getOrderedTime(), currentOrder.getHoursOfMeeting()));

                currentOrder = new Order();
            }
        }
    }

    private boolean isOverlap(Order order1, Order order2) {
        LocalDateTime start1 = order1.getOrderedTime();
        LocalDateTime end1 = start1.plusHours(order1.getHoursOfMeeting());

        LocalDateTime start2 = order2.getOrderedTime();
        LocalDateTime end2 = start2.plusHours(order2.getHoursOfMeeting());

        return isTwoDatesOverlap(start1, end1, start2, end2);
    }

    private boolean isTwoDatesOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        if (end1.isBefore(start2) || start1.isAfter(end2) || start1.isEqual(end2) || end1.isEqual(start2)) {
            //they do not overlap
            return false;
        } else {
            //they overlap
            return true;
        }
    }


    private String getInputToWorkWithFromFile(String fileName) {
//        System.out.println(fileName);

        StringBuilder inputFromFile = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
            inputFromFile.append(line).append("\n");
        } catch (FileNotFoundException e) {
            System.err.println("named file does not exist,\n" +
                    " is a directory rather than a regular file,\n" +
                    " or for some other reason cannot be opened for\n" +
            "reading. Make sure you use / on both Windows and UNIX systems. \n" +
                    "You wrote this path = " + fileName);
        } catch (IOException e) {
            System.err.println("something went wrong with file.\n" +
                    "Make sure you use / on both Windows and UNIX  systems. \n" +
                    "You wrote this path = " + fileName);
        }

        return inputFromFile.toString();
    }

    private boolean isOutsideOfficeHours(Order order, LocalTime orderLocalTime) {
        return orderLocalTime.isBefore(workingHours.getFrom()) ||
                orderLocalTime.plusHours(order.getHoursOfMeeting()).isAfter(workingHours.getTo());
    }

    private WorkingHours getWorkingHours(String line) {
//        System.out.println(line);
        String[] workingHoursArray = line.split(" ");
        String hoursFrom = workingHoursArray[0];
        String hoursTo = workingHoursArray[1];
        LocalTime workingTimeFrom = LocalTime.of(Integer.parseInt(hoursFrom.substring(0, 2)), Integer.parseInt(hoursFrom.substring(2)));
//        System.out.println(workingTimeFrom.toString());
        LocalTime workingTimeTo = LocalTime.of(Integer.parseInt(hoursTo.substring(0, 2)), Integer.parseInt(hoursTo.substring(2)));
//        System.out.println(workingTimeTo.toString());

        return new WorkingHours(workingTimeFrom, workingTimeTo);
    }
}
