package com.decathlon.usecase.error.custom;

public class SmsServiceCommunicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9146471703144206106L;

	public SmsServiceCommunicationException(String message) {
		super("Error encountered while sending SMS through external API: "+message);
	}
}
