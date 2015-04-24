package badsymptomchecker.services;

import java.util.List;


public interface BadSymptomService {
	List reason(List currentBM, String designModel);
	void compareBM(List currentBM,String designModel);
}
