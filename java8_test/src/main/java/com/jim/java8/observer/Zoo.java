package com.jim.java8.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 2017/7/18.
 */
public class Zoo {


    List<Animal> animals = new ArrayList<>();

    List<AnimalAddedListener> animalAddedListeners = new ArrayList<>();



    public void addAnimal(Animal animal){
        animals.add(animal);
        notifyAddedListener(animal);
    }

    public void notifyAddedListener(Animal animal){
        animalAddedListeners.forEach(s->s.updateAnimalAdded(animal));
    }

    public void registerAddedListener(AnimalAddedListener animalAddedListener){
        animalAddedListeners.add(animalAddedListener);
    }


    public static void main(String[] args) {
        Zoo zoo = new Zoo();
        zoo.registerAddedListener(s-> System.out.println("the animal added:" + s.getName()));
        Animal animal = new Animal();
        animal.setName("Monkey");

        Animal tiger = new Animal();
        tiger.setName("tiger");

        zoo.addAnimal(animal);

        zoo.addAnimal(tiger        );

    }
}
