package rubydelegator.jruby.converter;

public abstract class AbstractObjectToScriptlet implements ObjectToScriptlet {
	
	@Override
	public String convert(Object javaObject) {
		if (canConvert(javaObject)) {
			return toScriptlet(javaObject);
		}
		ObjectToScriptlet delegate = getDelegate();
		return delegate.convert(javaObject);
	}

}
