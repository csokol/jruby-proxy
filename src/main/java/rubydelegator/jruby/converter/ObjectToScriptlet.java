package rubydelegator.jruby.converter;


public interface ObjectToScriptlet {

	String toScriptlet(Object javaObject);

	boolean canConvert(Object javaObject);

	String convert(Object javaObject);

	ObjectToScriptlet getDelegate();

}
