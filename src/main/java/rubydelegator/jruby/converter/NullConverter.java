package rubydelegator.jruby.converter;

public class NullConverter extends AbstractJavaObjectToScriptConverter
		implements JavaObjectToScriptConverter {

	private JavaObjectToScriptConverter delegate;

	public NullConverter(JavaObjectToScriptConverter delegate) {
		this.delegate = delegate;
	}

	@Override
	public String toScriptlet(Object javaObject) {
		if (javaObject == null)
			return "nil";
		throw new IllegalArgumentException("not null object");
	}

	@Override
	public boolean canConvert(Object javaObject) {
		return javaObject == null;
	}

	@Override
	public JavaObjectToScriptConverter getDelegate() {
		return delegate;
	}
}