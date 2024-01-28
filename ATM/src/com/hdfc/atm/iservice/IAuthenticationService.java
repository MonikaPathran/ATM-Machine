package com.hdfc.atm.iservice;

public interface IAuthenticationService {
	public boolean authentication(Integer pin);
	public boolean resetPin(Integer newPin);
}
