package fr.utbm.ia54.trafficsimulator.agents;

import java.util.Random;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.envinterface.body.TurtleBodyFactory;
import org.janusproject.jaak.envinterface.frustum.SquareTurtleFrustum;
import org.janusproject.jaak.envinterface.perception.Obstacle;
import org.janusproject.jaak.turtle.Turtle;

import fr.utbm.ia54.trafficsimulator.environment.TrafficLight;

public class Car extends Turtle {

    /**
     * 
     */
    private static final long serialVersionUID = 3430254702259655426L;

    private Point2i destination;
    private static Random random;
    
    @SuppressWarnings("boxing")
    public Car(Point2i destination) {
        super(true);
        this.destination = destination;
        System.out.println("New turtle, destination : "+destination); //$NON-NLS-1$
        if(Car.random == null)
            Car.random = new Random();
    }

    @Override
    protected TurtleBody createTurtleBody(TurtleBodyFactory factory) {
        return factory.createTurtleBody(getAddress(), new Point2i(10, 10), new SquareTurtleFrustum(6));
    }

    @Override
    protected void turtleBehavior() {
        if(this.getPosition().equals(this.destination)) {
            System.out.println("Car arrived at destination : " + this.destination); //$NON-NLS-1$
            return;
        }
        for(TrafficLight tl : this.getPerceivedObjects(TrafficLight.class)) {
            if(tl.getPosition().equals(this.getPosition()) && tl.getStopLight()) {
                return;
            }
        }
        //If we have a wall on an adjacent square, we are on a street : no choice but to go forward
        for(Obstacle o : this.getPerceivedObjects(Obstacle.class)) {
            Point2i p = o.getPosition();
            
            //If wall on left : go down
            if(p.y() == this.getY() && p.x() == this.getX()-1) {
                move(0,1,true);
                return;
            }
            
            if(p.y() == this.getY() && p.x() == this.getX()+1) {
                move(0,-1,true);
                return;
            }
            
            if(p.x() == this.getX() && p.y() == this.getY()-1) {
                move(-1,0,true);
                return;
            }
            
            if(p.x() == this.getX() && p.y() == this.getY()+1) {
                move(1,0,true);
                return;
            }
        }
        
        //Determine on which corner of the crossing the car is
        String corner = null;
        for(Obstacle o : this.getPerceivedObjects(Obstacle.class)) {
            Point2i p = o.getPosition();
            if(p.x() == this.getX()+1 && p.y() == this.getY()+1) {
                corner = "BOTTOMRIGHT"; //$NON-NLS-1$
                break;
            }
            else if(p.x() == this.getX()-1 && p.y() == this.getY()+1) {
                corner = "BOTTOMLEFT"; //$NON-NLS-1$
                break;
            }
            else if(p.x() == this.getX()+1 && p.y() == this.getY()-1) {
                corner = "TOPRIGHT"; //$NON-NLS-1$
                break;
            }
            else if(p.x() == this.getX()-1 && p.y() == this.getY()-1) {
                corner = "TOPLEFT"; //$NON-NLS-1$
                break;
            }
            else
                corner = "???"; //$NON-NLS-1$
        }
        if(corner == "BOTTOMRIGHT") { //$NON-NLS-1$
            if(this.destination.y() < this.getY()) {
                move(0,-1,true);
                return;
            }
            if(this.destination.x() < this.getX()) {
                move(-1,-1,true);
                return;
            }
            move(1,0,true);
            return; 
        }
        if(corner == "BOTTOMLEFT") { //$NON-NLS-1$
            if(this.destination.x() > this.getX()) {
                move(1,0,true);
                return;
            }
            if(this.destination.y() < this.getY()) {
                move(1,-1,true);
                return;
            }
            move(0,1,true);
            return; 
        }
        
        if(corner == "TOPRIGHT") { //$NON-NLS-1$
            if(this.destination.x() < this.getX()) {
                move(-1,0,true);
                return;
            }
            if(this.destination.y() < this.getY()) {
                move(0,-1,true);
                return;
            }
            move(-1,1,true);
            return; 
        }
        if(corner == "TOPLEFT") { //$NON-NLS-1$
            if(this.destination.y() > this.getY()) {
                move(0,1,true);
                return;
           }
            if(this.destination.x() < this.getX()) {
                move(-1,0,true);
                return;
            }
            move(1,1,true);
            return; 
        }
    }


}
