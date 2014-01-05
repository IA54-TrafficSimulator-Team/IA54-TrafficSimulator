from random import randrange
from org.janusproject.kernel.message import StringMessage
from fr.utbm.ia54.trafficsimulator.environment import Wall
from fr.utbm.ia54.trafficsimulator.environment import TrafficLight
def behaviourTurtle(turtle) :

    print turtle.getPosition()
    print turtle.destination

    destinationInWall = False

    for o in turtle.getPerceivedObjects(Wall) :
        if o.getPosition() == turtle.destination :
            destinationInWall = True

    if (turtle.getPosition() == turtle.destination or destinationInWall == True) :
        print "car arrived"
        turtle.setDestination(randrange(50),randrange(50))
        print "new destination"

    for tl in turtle.getPerceivedObjects(TrafficLight) :
        if (tl.getPosition() == turtle.getPosition() and tl.getStopLight() == True) :
            return

    for o in turtle.getPerceivedObjects(Wall) :
        p = o.getPosition()

        #If wall on left : go down
        if (p.y() == turtle.getY() and p.x() == turtle.getX()-1) :
            turtle.move(0,1,True)
            turtle.wasOnCrossingBefore = False
            return

        #If wall on right : go up
        if (p.y() == turtle.getY() and p.x() == turtle.getX()+1) :
            turtle.move(0,-1,True);
            turtle.wasOnCrossingBefore = False;
            return

        #If wall on top : go left
        if (p.x() == turtle.getX() and p.y() == turtle.getY()-1) :
            turtle.move(-1,0,True);
            turtle.wasOnCrossingBefore = False;
            return

        #If wall on bottom : go right
        if (p.x() == turtle.getX() and p.y() == turtle.getY()+1) :
            turtle.move(1,0,True);
            turtle.wasOnCrossingBefore = False;
            return

    if turtle.wasOnCrossingBefore == True :
        if turtle.destination.x() == turtle.getX() :
            if turtle.destination.y() > turtle.getY() :
                turtle.move(0,1,True)
                return
            turtle.move(0,-1,True)
            return
        if turtle.destination.y() == turtle.getY() :
            if turtle.destination.x() > turtle.getX() :
                turtle.move(1,0,True)
                return
            turtle.move(-1,0,True)
            return
        turtle.moveForward(1)
        return

    turtle.wasOnCrossingBefore = True

    #Determine on which corner of the crossing the car is
    for o in turtle.getPerceivedObjects(Wall) :
        p = o.getPosition()

        if (p.x() == turtle.getX()+1 and p.y() == turtle.getY()+1) :
            corner="BOTTOMRIGHT"
            break
        elif (p.x() == turtle.getX()-1 and p.y() == turtle.getY()+1) :
            corner="BOTTOMLEFT"
            break
        elif (p.x() == turtle.getX()+1 and p.y() == turtle.getY()-1) :
            corner="TOPRIGHT"
            break
        elif (p.x() == turtle.getX()-1 and p.y() == turtle.getY()-1) :
            corner="TOPLEFT"
            break
        else :
            corner="?"

    if corner == "BOTTOMRIGHT" :
        if turtle.destination.x() > turtle.getX() :
            turtle.move(1,0,True)
            return
        if turtle.destination.y() < turtle.getY() :
            turtle.move(0,-1,True)
            return
        turtle.move(-1,-1,True)
        turtle.setHeading(-1,0)
        return

    if corner == "BOTTOMLEFT" :
        if turtle.destination.y() > turtle.getY() :
            turtle.move(0,1,True)
            return
        if turtle.destination.x() > turtle.getX() :
            turtle.move(1,0,True)
            return
        turtle.move(1,-1,True)
        turtle.setHeading(0,-1)
        return

    if corner == "TOPRIGHT" :
        if turtle.destination.y() < turtle.getY() :
            turtle.move(0,-1,True)
            return
        if turtle.destination.x() < turtle.getX() :
            turtle.move(-1,0,True)
            return
        turtle.move(-1,1,True)
        turtle.setHeading(0,1)
        return

    if corner == "TOPLEFT" :
        if turtle.destination.x() < turtle.getX() :
            turtle.move(-1,0,True)
            return
        if turtle.destination.y() > turtle.getY() :
            turtle.move(0,1,True)
            return
        turtle.move(1,1,True)
        turtle.setHeading(1,0)
        return
