/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pinkmotan.saracieprediction.household;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author agore
 */
public class PersonHelper {

  public static List<Person> readPersons(String path) throws IOException {
    List<Person> persons = new ArrayList<>();
    CSVParser parser = new CSVParser(new FileReader(path), CSVFormat.DEFAULT.withHeader());
    for (CSVRecord record : parser) {
      Person person = new Person();
      person.setId(record.get("Id"));
      person.setHouseId(record.get("idhogar"));
      if (record.isMapped("Target")) {
        person.setTarget(Integer.parseInt(record.get("Target")));
      } else {
        person.setTarget(5);
      }

      List<Float> parameters = new ArrayList<>();
      Map<String, String> mappedRecord = record.toMap();
      if (record.isMapped("Target")) {
        mappedRecord.remove("Target");
      }

      for (Map.Entry<String, String> entry : mappedRecord.entrySet()) {
        String value = entry.getValue();
        if (!value.isEmpty()) {
          if (!value.equals(person.getId()) && !value.equals(person.getHouseId()) && !value.equals(person.getTarget())) {
            if (StringUtils.isNumeric(value)) {
              parameters.add(Float.valueOf(value));
            } else {
              switch (value.toLowerCase()) {
                case "no":
                  parameters.add(0f);
                  break;
                case "yes":
                  parameters.add(1f);
                  break;
                default:
                  parameters.add(Float.valueOf(value));
              }
            }
          }
        } else {
          parameters.add(0f);
        }
      }

      person.setParameters(parameters);
      persons.add(person);
    }

    return persons;
  }

  public static void populateResults(List<Person> persons, String path) {
    try {
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
      bw.write("Id,Target");
      bw.newLine();
      bw.flush();
      for (Person person : persons) {
        StringBuilder oneLine = new StringBuilder();
        oneLine.append(person.getId());
        oneLine.append(",");
        oneLine.append(person.getTarget());

        bw.write(oneLine.toString());
        bw.newLine();
        bw.flush();
      }
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
