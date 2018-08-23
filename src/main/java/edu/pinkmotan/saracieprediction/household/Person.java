/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pinkmotan.saracieprediction.household;

import java.util.List;

/**
 *
 * @author agore
 */
public class Person {

  String id;
  String houseId;
  int target;
  int targetMask;
  List<Float> parameters;

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

  public int getTargetMask() {
    return targetMask;
  }

  public void setTargetMask(int targetMask) {
    this.targetMask = targetMask;
  }

  public List<Float> getParameters() {
    return parameters;
  }

  public void setParameters(List<Float> parameters) {
    this.parameters = parameters;
  }

  public boolean isWithoutTarget() {
    return Math.round(this.getTarget()) == 5;
  }

  public boolean isWithoutTargetMask() {
    return Math.round(this.getTargetMask()) == 0;
  }
  
  @Override
  public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof Person) {
          if(this.getId().equals(((Person) anObject).getId()))
            return true;
        }
        return false;
    }
}
