package com.solvd.dice.javapractice;

import java.util.Comparator;

public class NameComparator implements Comparator<EmployeeTest> {

    @Override
    public int compare(EmployeeTest emp1, EmployeeTest emp2) {
        return emp1.name.compareTo(emp2.name);
    }
}
