package usabilityimprover.service;

import java.lang.reflect.Method;

public class Strategy {
	private String method;
	private Class[] parameterTypes;
	private Object[] parameters;
	private Object implObj;
	private int streatgyType;
	
	public Strategy(String method, Class[] parameterTypes, Object[] parameters,int streatgyType) {
		this.method = method;
		this.parameterTypes = parameterTypes;
		this.parameters = parameters;
		this.implObj = implObj;
		this.streatgyType = streatgyType;
	}
	public Object getImplObj() {
		return implObj;
	}
	public String getMethod() {
		return method;
	}
	public Class[] getParmeterTypes() {
		return parameterTypes;
	}
	public Object[] getParmeters() {
		return parameters;
	}
	public int getStreatgyType() {
		return streatgyType;
	}

}
