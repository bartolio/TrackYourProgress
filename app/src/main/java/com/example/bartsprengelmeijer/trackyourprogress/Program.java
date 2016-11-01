package com.example.bartsprengelmeijer.trackyourprogress;

public class Program {

    private int id;
    private String name;
    private int numberOfWeeks;

    public Program(int id, String name, int numberOfWeeks) {
        this.id = id;
        this.name = name;
        this.numberOfWeeks = numberOfWeeks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfWeeks(int numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }
}
