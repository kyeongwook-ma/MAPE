package UBMGenerator.service;

import java.util.HashMap;
import java.util.List;

import sogang.selab.model.Transition;

public interface LogMonitorService {
	List<Transition> getAllTransition(); 
	Transition getUserTransition(int userId);
	HashMap<Transition, Double> calculatedRatioMap();
}
