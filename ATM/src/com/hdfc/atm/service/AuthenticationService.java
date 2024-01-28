package com.hdfc.atm.service;

import com.hdfc.atm.iservice.IAuthenticationService;

public class AuthenticationService implements IAuthenticationService{
	private Integer passCode = 4321;
	@Override
	public boolean authentication(Integer pin) {
	
		return passCode.equals(pin)? true:false;
	}
	@Override
	public boolean resetPin(Integer newPin) {
		boolean isChanged = false;
		try
		{
			this.passCode=newPin;
			isChanged = true;
		}catch(Exception ex)
		{
			System.out.println("Excaption raised while changing the pin :"+ex.getMessage());
		}
		return isChanged;
	}

}
