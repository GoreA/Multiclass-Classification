/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pinkmotan.saracieprediction;

import edu.pinkmotan.saracieprediction.household.Person;
import edu.pinkmotan.saracieprediction.household.PersonHelper;
import edu.pinkmotan.saracieprediction.math.Gradient;
import edu.pinkmotan.saracieprediction.math.Matrix;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author agore
 */
public class Main {

  public static void main(String[] args) throws IOException {
    List<Person> trainPersons = PersonHelper.readPersons("src\\main\\resources\\train.csv");
    List<Person> testPersons = PersonHelper.readPersons("src\\main\\resources\\test.csv");
    List<Person> finalResults = new ArrayList<>();

    List<Person> persons = new ArrayList<>();
    persons.addAll(trainPersons);
    persons.addAll(testPersons);

    populateTarget(trainPersons, persons, finalResults, 1);
    populateTarget(trainPersons, persons, finalResults, 2);
    populateTarget(trainPersons, persons, finalResults, 3);
    populateTarget(trainPersons, persons, finalResults, 4);

    for(Person p : trainPersons) {
      if(finalResults.contains(p)){
        finalResults.remove(p);
      }
    }
    PersonHelper.populateResults(finalResults, "src\\main\\resources\\results.csv");
  }

  private static void populateTarget(List<Person> persons, float f, int i) {
    List<Person> personsWithTarget = persons.stream().filter(p -> !p.isWithoutTarget()).collect(Collectors.toList());
    List<Person> personsWithoutTarget = persons.stream().filter(Person::isWithoutTarget).collect(Collectors.toList());

    Matrix m = Matrix.createMatrix(personsWithTarget);
    double[] theta = Gradient.calculateLogisticTheta(m, f, i);

    personsWithoutTarget.forEach(p -> {
      double targetMask = Math.round(Gradient.sigmoid(Gradient.calculateCost(theta, p)));
      p.setTargetMask((int) Math.round(targetMask));
    });
  }

  private static void populateTarget(List<Person> trainPersons, List<Person> persons, List<Person> finalResults, int target) {

    for (Person p : trainPersons) {
      if (p.getTarget() == target) {
        p.setTargetMask(1);
      } else {
        p.setTargetMask(0);
      }
    }

    populateTarget(persons, 0.015f, 10);
    
    for (Person person : persons) {
      if (person.getTargetMask() == 1) {
        person.setTarget(target);
        finalResults.add(person);
      }
    }
    
    for(Person person : finalResults){
      persons.remove(person);
    }
  }
}
