package rubydelegator.jruby;

import javassist.util.proxy.MethodHandler;

public interface ScriptMethodHandler extends MethodHandler {

	void init();

}
