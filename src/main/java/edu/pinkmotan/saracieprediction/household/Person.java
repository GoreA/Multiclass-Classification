/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pinkmotan.saracieprediction.household;

import java.util.ArrayList;

/**
 *
 * @author agore
 */
public class Person {
  String id;
  String houseId;
  int target;
  ArrayList<Integer> parameters;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getHouseId() {
    return houseId;
  }

  public void setHouseId(String houseId) {
    this.houseId = houseId;
  }

  public int getTarget() {
    return target;
  }

  public void setTarget(int target) {
    this.target = target;
  }

  public ArrayList<Integer> getParameters() {
    return parameters;
  }

  public void setParameters(ArrayList<Integer> parameters) {
    this.parameters = parameters;
  }
  
  
}
