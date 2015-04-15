package badsymptomchecker.services;

import java.util.List;


public interface BadSymptomService {
	List reason(Object currentBM, Object designModel);
	void compareBM(Object currentBM,Object designModel);
}
