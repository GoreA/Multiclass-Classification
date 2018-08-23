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

    populateTarget(trainPersons, testPersons, finalResults, 1);
    populateTarget(trainPersons, testPersons, finalResults, 2);
    populateTarget(trainPersons, testPersons, finalResults, 3);

//    the rest of persons automaticaly will get target = 4
    ArrayList<Person> finalFinal = new ArrayList<>();
    for (Person testPerson : testPersons) {
      if(!finalResults.contains(testPerson)){
        testPerson.setTarget(4);
        finalResults.add(testPerson);
      }
    }
    PersonHelper.populateResults(finalResults, "src\\main\\resources\\results.csv");
  }

  private static void populateTarget(List<Person> trainPersons, List<Person> testPersons, float f, int i) {
    Matrix m = Matrix.createMatrix(trainPersons);
    double[] theta = Gradient.calculateLogisticTheta(m, f, i);

    testPersons.forEach(p -> {
      double targetMask = Math.round(Gradient.sigmoid(Gradient.calculateCost(theta, p)));
      p.setTargetMask((int) Math.round(targetMask));
    });
  }

  private static void populateTarget(List<Person> trainPersons, List<Person> testPersons, List<Person> finalResults, int target) {

    for (Person p : trainPersons) {
      if (p.getTarget() == target) {
        p.setTargetMask(1);
      } else {
        p.setTargetMask(0);
      }
    }

    populateTarget(trainPersons, testPersons, 0.000003f, 1000);

    for (Person person : testPersons) {
      if (person.getTargetMask() == 1) {
        person.setTarget(target);
        finalResults.add(person);
      }
    }

    for (Person person : finalResults) {
      testPersons.remove(person);
    }
  }
}
