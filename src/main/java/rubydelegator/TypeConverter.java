package rubydelegator;

import org.jruby.runtime.builtin.IRubyObject;

public interface TypeConverter {

	IRubyObject convert(Object javaObject);

}
