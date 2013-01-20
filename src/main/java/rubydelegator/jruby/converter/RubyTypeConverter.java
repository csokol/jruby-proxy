package rubydelegator.jruby.converter;

import org.jruby.Ruby;
import org.jruby.runtime.builtin.IRubyObject;

import rubydelegator.TypeConverter;

public class RubyTypeConverter implements TypeConverter {

	private Ruby runtime;
	private ObjectToScriptlet converter;

	public RubyTypeConverter(Ruby runtime) {
		this.runtime = runtime;
		converter = new NumberConverter(null);
		converter = new StringConverter(converter);
	}

	@Override
	public IRubyObject convert(Object javaObject) {
		return runtime.evalScriptlet(converter.convert(javaObject));
	}

}