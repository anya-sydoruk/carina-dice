package com.solvd.dice.javapractice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaPractice {

    public static void main(String[] args) {
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
    }
}
