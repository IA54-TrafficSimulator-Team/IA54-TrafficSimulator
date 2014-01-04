package fr.utbm.ia54.trafficsimulator.environment;

import org.janusproject.jaak.envinterface.endogenous.AutonomousEndogenousProcess;
import org.janusproject.jaak.envinterface.influence.Influence;
import org.janusproject.jaak.envinterface.perception.EnvironmentalObject;

public class TrafficLight extends EnvironmentalObject implements AutonomousEndogenousProcess{

	private static final long serialVersionUID = 85320305727049670L;
	
	public static final Object TRAFFICLIGHT_SEMANTIC = new Object();

	private boolean stopLight = true;

	public TrafficLight(Object semantic) {
		super(semantic);
	}
	
	public TrafficLight() {
		this(TRAFFICLIGHT_SEMANTIC);
	}
	
	public boolean getStopLight(){
		return this.stopLight;
	}

	/** Invoked to run the dynamics of the traffic light.
	  * @param currentTime is the current time in the simulation.
	  * @param simulationStepDuration is the duration of one simulation step.
	 */
	public Influence runAutonomousEndogenousProcess(float currentTime, float simulationStepDuration) {
		
		if(currentTime%5.0f==0.0f)
			this.stopLight=!this.stopLight;
		
		// There is no desire of action for the traffic light.
		return null;
	}

}
