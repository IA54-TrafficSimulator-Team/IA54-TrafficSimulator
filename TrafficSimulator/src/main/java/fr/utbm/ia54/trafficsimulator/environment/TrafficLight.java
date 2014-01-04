package fr.utbm.ia54.trafficsimulator.environment;

import org.janusproject.jaak.envinterface.endogenous.AutonomousEndogenousProcess;
import org.janusproject.jaak.envinterface.influence.Influence;
import org.janusproject.jaak.envinterface.perception.EnvironmentalObject;

public class TrafficLight extends EnvironmentalObject implements AutonomousEndogenousProcess{

	private static final long serialVersionUID = 85320305727049670L;
	
	public static final Object TRAFFICLIGHT_SEMANTIC = new Object();

	private boolean stopLight = true;
	private int counter;
	private int startValue;

	public TrafficLight(Object semantic) {
		super(semantic);
		this.counter = 0;
	}
	
	public TrafficLight(int startValue) {
		this(TRAFFICLIGHT_SEMANTIC);
		this.startValue = startValue;
	}
	
	public boolean getStopLight(){
		return this.stopLight;
	}

	/** Invoked to run the dynamics of the traffic light.
	  * @param currentTime is the current time in the simulation.
	  * @param simulationStepDuration is the duration of one simulation step.
	 */
	public Influence runAutonomousEndogenousProcess(float currentTime, float simulationStepDuration) {
		this.counter++;
		if(this.counter%5==this.startValue)
			this.stopLight=!this.stopLight;
		
		// There is no desire of action for the traffic light.
		return null;
	}

}
