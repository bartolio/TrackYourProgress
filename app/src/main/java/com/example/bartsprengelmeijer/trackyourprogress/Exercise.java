package com.example.bartsprengelmeijer.trackyourprogress;

public class Exercise {

    private String exercise;
    private int sets;
    private int reps;
    private int weight;

    public Exercise(String exercise, int sets, int reps, int weight){
        super();
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public String getExercise() {
        return exercise;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
