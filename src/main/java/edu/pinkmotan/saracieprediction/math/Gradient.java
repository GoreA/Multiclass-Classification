/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pinkmotan.saracieprediction.math;

import edu.pinkmotan.saracieprediction.household.Person;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agore
 */
public class Gradient {

  public static double[] calculateTheta(Matrix matrix, float alpha, int iterations) {
    //initialise theta. All values should be equal to 0 as starters. The length of Theta should be equal to the number
    //of parameters from one matrixX row
    double[] theta = new double[matrix.getMatrixX()[1].length];
    double[][] theta_hist = new double[iterations][matrix.getMatrixX()[1].length];
    int m = matrix.getMatrixY().length;
//        initialise cost
    double[] cost = new double[m];
//        initialise J
    double[] J_history = new double[iterations];
    for (int i = 0; i < J_history.length; i++) {
      J_history[i] = 1000;
    }

//        here the iterations begin
    for (int i = 0; i < iterations; i++) {

//          update theta
      for (int l = 0; l < theta.length; l++) {
        double div = 0;
        for (int ll = 0; ll < m; ll++) {
          div = div + (cost[ll] - matrix.getMatrixY()[ll]) * matrix.getMatrixX()[ll][l];
        }
        theta[l] = theta[l] - (alpha / m) * div;
      }
//        calculate the cost with obtained theta
      for (int j = 0; j < m; j++) {
        double[] vectorX = matrix.getMatrixX()[j];
        cost[j] = 0;
        for (int k = 0; k < theta.length; k++) {
          cost[j] = cost[j] + theta[k] * vectorX[k];
        }
      }

      // update J after each iteration.
      double J_part = getRegularization(theta, 100);
      for (int t = 0; t < m; t++) {
        J_part = J_part + Math.pow((cost[t] - matrix.getMatrixY()[t]), 2);
      }
      // if we put 1 / (2 * m) instead of just /(2*m) JVM returns us 0. In this case the J will always be 0
      J_history[i] = J_part / (2 * m);
      theta_hist[i] = theta;
    }
    double J_min = J_history[0];
    int best_iterration = 0;
    for (int i = 1; i < J_history.length; i++) {
      if (Math.abs(J_history[i]) < Math.abs(J_min)) {
        J_min = J_history[i];
        best_iterration = i;
      }
    }
    System.out.println(best_iterration);
    return theta_hist[best_iterration];
  }

  public static double[] calculateLogisticTheta(Matrix matrix, float alpha, int iterations) {
    //initialise theta. All values should be equal to 0 as starters. The length of Theta should be equal to the number
    //of parameters from one matrixX row
    double[] theta = new double[matrix.getMatrixX()[1].length];
    double[][] theta_hist = new double[iterations][matrix.getMatrixX()[1].length];
    int m = matrix.getMatrixY().length;
//        initialise cost
    double[] cost = new double[m];
//        initialise J
    double[] J_history = new double[iterations];
    for (int i = 0; i < J_history.length; i++) {
      J_history[i] = 1000;
    }

//        here the iterations begin
    for (int i = 0; i < iterations; i++) {
//          update theta
      for (int l = 0; l < theta.length; l++) {
        double div = 0;
        for (int ll = 0; ll < m; ll++) {
          div += (cost[ll] - matrix.getMatrixY()[ll]) * matrix.getMatrixX()[ll][l];
        }
        theta[l] = theta[l] - (alpha / m) * div;
      }
//        calculate the cost with obtained theta, H of theta vector
      for (int j = 0; j < m; j++) {
        double[] vectorX = matrix.getMatrixX()[j];
        cost[j] = 0;
        for (int k = 0; k < theta.length; k++) {
          cost[j] += theta[k] * vectorX[k];
        }
        //sigmoid function
        cost[j] = sigmoid(cost[j]);
      }

      // TODO need to add regularisation.
      double J_part = getRegularization(theta, 10);
      for (int t = 0; t < m; t++) {
          J_part += matrix.getMatrixY()[t] * Math.log(cost[t]) + (1 - matrix.getMatrixY()[t]) * Math.log(1 - cost[t]);
      }

      J_history[i] = J_part / m;
      theta_hist[i] = theta;
    }
    double J_min = J_history[0];
    int best_iterration = 0;
    for (int i = 1; i < J_history.length; i++) {
      if (Math.abs(J_history[i]) < Math.abs(J_min)) {
        J_min = J_history[i];
        best_iterration = i;
      }
    }
    System.out.println(best_iterration);
    return theta_hist[best_iterration];
  }

  public static double calculateCost(double[] theta, Person p) {
    try {
      double cost = theta[0];
      for (int i = 1; i < theta.length; i++) {
        cost = cost + ((double) (p.getParameters().get(i - 1)) * theta[i]);
      }
      return cost;
    } catch (IllegalArgumentException ex) {
      Logger.getLogger(Gradient.class.getName()).log(Level.SEVERE, null, ex);
      return 0;
    }
  }

  public static double sigmoid(double cost) {
    return 1.0 / (1.0 + Math.exp(-cost));
  }

  private static double getRegularization(double[] theta, double lambda) {
    double regularization = 0;
    for (int i = 1; i < theta.length; i++) {
      regularization = regularization + Math.pow(theta[i], 2) * lambda;
    }
    return regularization;
  }
}
