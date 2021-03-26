package com.decathlon.usecase.error.custom;

public class TeamNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TeamNotFoundException(Long teamId) {
		super("Team not found for id: "+teamId);
	}

}
