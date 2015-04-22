package UBMGenerator;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.osgi.framework.BundleContext;

import sogang.selab.model.Point;
import sogang.selab.model.State;
import sogang.selab.model.Transition;
import sogang.selab.model.Transition.TransitionBuilder;
import UBMGenerator.service.LogMonitorService;

public class LogMonitor2 implements LogMonitorService{

	private static ArrayList<Transition> transitions = new ArrayList<Transition>();
	private static HashMap<Integer, List<Transition>> userBMMap = new HashMap<Integer, List<Transition>>();
	private BundleContext bundleContext;
	private BufferedReader br;

	public LogMonitor2(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
		transitions = (ArrayList<Transition>) getAllTransition();


		try {
			br = new BufferedReader(new FileReader(new File( "/data/mylog.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Transition> getAllTransition() {

		/* 비어있는 경우에 디비에서 추출하여 반환 */
		if(transitions.size() <= 0) {
			String strLine = null;   

			try {
				while( (strLine = br.readLine()) != null) {

					StringTokenizer st = new StringTokenizer(strLine, "\t");
					int seq = Integer.valueOf(st.nextToken());
					float x = Float.parseFloat(st.nextToken());
					float y = Float.parseFloat(st.nextToken());
					String event = st.nextToken();

					State s = State.newInstance(String.valueOf(seq));
					State s2 = State.newInstance(String.valueOf(seq+1));

					Transition t = new TransitionBuilder(s, s2)
					.event(event).point(new Point(x, y)).createTransition();

					transitions.add(t);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

		return transitions;
	}

	public HashMap<Transition, Double> calculatedRatioMap() {

		List<Transition> allTransitions;
		try {
			allTransitions = getAllTransition();

			HashMap<Transition, Double> transitionRatioMap
			= new HashMap<Transition, Double>();

			int transitionSize = allTransitions.size();

			for(Transition t : allTransitions) {

				if(transitionRatioMap.containsKey(t)) {	
					double preRatio = transitionRatioMap.get(t);
					double currRatio = getCurrRatio(preRatio, transitionSize);
					transitionRatioMap.put(t, currRatio);

				} else {
					transitionRatioMap.put(t, getRatio(transitionSize));	
				}
			}

			return transitionRatioMap;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} 
		return null;


	}

	private double getRatio(int transitionSize) {
		return ((double)1 / transitionSize  * 100);
	}

	private double getCurrRatio(double preRatio, int transitionSize) {
		int incrementedCount = (int) (preRatio * transitionSize / 100 ) + 1;
		return  (double)incrementedCount / (double)transitionSize * 100;
	}

	@Override
	public List<Transition> getUserTransition(int userId) {
		
		ArrayList<Transition> userBMs = new ArrayList<Transition>();
		
		State s1 = State.newInstance("MainFrame");
		State s2 = State.newInstance("MainFrame");
		Transition t = new TransitionBuilder(s1, s2).createTransition();
		
		userBMs.add(t);
		
		return userBMs;
	}
}
