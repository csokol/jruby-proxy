package rubydelegator.jruby.converter;


public interface JavaObjectToScriptConverter {

	String toScriptlet(Object javaObject);

	boolean canConvert(Object javaObject);

	String convert(Object javaObject);

	JavaObjectToScriptConverter getDelegate();

}
