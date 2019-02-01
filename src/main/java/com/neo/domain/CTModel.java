package com.neo.domain;

public class CTModel {
  private int parameter;
  private int strength;
  private int[] values;

  public CTModel(int parameter, int strength, int[] values){
    this.parameter = parameter;
    this.strength = strength;
    this.values = values;
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

}
