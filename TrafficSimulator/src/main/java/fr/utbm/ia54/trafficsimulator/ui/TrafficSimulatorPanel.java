package fr.utbm.ia54.trafficsimulator.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.JPanel;

import org.janusproject.jaak.envinterface.channel.GridStateChannel;
import org.janusproject.jaak.envinterface.channel.GridStateChannelListener;
import org.janusproject.jaak.envinterface.perception.EnvironmentalObject;
import org.janusproject.jaak.envinterface.perception.Obstacle;

import fr.utbm.ia54.trafficsimulator.environment.TrafficLight;
import fr.utbm.ia54.trafficsimulator.environment.Wall;

public class TrafficSimulatorPanel extends JPanel implements
GridStateChannelListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1634442678067332107L;

	public static final int CELL_SIZE = 16;

	private final GridStateChannel channel;

	public GridStateChannel getChannel() {
		return this.channel;
	}

	public TrafficSimulatorPanel(GridStateChannel channel) {
		setBackground(Color.GRAY);
		this.channel = channel;
		this.channel.addGridStateChannelListener(this);
		addMouseListener(this);

	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void jaakEnd() {
		// TODO Auto-generated method stub

	}

	public void jaakStart() {
		repaint();

	}

	@Override
	public synchronized void paint(Graphics g) {

		/*super.paint(g);
        for(int x=0; x<this.channel.getGridWidth(); ++x) {

            for(int y=0; y<this.channel.getGridHeight(); ++y) {
                if(this.channel.getEnvironmentalObjects(x, y).iterator().hasNext()) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                if(this.channel.containsTurtle(x, y)) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }*/

		super.paint(g);
		Iterable<EnvironmentalObject> iterable;
		Iterator<EnvironmentalObject> iterator;
		EnvironmentalObject obj = null;
		for(int x=0; x<this.channel.getGridWidth(); ++x) {
			for(int y=0; y<this.channel.getGridHeight(); ++y) {
				if(this.channel.containsTurtle(x, y)) {
					g.setColor(Color.BLUE);
					g.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
				}
				else {
					iterable = this.channel.getEnvironmentalObjects(x, y);
					if(iterable!=null){
						iterator=iterable.iterator();
						if(iterator.hasNext()) {
							obj = iterator.next();
						}
						if(obj!=null) {
							if(obj instanceof Wall) {
								g.setColor(Color.BLACK);
								g.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
							}
							if(obj instanceof TrafficLight) {
								if(((TrafficLight)obj).getStopLight())
									g.setColor(Color.RED);
								else	
									g.setColor(Color.GREEN);
								g.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
							}
						}
					}
				}
			}
		}
	}

	public synchronized void gridStateChanged() {

		repaint();
	}
}
