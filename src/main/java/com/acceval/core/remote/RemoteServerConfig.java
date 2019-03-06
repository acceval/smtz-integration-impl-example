package com.acceval.core.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({ "classpath:remote-server.properties" })
public class RemoteServerConfig {

	@Autowired
	private Environment env;
	
	public String getCrendentialUser() {
		return env.getProperty("credential_user");
	}
	
	public String getCrendentialPassword() {
		return env.getProperty("credential_password");
	}
	
	public String getRemoteIp() {
		return env.getProperty("remote_ip");
	}
	
	public String getRemotePort() {
		return env.getProperty("remote_port");
	}
	

}
