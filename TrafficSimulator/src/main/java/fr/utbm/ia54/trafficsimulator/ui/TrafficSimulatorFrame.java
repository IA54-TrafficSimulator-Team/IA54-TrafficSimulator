package fr.utbm.ia54.trafficsimulator.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.ref.WeakReference;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.janusproject.jaak.envinterface.channel.GridStateChannel;
import org.janusproject.jaak.envinterface.channel.GridStateChannelListener;
import org.janusproject.kernel.agent.Kernels;

public class TrafficSimulatorFrame extends JFrame implements GridStateChannelListener {
	
	private static final long serialVersionUID = 530029308607649235L;

	private final WeakReference<GridStateChannel> channel;
	
	/**
	 * @param panel is the panel which is able to display the ant colony.
	 */
	public TrafficSimulatorFrame(TrafficSimulatorPanel panel) {
		this.channel = new WeakReference<GridStateChannel>(panel.getChannel());
		
		setTitle("Traffic Simulator"); //$NON-NLS-1$
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		addWindowListener(new Closer());
		
		JScrollPane scrollPane = new JScrollPane(panel);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(10+TrafficSimulatorPanel.CELL_SIZE*(getChannel().getGridWidth()), 10+TrafficSimulatorPanel.CELL_SIZE*(getChannel().getGridHeight())));
		
		pack();
		
		getChannel().addGridStateChannelListener(this);
	}
	
	/**
	 * Replies the channel to the grid.
	 * @return the channel to the grid.
	 */
	protected GridStateChannel getChannel() {
		return this.channel.get();
	}

	private static class Closer extends WindowAdapter {
		/**
		 */
		public Closer() {
			//
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void windowClosing(WindowEvent event) {
			Kernels.killAll();
			System.exit(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void jaakEnd() {
		getChannel().removeGridStateChannelListener(this);
		setTitle("Traffic Simulator"); //$NON-NLS-1$
	}

    public void gridStateChanged() {
        // TODO Auto-generated method stub
        
    }

    public void jaakStart() {
        // TODO Auto-generated method stub
        
    }

	
}
