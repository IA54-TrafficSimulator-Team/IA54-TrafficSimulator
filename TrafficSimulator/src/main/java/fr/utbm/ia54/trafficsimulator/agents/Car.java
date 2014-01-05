package fr.utbm.ia54.trafficsimulator.agents;

import java.util.Random;

import org.arakhne.afc.math.continous.object2d.Vector2f;
import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.envinterface.body.TurtleBodyFactory;
import org.janusproject.jaak.envinterface.frustum.SquareTurtleFrustum;
import org.janusproject.jaak.envinterface.perception.Obstacle;
import org.janusproject.jaak.turtle.Turtle;

import fr.utbm.ia54.trafficsimulator.environment.TrafficLight;
import fr.utbm.ia54.trafficsimulator.environment.Wall;

public class Car extends Turtle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3430254702259655426L;

	private Point2i destination;
	private static Random random;
	private boolean wasOnCrossingBefore;
	@SuppressWarnings("boxing")
	public Car(Point2i destination) {
		super(true);
		this.destination = destination;
		this.wasOnCrossingBefore = false;
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
	    //System.out.println("Destination : " + destination + " Position : " + getPosition());
	    boolean destinationInWall = false;
	    
	    for(Obstacle o : this.getPerceivedObjects(Obstacle.class)) {
	        if (o.getPosition().equals(this.destination))
	            destinationInWall = true;
	    }
		//If the car is arrived at its destination : don't move and create new destination
		if(this.getPosition().equals(this.destination) || destinationInWall) {
			System.out.println("Car arrived at destination : " + this.destination); //$NON-NLS-1$
			this.destination = new Point2i(random.nextInt(47),random.nextInt(47));
			System.out.println("New destination : " + this.destination); //$NON-NLS-1$
			return;
		}
		
		//If the car is in front of a traffic light which is red
		for(TrafficLight tl : this.getPerceivedObjects(TrafficLight.class)) {
			if(tl.getPosition().equals(this.getPosition()) && tl.getStopLight()) {
				return;
			}
		}

		//If we have a wall on an adjacent square, we are on a street : no choice but to go forward
		for(Wall o : this.getPerceivedObjects(Wall.class)) {
			Point2i p = o.getPosition();

			//If wall on left : go down
			if(p.y() == this.getY() && p.x() == this.getX()-1) {
				move(0,1,true);
				this.wasOnCrossingBefore = false;
				return;
			}

			//If wall on right : go up
			if(p.y() == this.getY() && p.x() == this.getX()+1) {
				move(0,-1,true);
				this.wasOnCrossingBefore = false;
				return;
			}

			//If wall on top : go left
			if(p.x() == this.getX() && p.y() == this.getY()-1) {
				move(-1,0,true);
				this.wasOnCrossingBefore = false;
				return;
			}

			//If wall on bottom : go right
			if(p.x() == this.getX() && p.y() == this.getY()+1) {
				move(1,0,true);
				this.wasOnCrossingBefore = false;
				return;
			}
		}
		
		if(this.wasOnCrossingBefore) {
		    if(this.destination.x() == this.getX()) {
		        if(this.destination.y() > this.getY()) {
		            move(0,1,true);
		            return;
		        }
		        move(0,-1,true);
		        return;
		    }
		    if(this.destination.y() == this.getY()) {
                if(this.destination.x() > this.getX()) {
                    move(1,0,true);
                    return;
                }
                move(-1,0,true);
                return;
            }
		    moveForward(1);
		    return;
		}
		
		this.wasOnCrossingBefore = true;
		
		//Determine on which corner of the crossing the car is
		String corner = null;
		for(Wall o : this.getPerceivedObjects(Wall.class)) {
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
			if(this.destination.x() > this.getX()) {
				move(1,0,true);
				return;
			}
			if(this.destination.y() < this.getY()) {
				move(0,-1,true);
				return;
			}
			move(-1,-1,true);
			this.setHeading(new Vector2f(-1,0));
			return; 
		}
		if(corner == "BOTTOMLEFT") { //$NON-NLS-1$
			if(this.destination.y() > this.getY()) {
				move(0,1,true);
				return;
			}
			if(this.destination.x() > this.getX()) {
				move(1,0,true);
				return;
			}
			move(1,-1,true);
			this.setHeading(new Vector2f(0,-1));
			return; 
		}
		if(corner == "TOPRIGHT") { //$NON-NLS-1$
			if(this.destination.y() < this.getY()) {
				move(0,-1,true);
				return;
			}
			if(this.destination.x() < this.getX()) {
				move(-1,0,true);
				return;
			}
			move(-1,1,true);
			this.setHeading(new Vector2f(0,1));
			return; 
		}
		if(corner == "TOPLEFT") { //$NON-NLS-1$
			if(this.destination.x() < this.getX()) {
				move(-1,0,true);
				return;
			}
			if(this.destination.y() > this.getY()) {
				move(0,1,true);
				return;
			}
			move(1,1,true);
			this.setHeading(new Vector2f(1,0));
			return; 
		}
	}


}
