package fr.utbm.ia54.trafficsimulator.environment;

import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.spawner.JaakPointSpawner;
import org.janusproject.jaak.turtle.Turtle;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.time.KernelTimeManager;

public class TrafficSimulationSpawner extends JaakPointSpawner {

    int budget;
    
    public TrafficSimulationSpawner(int x, int y) {
        super(x, y);
        this.budget = 10;
    }

    @Override
    protected float computeSpawnedTurtleOrientation(KernelTimeManager arg0) {
        return 0;
    }

    @Override
    protected Turtle createTurtle(KernelTimeManager arg0) {
        return null;
    }

    @Override
    protected Object[] getTurtleActivationParameters(Turtle arg0,
            KernelTimeManager arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected boolean isSpawnable(KernelTimeManager arg0) {
        return this.budget > 0;
    }

    @Override
    protected void turtleSpawned(Turtle arg0, TurtleBody arg1,
            KernelTimeManager arg2) {
        --this.budget;
    }

    @Override
    protected void turtleSpawned(AgentAddress arg0, TurtleBody arg1,
            KernelTimeManager arg2) {
     // TODO Auto-generated method stub
    }


}
