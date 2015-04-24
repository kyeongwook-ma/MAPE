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

import sogang.selab.model.Point;
import sogang.selab.model.State;
import sogang.selab.model.Transition;
import sogang.selab.model.Transition.TransitionBuilder;

public class LogMonitor2{

	private static ArrayList<Transition> transitions = new ArrayList<Transition>();
	private BufferedReader br;

	public LogMonitor2() {
		transitions = (ArrayList<Transition>) getAllTransition();
	}

	private List<String> getlogLines() {

		ArrayList<String> logLines = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader(new File( "/data/mylog.txt")));

			String strLine = null;   

			while( (strLine = br.readLine()) != null) {
				logLines.add(strLine);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return logLines;
	}

	public List<Transition> getAllTransition() {

		/* 비어있는 경우에 디비에서 추출하여 반환 */
		if(transitions.size() <= 0) {
			List<String> logLines = getlogLines();
			try {

				for(int i = 0; i < logLines.size(); ++i) {

					StringTokenizer st = new StringTokenizer(logLines.get(i), "\t");
					int ownId = Integer.valueOf(st.nextToken());
					String target = st.nextToken();
					float x = Float.parseFloat(st.nextToken());
					float y = Float.parseFloat(st.nextToken());
					String event = st.nextToken();

					State s, s2;
					if(i < logLines.size()-1) {
						s = State.newInstance(String.valueOf(target));
						
						String nextTarget = getNextTarget(logLines.get(i+1));
						
						s2 = State.newInstance(nextTarget);
					} else {
						s = State.newInstance(target);
						s2 = State.newInstance(null);
					}

					Transition t = new TransitionBuilder(s, s2)
					.target(target).event(event).point(new Point(x, y)).createTransition();

					transitions.add(t);

				}

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} 
		}

		return transitions;
	}

	private String getNextTarget(String nextLine) {		
		StringTokenizer st2 = new StringTokenizer(nextLine, "\t");
		System.out.println(nextLine);
		for(int i = 0; i < 1; ++i) {
			st2.nextToken();
		}
		
		String nextTarget = st2.nextToken();
		return nextTarget;
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

	public List<Transition> getUserTransition(int userId) {

		List<Transition> allTransitions = getAllTransition();
		ArrayList<Transition> userTransitions = new ArrayList<Transition>();
		
		for(Transition t : allTransitions) {
			int id = t.getOwnUserId();
			
			if(id == userId) {
				userTransitions.add(t);
			}
		}
		
		return userTransitions;
	}
}
