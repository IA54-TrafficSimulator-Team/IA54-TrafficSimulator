package fr.utbm.ia54.trafficsimulator.agents;

import java.util.Random;

import org.arakhne.afc.math.discrete.object2d.Point2i;
import org.janusproject.jaak.envinterface.body.TurtleBody;
import org.janusproject.jaak.envinterface.body.TurtleBodyFactory;
import org.janusproject.jaak.envinterface.perception.Obstacle;
import org.janusproject.jaak.turtle.Turtle;

public class Car extends Turtle {

    /**
     * 
     */
    private static final long serialVersionUID = 3430254702259655426L;

    private Point2i destination;
    private static Random random;
    
    public Car(Point2i destination) {
        this.destination = destination;
        if(Car.random == null)
            Car.random = new Random();
    }

    @Override
    protected TurtleBody createTurtleBody(TurtleBodyFactory factory) {
        return factory.createTurtleBody(getAddress(), new Point2i(10, 10));//,new CircleTurtleFrustum(10));
    }

    @Override
    protected void turtleBehavior() {
        System.out.println(""); //$NON-NLS-1$
        System.out.println("Position : "+this.getPosition()); //$NON-NLS-1$
        System.out.println("Want to go to : "+this.destination); //$NON-NLS-1$
        int x, y;
        
        if(this.destination.x() > this.getX())
            x = 1;
        else if (this.destination.x() < this.getX())
            x = -1;
        else
            x = 0;
        
        if(this.destination.y() > this.getY())
            y = 1;
        else if (this.destination.y() < this.getY())
            y = -1;
        else
            y = 0;
        
        //Avoid going in diagonals
        if(x!=0 && y!=0) {
            if(random.nextBoolean())
                x = 0;
            else
                y = 0;
        }
        
        if(!this.hasPerceivedObject()) {
            System.out.println("No percieved object"); //$NON-NLS-1$
            move(x,y,true);
            return;
        }
        
        boolean canX = true;
        boolean canY = true;
        
        for(Obstacle o : this.getPerceivedObjects(Obstacle.class)) {
            if(o.getPosition().x() == this.getX() + x && o.getPosition().y() == this.getY()) {
                System.out.println("cant x"); //$NON-NLS-1$
                canX = false;
            }
            if(o.getPosition().y() == this.getY() + y && o.getPosition().x() == this.getX()) {
                System.out.println("cant y"); //$NON-NLS-1$
                canY = false;
            }
        }
        
        if(!canX)
            x = 0;
        if(!canY)
            y = 0;
        
        move(x,y,true);
    }


}
