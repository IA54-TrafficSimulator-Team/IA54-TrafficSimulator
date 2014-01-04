package fr.utbm.ia54.trafficsimulator.environment;

import org.janusproject.jaak.envinterface.endogenous.AutonomousEndogenousProcess;
import org.janusproject.jaak.envinterface.influence.Influence;
import org.janusproject.jaak.envinterface.perception.Obstacle;

public class TrafficLight extends Obstacle implements AutonomousEndogenousProcess{

	/**
	 * 
	 */
	private static final long serialVersionUID = 85320305727049670L;
	
	private boolean stopLight = false;
	public TrafficLight() {
		
	}

	/** Invoked to run the dynamics of the traffic light.
	  * @param currentTime is the current time in the simulation.
	  * @param simulationStepDuration is the duration of one simulation step.
	 */
	public Influence runAutonomousEndogenousProcess(float currentTime, float simulationStepDuration) {
		
		if(currentTime%5==0)
			stopLight=!stopLight;
		
		// There is no desire of action for the traffic light.
		return null;
	}

}
