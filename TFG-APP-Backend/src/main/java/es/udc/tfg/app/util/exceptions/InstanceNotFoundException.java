package es.udc.tfg.app.util.exceptions;

public class InstanceNotFoundException extends Exception {

	private Object instanceId;
	private String className;
	
	public InstanceNotFoundException (Object instanceId, String className) {
		super("Instance Not Found (instanceId = '" + instanceId + "' & className = '" + className + "')");
		this.instanceId = instanceId;
		this.className = className;
	}

	public Object getInstanceId() {
		return instanceId;
	}

	public String getClassName() {
		return className;
	}


}
