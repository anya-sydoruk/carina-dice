package com.solvd.dice.javapractice;

public class EmployeeTest implements Comparable<EmployeeTest>{

    public int id;
    public String name;
    public String surname;
    public int salary;

    public EmployeeTest(int id, String name, String surname, int salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public int compareTo(EmployeeTest anotherEmpl) {
        return this.id - anotherEmpl.id;
    }
}
