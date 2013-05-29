package com.corejsf;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("user")
@SessionScoped
public class UserBean implements Serializable {

	private static final Logger logger = Logger.getLogger("com.corejsf");
	private static final long serialVersionUID = 1L;

	private String name;
	private String role;
	
	public String getName() {
		if (name == null) getUserData();
		
		return name == null ? "" : name;
	}
	
	public String getRole() {
		return role == null ? "" : role;
	}
	
	public void setRole(String newValue) {
		role = newValue;
	}
	
	public boolean isInRole() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		Object requestObject = context.getRequest();
		if (!(requestObject instanceof HttpServletRequest)) {
			logger.severe("request object has type " + requestObject.getClass());
			
			return false;
		}
		
		HttpServletRequest request = (HttpServletRequest)requestObject;
		return request.isUserInRole(role);
	}
	
	private void getUserData() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		Object requestObject = context.getRequest();
		if (!(requestObject instanceof HttpServletRequest)) {
			logger.severe("request object has type " + requestObject.getClass());
			
			return;
		}
		
		HttpServletRequest request = (HttpServletRequest)requestObject;
		name = request.getRemoteUser();
	}
}
