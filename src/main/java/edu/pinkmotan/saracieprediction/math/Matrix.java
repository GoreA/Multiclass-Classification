/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pinkmotan.saracieprediction.math;

import edu.pinkmotan.saracieprediction.Main;
import edu.pinkmotan.saracieprediction.household.Person;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agore
 */
public class Matrix {

  private double matrixX[][];
  private double matrixY[];

  public static Matrix EMPTY = new Matrix();

  private Matrix() {
  }

  private Matrix(double[][] matrixX, double[] matrixY) {
    this.matrixX = matrixX;
    this.matrixY = matrixY;
  }

  public double[][] getMatrixX() {
    return matrixX;
  }

  public double[] getMatrixY() {
    return matrixY;
  }

  public static Matrix createMatrix(List<Person> persons) {
    try {
      double[][] X = new double[persons.size()][persons.get(0).getParameters().size() + 1];
      double[] Y = new double[persons.size()];
      for (int i = 0; i < persons.size(); i++) {
        Person person = persons.get(i);
        for (int j = 0; j <= persons.get(0).getParameters().size(); j++) {
          if (j == 0) {
            X[i][j] = 1; // set to 1 for x0
          } else {
            X[i][j] = (double) person.getParameters().get(j-1);
          }
        }
      }
      for (int i = 0; i < persons.size(); i++) {
        Person person = persons.get(i);
        Y[i] = (double) person.getTargetMask();
      }

      return new Matrix(X, Y);
    } catch (SecurityException ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
      return Matrix.EMPTY;
    }
  }
}
