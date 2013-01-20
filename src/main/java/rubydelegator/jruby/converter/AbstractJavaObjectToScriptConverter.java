package rubydelegator.jruby.converter;

public abstract class AbstractJavaObjectToScriptConverter implements JavaObjectToScriptConverter {
	
	@Override
	public String convert(Object javaObject) {
		if (canConvert(javaObject)) {
			return toScriptlet(javaObject);
		}
		JavaObjectToScriptConverter delegate = getDelegate();
		return delegate.convert(javaObject);
	}

}
