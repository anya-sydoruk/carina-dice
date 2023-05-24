package com.solvd.dice.javapractice;

import java.util.ArrayList;
import java.util.Arrays;

public class JavaPractice {

    public static void main(String[] args) {

        String expected = "1) When reconnected, devices should recover to red (Alarms) shortly.\r\n" +
                "2) Alarm condition should be reflected in EE active alarms list.\r\n" +
                "3) Alarm message should appear in device display.\r\n" +
                "4) Alarm entered entry should be in alarm history. \r\n" +
                "5) Alarm message should appear in alarm summary page.";

        ArrayList<String> expected2 = new ArrayList<>();
        String[] expected1 = expected.split("\r\n");
        for (String step : expected1) {
            step = step.replaceFirst("[0-9]\\)\\s","");
            expected2.add(step);
        }
        System.out.println(Arrays.toString(expected1));
        System.out.println(expected2);
        /*
        EmployeeTest emp1 = new EmployeeTest(25, "Anton", "Karavi", 12345);
        EmployeeTest emp2 = new EmployeeTest(13, "Lena", "Koroleva", 43553);
        EmployeeTest emp3 = new EmployeeTest(01, "Mike", "Guiness", 2233);
        List<EmployeeTest> employeeTests = new ArrayList<>();
        employeeTests.add(emp1);
        employeeTests.add(emp2);
        employeeTests.add(emp3);
        System.out.println(employeeTests);

        Collections.sort(employeeTests);
        System.out.println(employeeTests);

        Collections.sort(employeeTests, new NameComparator());
        System.out.println(employeeTests);

        Collections.sort(employeeTests, new SalaryComparator());
        System.out.println(employeeTests);

         */
    }
}
