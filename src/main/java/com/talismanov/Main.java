package com.talismanov;

import com.talismanov.beans.Order;
import com.talismanov.beans.WorkingHours;
import com.talismanov.util.FileHelper;
import com.talismanov.util.OverlapHelper;
import com.talismanov.util.WorkingHoursHelper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.talismanov.util.DateUtils.isOutsideOfficeHours;

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

        String fileName = checkArgsAndGetFileName(args);
        String input = FileHelper.getInputToWorkWithFromFile(fileName);

        String[] lines = input.split("\n");
        String firstLine = lines[0];
        workingHours = WorkingHoursHelper.getWorkingHours(firstLine);

        fillInputList(lines);
        //sort by date of registration
        Collections.sort(inputList);

        //check if outside of office hours and if is overlap with date

        inputList.stream().filter(order -> {
            LocalDateTime orderedTime = order.getOrderedTime();
            LocalTime orderLocalTime = orderedTime.toLocalTime();
            return !isOutsideOfficeHours(order, orderLocalTime, workingHours) && !OverlapHelper.isOverlapWithList(order, finalList);
        }).forEach(finalList::add);

        printResult();
    }

    private String checkArgsAndGetFileName(String[] args) {
        if (args.length != 1) {
            System.err.println("please provide path to file.\n " +
                    "For Example: C:/work/file.txt for Windows \n" +
                    "or /home/smith/file.txt for UNIX");
            System.exit(1);
        }

        return args[0];
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
            System.out.println(localTime + " " + localTime.plusHours(order.getHoursOfMeeting()) + " " + order.getUserRegistered());
        }
    }
}
