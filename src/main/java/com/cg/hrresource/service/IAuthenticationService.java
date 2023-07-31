package com.cg.hrresource.service;

import com.cg.hrresource.dto.AuthenticationRequest;
import com.cg.hrresource.dto.AuthenticationResponse;
import com.cg.hrresource.dto.RegisterRequest;
import com.cg.hrresource.entities.User;

public interface IAuthenticationService {
	AuthenticationResponse register(RegisterRequest request);

	AuthenticationResponse authenticate(AuthenticationRequest request);

	User changePassword(AuthenticationRequest request);

	User getUserById(int id);

	boolean deletebyid(Integer id);

	boolean signout(int id);

	User getUserDetails(String email);

}
