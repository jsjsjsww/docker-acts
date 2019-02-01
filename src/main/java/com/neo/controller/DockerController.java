package com.neo.controller;

import com.neo.domain.CTModel;
import com.neo.domain.TestSuite;
import com.neo.service.ACTSMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/generation")
public class DockerController {
	
    @RequestMapping(value = "/ACTS", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    // ACTS 3.0 version
    public TestSuite ACTSGeneration(HttpServletRequest request) {
        String p = request.getParameter("parameters");
        String stren = request.getParameter("strength");
        String value = request.getParameter("values");
        int parameters = Integer.parseInt(p);
        int strength = Integer.parseInt(stren);
        String[] val = value.split(" ");
        int[] values = new int[val.length];
        for(int i = 0; i < values.length; i++)
            values[i] = Integer.parseInt(val[i]);
        CTModel model = new CTModel(parameters, strength, values);
        ACTSMethod.generateModelFile(model);
        long time = ACTSMethod.runACTS("ACTS/model.txt", strength);
        TestSuite ts = ACTSMethod.transferTestsuite("ACTS/result.txt");
        ts.setTime(time);
        return ts;
    }
}