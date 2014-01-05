package fr.utbm.ia54.trafficsimulator.agents;

import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.envinterface.body.TurtleBodyFactory;
import org.janusproject.jaak.envinterface.frustum.SquareTurtleFrustum;

import fr.utbm.ia54.trafficsimulator.script.JythonTurtle;

public class JythonCar extends JythonTurtle {

    /**
     * 
     */
    private static final long serialVersionUID = -530889618501640929L;
    
    private Point2i destination;
    @SuppressWarnings("unused")
	private boolean wasOnCrossingBefore;
    
    public JythonCar(Point2i destination) {
    	
    	super("git/IA54-TrafficSimulator/TrafficSimulator/res/scripts-python/PythonCar.py"); //$NON-NLS-1$
        //super("Documents\\GitHub\\IA54-TrafficSimulator\\TrafficSimulator\\PythonTurtles\\testturtle.py"); //$NON-NLS-1$
        this.destination = destination;
    }
    
    @Override
    protected TurtleBody createTurtleBody(TurtleBodyFactory factory) {
        return factory.createTurtleBody(getAddress(), new Point2i(10, 10), new SquareTurtleFrustum(6));
    }
    
    public void setDestination(int x, int y) {
    	this.destination.setX(x);
    	this.destination.setY(y);
    }
    
    public void setHeading(float x, float y) {
    	this.setHeading(new Vector2f(x,y));
    }
}
