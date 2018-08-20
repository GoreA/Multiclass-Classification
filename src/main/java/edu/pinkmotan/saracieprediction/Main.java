/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pinkmotan.saracieprediction;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ujmp.core.Matrix;

/**
 *
 * @author agore
 */
public class Main {

  public static void main(String[] args) {

    File file = new File(".\\src\\main\\resources\\train.csv");
    try {
      Matrix result = Matrix.Factory.importFrom().file(file).asDenseCSV();
      System.out.println("Columns: " + result.getDimensionCount());
      System.out.println("First label: " + result.getColumnLabel(0));
//      result.showGUI();

    } catch (IOException ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
