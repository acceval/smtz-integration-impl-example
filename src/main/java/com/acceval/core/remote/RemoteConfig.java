package com.acceval.core.remote;

public class RemoteConfig {

	private String credentialUser;
	private String credentialPassword;
	private String remoteIp;
	private String remotePort;
		
	public String getCredentialUser() {
		return credentialUser;
	}
	public void setCredentialUser(String credentialUser) {
		this.credentialUser = credentialUser;
	}
	public String getCredentialPassword() {
		return credentialPassword;
	}
	public void setCredentialPassword(String credentialPassword) {
		this.credentialPassword = credentialPassword;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public String getRemotePort() {
		return remotePort;
	}
	public void setRemotePort(String remotePort) {
		this.remotePort = remotePort;
	}
	

}
