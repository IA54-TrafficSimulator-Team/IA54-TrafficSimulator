package fr.utbm.ia54.trafficsimulator.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import org.janusproject.jaak.envinterface.channel.GridStateChannel;
import org.janusproject.jaak.envinterface.channel.GridStateChannelListener;

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
        super.paint(g);
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
        }
    }
    
    public synchronized void gridStateChanged() {
        
        
        repaint();
    }
}
