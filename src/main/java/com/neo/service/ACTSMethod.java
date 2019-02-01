package com.neo.service;

import com.neo.domain.CTModel;
import com.neo.domain.TestSuite;



import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class ACTSMethod {
  public static void generateModelFile(CTModel model){
    StringBuffer res = new StringBuffer("[System]\nName:s1\n[Parameter]\n");
    int parameter = model.getParameter();
    int strength = model.getStrength();
    int[] values = model.getValues();
    for (int i = 0; i < values.length; i++) {
      res.append("p" + (i + 1) + "(int):");
      for (int j = 0; j < values[i]; j++) {
        res.append(j);
        if (j != values[i] -1)
          res.append(",");
	  }
	  res.append("\n");
	}
	try {
	  File file = new File("./ACTS/model.txt");
	  if (!file.exists()) {
		file.getParentFile().mkdirs();
		file.createNewFile();
	  }
	  FileWriter writer = new FileWriter(file);
	  writer.write(res.toString());
	  writer.close();
	} catch (IOException e) {
	  e.printStackTrace();
	}
  }

  public static long runACTS(String modelFile, int strength){
	System.out.println(System.getProperty("user.dir"));
	String command = "java -Ddoi=" + strength + " -jar ACTS/acts_3.0.jar " + modelFile + " ACTS/result.txt";
	Runtime runtime = Runtime.getRuntime();
	long res = 0;
	try{
	  Instant start = Instant.now();
	  runtime.exec(command).waitFor();
	  Instant end = Instant.now();
	  res = Duration.between(start, end).toMillis();
	}catch (Exception e){
	  e.printStackTrace();
	}
	return res;
  }

  public static TestSuite transferTestsuite(String filePath){
	ArrayList<int[]> testcases = new ArrayList<>();
	try{
	  BufferedReader br = new BufferedReader(new FileReader(filePath));
	  br.readLine();
	  br.readLine();
	  br.readLine();
	  String s = br.readLine();
	  int parameter = Integer.parseInt(s.replaceAll("\n", "").split(": ")[1]);
	  System.out.println("parameters = " + parameter);
	  while ((s = br.readLine()) != null){
	    if(!s.equals("") && s.split(" ")[0].equals("Configuration")){
	      int[] ts = new int[parameter];
	      br.readLine();
	      for (int i = 0; i < parameter; i++){
	        s = br.readLine();
	        ts[i] = Integer.parseInt(s.split("=")[2]);
		  }
		  testcases.add(ts);
		}
	  }
	}catch (Exception e){
	  System.out.println(e.getMessage());
	}
	finally {
	  return new TestSuite(testcases, 1);
	}
  }


  public static void  main(String[] args){
    //generateModelFile(new CTModel(3, new int[]{2, 2, 2}));
	//runACTS("model.txt", 2);
	TestSuite testSuite = transferTestsuite("ACTS/result.txt");
	//System.out.println(new JSONbject(testSuite).toString());
  }
}
