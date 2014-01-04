package fr.utbm.ia54.trafficsimulator.main;
 
import java.util.logging.Level;

import javax.swing.JFrame;

import org.janusproject.jaak.envinterface.channel.GridStateChannel;
import org.janusproject.jaak.envinterface.perception.Obstacle;
import org.janusproject.jaak.environment.model.JaakEnvironment;
import org.janusproject.jaak.environment.solver.ActionApplier;
import org.janusproject.jaak.kernel.JaakKernel;
import org.janusproject.jaak.kernel.JaakKernelController;
import org.janusproject.jaak.spawner.JaakSpawner;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.logger.LoggerUtil;

import fr.utbm.ia54.trafficsimulator.environment.TrafficSimulationWorldSpawner;
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
        JaakEnvironment environment = new JaakEnvironment(50, 50);
        ActionApplier ap = environment.getActionApplier();
        
        environment.setWrapped(true);
        
        /*for(int x=0;x<environment.getWidth();++x) {
            ap.putObject(x, 0, new Obstacle());
            ap.putObject(x, environment.getHeight()-1, new Obstacle());
        }
        
        for(int y=1;y<environment.getHeight();++y) {
            ap.putObject(0, y, new Obstacle());
            ap.putObject(environment.getWidth()-1, y, new Obstacle());
        }*/
        
        
        for(int x1=1;x1<environment.getWidth();x1+=7) {
            for(int y1=1;y1<environment.getHeight();y1+=7) {
                for(int x2=x1;x2<x1+6;++x2) {
                    for(int y2=y1;y2<y1+6;++y2) {
                        ap.putObject(x2, y2, new Obstacle());
                    }
                }
            }
        }
        
        /*for(int i=2;i<6;++i)
            ap.putObject(i, i, new Obstacle());*/

        return environment;
    }

}
