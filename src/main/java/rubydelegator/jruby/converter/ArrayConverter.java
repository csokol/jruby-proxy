package rubydelegator.jruby.converter;

public class ArrayConverter extends AbstractJavaObjectToScriptConverter {
	private static Object[] ARR = new Object[0];
	private JavaObjectToScriptConverter delegate;
	
	public ArrayConverter(JavaObjectToScriptConverter delegate) {
		this.delegate = delegate;
	}

	@Override
	public String toScriptlet(Object javaObject) {
		Object[] arr = (Object[]) javaObject;
		StringBuffer buf = new StringBuffer("[");
		for (Object object : arr) {
			String converted = delegate.convert(object);
			buf.append(converted);
			buf.append(",");
		}
		buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}

	@Override
	public boolean canConvert(Object javaObject) {
		return javaObject != null && javaObject.getClass().isAssignableFrom(ARR.getClass());
	}

	@Override
	public JavaObjectToScriptConverter getDelegate() {
		return delegate;
	}

}
