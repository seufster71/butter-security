package org.mangobutter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.utils.Request;
import com.example.utils.Response;

@RestController
@RequestMapping("api/login/")
public class LoginController {

	@RequestMapping(value = "service", method = RequestMethod.POST)
	public Response service(@RequestBody Request request){
		
		Response response = new Response();
		Map<String,Object> params = new HashMap<String,Object>();
		
		if (request.getParams().get("username") != null && request.getParams().get("password") != null) {
		
			params.put("username", request.getParams().get("username"));
			params.put("password", request.getParams().get("password"));
			
			 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			 authorities.add(new SimpleGrantedAuthority("PRIVATE"));
			Authentication auth = new UsernamePasswordAuthenticationToken(request.getParams().get("username"),request.getParams().get("password"), authorities);
			SecurityContextHolder.getContext().setAuthentication(auth);
			response.getParams().put("Status", "Login successful");
		} else {
			params.put("status", "missing credentials");
		}
		
		response.setParams(params);
		return response;
	}
}
