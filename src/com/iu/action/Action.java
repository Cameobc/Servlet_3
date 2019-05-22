package com.iu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	//List
	public abstract ActionForward list(HttpServletRequest request, HttpServletResponse response);
	
	//Select
	public abstract ActionForward select(HttpServletRequest request, HttpServletResponse response);
	
	//Insert
	public abstract ActionForward insert(HttpServletRequest request, HttpServletResponse response);
	
	//Update
	public abstract ActionForward update(HttpServletRequest request, HttpServletResponse response);
	
	//Delete
	public abstract ActionForward delete(HttpServletRequest request, HttpServletResponse response);

}
