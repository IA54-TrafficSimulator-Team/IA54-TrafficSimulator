package fr.utbm.ia54.trafficsimulator.script;

import java.io.IOException;
import java.lang.ref.SoftReference;

import javax.script.ScriptEngineManager;

import org.arakhne.afc.vmutil.locale.Locale;
import org.janusproject.jaak.turtle.Turtle;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;
import org.janusproject.scriptedagent.ScriptExecutionContext;

public abstract class ScriptedTurtle <C extends ScriptExecutionContext> extends Turtle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6735814269138266278L;

	public static final String ACTIVATE_SCRIPT_FUNCTION = "activateTurtle";
	public static final String LIVE_SCRIPT_FUNCTION = "liveTurtle"; 
	public static final String END_SCRIPT_FUNCTION = "endTurtle";
	
	private static SoftReference<ScriptEngineManager> sharedManager = null;
	
	public static ScriptEngineManager getSharedScriptEngineManager() {
		synchronized(ScriptedTurtle.class) {
			ScriptEngineManager manager = (sharedManager==null) ? null : sharedManager.get();
			if (manager==null) {
				manager = new ScriptEngineManager();
				sharedManager = new SoftReference<ScriptEngineManager>(manager);
			}
			return manager;
		}
	}
	
	//private C scriptExecutor;
	//private TeeManager teeManager = null;
	//private ScriptLoader<C> scriptLoader = null;
	//private boolean automaticScriptExecution;
	
	
	protected static abstract class ScriptLoader<C extends ScriptExecutionContext> {

		/**
		 */
		public ScriptLoader() {
			//
		}
		
		/** Run the startup script.
		 * 
		 * @param context
		 * @param agent
		 * @throws IOException
		 */
		protected abstract void loadStartupScript(ScriptExecutionContext context, ScriptedTurtle<C> agent) throws IOException;
		
		/** Load the startup script.
		 * 
		 * @param context is the execution context
		 * @param agent is the agent associated to the script.
		 * @return the execution status.
		 */
		Status load(ScriptExecutionContext context, ScriptedTurtle<C> agent) {
			Status status = null;
			boolean isCatched = context.isCatchAllExceptions();
			context.setCatchAllExceptions(false);
			try {
				loadStartupScript(context, agent);
			}
			catch(Throwable e) {
				status = StatusFactory.error(
						agent,
						Locale.getString(ScriptedTurtle.class,
								"AUTOMATIC_EXECUTION_CANCELED", //$NON-NLS-1$
								agent.getAddress()),
						e);
				status.setLoggable(false); // Status already logged
			}
			context.setCatchAllExceptions(isCatched);
			return status;
		}
		
	}

}
