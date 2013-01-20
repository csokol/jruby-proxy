package rubydelegator.jruby;

import java.lang.reflect.Method;

import org.jruby.Ruby;
import org.jruby.runtime.builtin.IRubyObject;

public class RubyMethodHandler<T> implements ScriptMethodHandler {

	private final Ruby runtime;
	private IRubyObject rubyClass;
	private IRubyObject rubyInstance;
	private final TypeConverter converter;
	private final Class<T> javaClass;

	public RubyMethodHandler(Ruby runtime, Class<T> javaClass,
			TypeConverter converter) {
		this.runtime = runtime;
		this.javaClass = javaClass;
		this.converter = converter;
	}
	
	@Override
	public void init() {
		rubyClass = runtime.evalScriptlet(javaClass.getSimpleName());
		rubyInstance = rubyClass.callMethod(runtime.getCurrentContext(), "new");
	}

	@Override
	public Object invoke(Object self, Method thisMethod, Method proceed,
			Object[] args) throws Throwable {
		IRubyObject[] rubyParams = new IRubyObject[args.length];
		for (int i = 0; i < args.length; i++) {
			IRubyObject rubyObject = converter.convert(args[i]);
			rubyParams[i] = rubyObject;
		}
		IRubyObject result = rubyInstance.callMethod(
				runtime.getCurrentContext(), thisMethod.getName(), rubyParams);
		return result.toJava(thisMethod.getReturnType());
	}

}
