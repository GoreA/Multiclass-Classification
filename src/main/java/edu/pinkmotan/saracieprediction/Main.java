/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pinkmotan.saracieprediction;

import edu.pinkmotan.saracieprediction.household.Person;
import edu.pinkmotan.saracieprediction.household.PersonHelper;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author agore
 */
public class Main {

  public static void main(String[] args) throws IOException {
    List<Person> persons = PersonHelper.readPersons("src\\main\\resources\\train.csv");
    
    PersonHelper.populateResults(persons, "src\\main\\resources\\results.csv");
  }
}
