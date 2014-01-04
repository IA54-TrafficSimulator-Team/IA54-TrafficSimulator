package fr.utbm.ia54.trafficsimulator.environment;

import java.util.Random;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.janusproject.jaak.envinterface.EnvironmentArea;
import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.spawner.JaakWorldSpawner;
import org.janusproject.jaak.turtle.Turtle;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.time.KernelTimeManager;

import fr.utbm.ia54.trafficsimulator.agents.Car;

public class TrafficSimulationWorldSpawner extends JaakWorldSpawner {
    private int budget;
    private Random random;
    private EnvironmentArea environmentArea;
    
    public TrafficSimulationWorldSpawner(EnvironmentArea environment) {
        super(environment);
        this.environmentArea = environment;
        this.budget = 1;
        this.random = new Random();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected boolean isSpawnable(KernelTimeManager timeManager) {
        // TODO Auto-generated method stub
        return this.budget > 0;
    }

    @Override
    protected Turtle createTurtle(KernelTimeManager arg0) {
        return new Car(new Point2i(this.random.nextInt(this.environmentArea.getWidth()),this.random.nextInt(this.environmentArea.getHeight())));
    }

    @Override
    protected void turtleSpawned(AgentAddress turtle, TurtleBody body,
            KernelTimeManager timeManager) {
        --this.budget;
    }

    @Override
    protected void turtleSpawned(Turtle turtle, TurtleBody body,
            KernelTimeManager timeManager) {
        // TODO Auto-generated method stub
        --this.budget;
    }    

}
