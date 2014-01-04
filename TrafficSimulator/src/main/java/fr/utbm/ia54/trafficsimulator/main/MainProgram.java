package fr.utbm.ia54.trafficsimulator.main;
 
import java.util.logging.Level;

import javax.swing.JFrame;

import org.janusproject.jaak.envinterface.channel.GridStateChannel;
import org.janusproject.jaak.environment.model.JaakEnvironment;
import org.janusproject.jaak.environment.solver.ActionApplier;
import org.janusproject.jaak.kernel.JaakKernel;
import org.janusproject.jaak.kernel.JaakKernelController;
import org.janusproject.jaak.spawner.JaakSpawner;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.logger.LoggerUtil;

import fr.utbm.ia54.trafficsimulator.environment.TrafficLight;
import fr.utbm.ia54.trafficsimulator.environment.TrafficSimulationWorldSpawner;
import fr.utbm.ia54.trafficsimulator.environment.Wall;
import fr.utbm.ia54.trafficsimulator.ui.TrafficSimulatorFrame;
import fr.utbm.ia54.trafficsimulator.ui.TrafficSimulatorPanel;

public class MainProgram {

    public MainProgram() {
        
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        LoggerUtil.setGlobalLevel(Level.WARNING);
        JaakEnvironment environment = createEnvironment();

        JaakSpawner[] spawner = new JaakSpawner[1];
        spawner[0] = new TrafficSimulationWorldSpawner(environment);
        JaakKernelController controller = JaakKernel.initializeKernel(environment, spawner);
        controller.getTimeManager().setWaitingDuration(500);
        GridStateChannel channel = Kernels.get().getChannelManager().getChannel(controller.getKernelAddress(), GridStateChannel.class);
        TrafficSimulatorPanel panel = new TrafficSimulatorPanel(channel);
        JFrame frame = new TrafficSimulatorFrame(panel);
        frame.setVisible(true);

    }
    
    private static JaakEnvironment createEnvironment() {
        JaakEnvironment environment = new JaakEnvironment(50,50);
        ActionApplier ap = environment.getActionApplier();
        
        environment.setWrapped(true);        
        
        for(int x1=1;x1<environment.getWidth();x1+=8) {
            for(int y1=1;y1<environment.getHeight();y1+=8) {
                for(int x2=x1;x2<x1+6;++x2) {
                    for(int y2=y1;y2<y1+6;++y2) {
                        ap.putObject(x2, y2, new Wall());
                    }
                }
                ap.putObject(x1+6, y1+5, new TrafficLight());
                ap.putObject(x1+5, y1+7, new TrafficLight());
                ap.putObject(x1+8, y1+6, new TrafficLight());
                ap.putObject(x1+7, y1+8, new TrafficLight());
            }
        }
        
        

        return environment;
    }

}
