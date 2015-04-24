package UBMGenerator.service;

import java.util.HashMap;
import java.util.List;

import sogang.selab.model.Transition;

public interface LogMonitorService {

	public List genCurBM(String dirPath);
	public String getDesignedModel();
}
