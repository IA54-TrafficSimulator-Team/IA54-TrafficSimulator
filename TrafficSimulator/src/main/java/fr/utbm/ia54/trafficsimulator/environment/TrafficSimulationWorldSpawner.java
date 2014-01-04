package fr.utbm.ia54.trafficsimulator.environment;

import java.util.Random;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.janusproject.jaak.environment.model.JaakEnvironment;
import org.janusproject.jaak.spawner.JaakWorldSpawner;
import org.janusproject.jaak.turtle.Turtle;
import org.janusproject.kernel.time.KernelTimeManager;

import fr.utbm.ia54.trafficsimulator.agents.Car;

public class TrafficSimulationWorldSpawner extends JaakWorldSpawner {
    private Random random;
    private JaakEnvironment environment;
    
    private final int VEHICLE_COUNT = 2;
    
    public TrafficSimulationWorldSpawner(JaakEnvironment environment) {
        super(environment);
        this.environment = environment;
        this.random = new Random();
    }

    @Override
    protected boolean isSpawnable(KernelTimeManager timeManager) {
        return this.environment.getTurtleCount() < this.VEHICLE_COUNT;
    }

    @Override
    protected Turtle createTurtle(KernelTimeManager arg0) {
        Point2i destination;
        
        do{
            destination = new Point2i(this.random.nextInt(this.environment.getWidth()),this.random.nextInt(this.environment.getWidth()));
        }
        while(this.environment.hasObstacle(destination));
        
        return new Car(destination);
    } 

}
