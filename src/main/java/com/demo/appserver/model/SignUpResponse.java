package com.demo.appserver.model;

public class SignUpResponse {
    private boolean succeed;
    private String message;

    public SignUpResponse(boolean succeed, String message){
        this.succeed = succeed;
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

	/**
	 * @return the succeed
	 */
	public boolean isSucceed() {
		return succeed;
	}

	/**
	 * @param succeed the succeed to set
	 */
	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}
}