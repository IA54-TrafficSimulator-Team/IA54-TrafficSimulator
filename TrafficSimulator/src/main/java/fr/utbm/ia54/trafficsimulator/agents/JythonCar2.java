package fr.utbm.ia54.trafficsimulator.agents;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.envinterface.body.TurtleBodyFactory;
import org.janusproject.jaak.envinterface.frustum.SquareTurtleFrustum;

import fr.utbm.ia54.trafficsimulator.script.JythonTurtle2;

public class JythonCar2 extends JythonTurtle2 {

    /**
     * 
     */
    private static final long serialVersionUID = -530889618501640929L;
    
    private Point2i destination;
    
    public JythonCar2(Point2i destination) {
        super("Documents\\GitHub\\IA54-TrafficSimulator\\TrafficSimulator\\PythonTurtles\\testturtle.py"); //$NON-NLS-1$
        this.destination = destination;
    }
    
    @Override
    protected TurtleBody createTurtleBody(TurtleBodyFactory factory) {
        return factory.createTurtleBody(getAddress(), new Point2i(10, 10), new SquareTurtleFrustum(6));
    }
}
