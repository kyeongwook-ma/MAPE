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
import UBMGenerator.service.LogMonitorService;

public class LogMonitor2 implements LogMonitorService {

	private static ArrayList<Transition> transitions = new ArrayList<Transition>();
	private BufferedReader br;

	public LogMonitor2() {

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

	@Override
	public List genCurBM(String dirPath) {

		ArrayList<String> logLines = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader(new File(dirPath)));

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

	@Override
	public String getDesignedModel() {

		StringBuilder sb = new StringBuilder();

		/* 비어있는 경우에 디비에서 추출하여 반환 */
		List<String> logLines = genCurBM("/data/mylog.txt");

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

			sb.append(t.toString());


		}

		return sb.toString();
	}

	
}
