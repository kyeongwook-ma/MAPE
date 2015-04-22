package UBMGenerator.service;

import java.util.HashMap;
import java.util.List;

import sogang.selab.model.Transition;

public interface LogMonitorService {
	List<Transition> getAllTransition(); 
	List<Transition> getUserTransition(int userId);
	HashMap<Transition, Double> calculatedRatioMap();
}
