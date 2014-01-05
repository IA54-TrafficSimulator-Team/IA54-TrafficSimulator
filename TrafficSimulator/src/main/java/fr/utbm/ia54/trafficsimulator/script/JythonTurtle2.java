package fr.utbm.ia54.trafficsimulator.script;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;

import javax.script.ScriptEngineManager;

import org.janusproject.jythonengine.JythonExecutionContext;
import org.janusproject.scriptedagent.ScriptedAgent;
import org.python.core.Options;
import org.python.core.PyObject;
import org.python.core.PyType;

/**
 * Agent created to run Jython commands and scripts.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class JythonTurtle2 extends ScriptedTurtle2<JythonExecutionContext> {

    private static final long serialVersionUID = -1096878039754819565L;

    private static boolean isReset = false;
    
    private static void resetMemberAccess() {
        synchronized(JythonTurtle2.class) {
            if (!isReset) {
                isReset = true;
                Class<?> type = JythonTurtle2.class;
                // Force the Python interpreter to ignore the Java access rights
                Options.respectJavaAccessibility = false;
                
                // Revert the access right changes for the private members
                while (type!=null && !type.equals(Object.class)) {
                    PyType ptype = PyType.fromClass(type);
                    PyObject dictionary = ptype.fastGetDict();
                    for(Method method : type.getDeclaredMethods()) {
                        if (!Modifier.isPublic(method.getModifiers())
                            &&!Modifier.isProtected(method.getModifiers())) {
                            try {
                                dictionary.__delitem__(method.getName());
                            }
                            catch(Throwable _) {
                                // Silently pass the exception
                            }
                        }
                    }
                    for(Field field : type.getDeclaredFields()) {
                        if (!Modifier.isPublic(field.getModifiers())
                            &&!Modifier.isProtected(field.getModifiers())) {
                            try {
                                dictionary.__delitem__(field.getName());
                            }
                            catch(Throwable _) {
                                // Silently pass the exception
                            }
                        }
                    }
                    type = type.getSuperclass();
                }
        
                // Restore the accessibility checking for the other classes than JythonAgent
                Options.respectJavaAccessibility = false;
            }
        }
    }
    
    /**
     * Creates a new JythonAgent.
     * 
     * @param scriptManager is the manager of the script engines to use.
     */
    public JythonTurtle2(ScriptEngineManager scriptManager) {
        super(new JythonExecutionContext(scriptManager));
        resetMemberAccess();
    }
    
    /**
     * Creates a new JythonAgent. 
     */
    public JythonTurtle2() {
        this(getSharedScriptEngineManager());
    }

    /**
     * Creates a new JythonAgent.
     * The script to load is locaded in
     * one of the directories managed by the script directory repository.
     * 
     * @param scriptManager is the manager of the script engines to use.
     * @param scriptBasename is the basename of the script to load at startup.
     */
    public JythonTurtle2(ScriptEngineManager scriptManager, String scriptBasename) {
        super(new JythonExecutionContext(scriptManager), scriptBasename);
        resetMemberAccess();
    }
    
    /**
     * Creates a new JythonAgent. 
     * The script to load is locaded in
     * one of the directories managed by the script directory repository.
     * 
     * @param scriptBasename is the basename of the script to load at startup.
     */
    public JythonTurtle2(String scriptBasename) {
        this(getSharedScriptEngineManager(), scriptBasename);
    }

    /**
     * Creates a new JythonAgent.
     * 
     * @param scriptManager is the manager of the script engines to use.
     * @param script is the filename of the script to load at startup.
     */
    public JythonTurtle2(ScriptEngineManager scriptManager, File script) {
        super(new JythonExecutionContext(scriptManager), script);
        resetMemberAccess();
    }
    
    /**
     * Creates a new JythonAgent. 
     * 
     * @param script is the filename of the script to load at startup.
     */
    public JythonTurtle2(File script) {
        this(getSharedScriptEngineManager(), script);
    }

    /**
     * Creates a new JythonAgent.
     * 
     * @param scriptManager is the manager of the script engines to use.
     * @param script is the filename of the script to load at startup.
     */
    public JythonTurtle2(ScriptEngineManager scriptManager, URL script) {
        super(new JythonExecutionContext(scriptManager), script);
        resetMemberAccess();
    }
    
    /**
     * Creates a new JythonAgent. 
     * 
     * @param script is the filename of the script to load at startup.
     */
    public JythonTurtle2(URL script) {
        this(getSharedScriptEngineManager(), script);
    }

}