package com.talismanov;

import com.talismanov.beans.Order;
import com.talismanov.beans.WorkingHours;
import com.talismanov.util.DateUtils;

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

    }

    private void performWork(String[] args) {

        if (args.length != 1) {
            System.err.println("please provide path to file.\n " +
                    "For Example: C:/work/file.txt for Windows \n" +
                    "or /home/smith/file.txt for UNIX");
            System.exit(1);
        }

        String fileName = args[0];
        String input = getInputToWorkWithFromFile(fileName);

        String[] lines = input.split("\n");
        String firstLine = lines[0];
        workingHours = getWorkingHours(firstLine);

        fillInputList(lines);
        //sort by date of registration
        Collections.sort(inputList);

        //check if outside of office hours and if is overlap with date

        inputList.stream().filter( order -> {
            LocalDateTime orderedTime = order.getOrderedTime();
            LocalTime orderLocalTime = orderedTime.toLocalTime();
            return  !isOutsideOfficeHours(order, orderLocalTime) && !isOverlapWithList(order);
        }).forEach(finalList::add);

        printResult();
    }

    private boolean isOverlapWithList(Order order) {
        List<Order> copy = new ArrayList<>(finalList);
        return copy.stream()
                .anyMatch(item -> isOverlap(order, item));
    }

    /*
        Should be like this for testing input
        2011-03-21
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

    private void fillInputList(String[] lines) {
        Order currentOrder = new Order();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (i % 2 == 1) {
                //line where employee made an order
                int emp = line.indexOf("EMP");

                String employee = line.substring(emp);
                currentOrder.setUserRegistered(employee);

                String dateWhenRegisteredAsString = line.substring(0, emp).trim();
                LocalDateTime dateTime = LocalDateTime.parse(dateWhenRegisteredAsString, formatterWithSeconds);
                currentOrder.setRegisteredAt(dateTime);

            } else {
                //line where is a date of meeting and hours of meeting
                String dateWhenMeetingStartsAsString = line.substring(0, line.length() - 2);
                currentOrder.setOrderedTime(LocalDateTime.parse(dateWhenMeetingStartsAsString, formatterWithoutSeconds));
                String hours = line.substring(line.length() - 1);
                currentOrder.setHoursOfMeeting(Integer.parseInt(hours));

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

        return DateUtils.isTwoDatesOverlap(start1, end1, start2, end2);
    }

    private String getInputToWorkWithFromFile(String fileName) {
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
            System.exit(2);
        } catch (IOException e) {
            System.err.println("something went wrong with file.\n" +
                    "Make sure you use / on both Windows and UNIX  systems. \n" +
                    "You wrote this path = " + fileName);
            System.exit(3);
        }

        return inputFromFile.toString();
    }

    private boolean isOutsideOfficeHours(Order order, LocalTime orderLocalTime) {
        return orderLocalTime.isBefore(workingHours.getFrom()) ||
                orderLocalTime.plusHours(order.getHoursOfMeeting()).isAfter(workingHours.getTo());
    }

    private WorkingHours getWorkingHours(String line) {
        String[] workingHoursArray = line.split(" ");
        String hoursFrom = workingHoursArray[0];
        String hoursTo = workingHoursArray[1];
        LocalTime workingTimeFrom = LocalTime.of(Integer.parseInt(hoursFrom.substring(0, 2)), Integer.parseInt(hoursFrom.substring(2)));
        LocalTime workingTimeTo = LocalTime.of(Integer.parseInt(hoursTo.substring(0, 2)), Integer.parseInt(hoursTo.substring(2)));

        return new WorkingHours(workingTimeFrom, workingTimeTo);
    }
}
