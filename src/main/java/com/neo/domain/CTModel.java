package com.neo.domain;

import java.util.ArrayList;

public class CTModel {
  private int parameter;
  private int strength;
  private int[] values;
  private ArrayList<String> constraint;

  public CTModel(int parameter, int strength, int[] values, ArrayList<String> constraint){
    this.parameter = parameter;
    this.strength = strength;
    this.values = values;
    this.constraint = constraint;
  }

  public int getParameter() {
	return parameter;
  }

  public int getStrength() {
    return strength;
  }

  public int[] getValues() {
	return values;
  }

  public ArrayList<String> getConstraint() {
    return constraint;
  }
}
