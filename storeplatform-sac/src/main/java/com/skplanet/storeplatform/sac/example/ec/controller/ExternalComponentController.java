package com.skplanet.storeplatform.sac.example.ec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test/external")
public class ExternalComponentController {

	@RequestMapping(value = "/searchUserPost", method = RequestMethod.POST)
	@ResponseBody
	public User searchUserPost(@RequestBody User user) {

		return user;
	}

	@RequestMapping(value = "/searchUserGet", method = RequestMethod.GET)
	@ResponseBody
	public User searchUserGet(@RequestParam Long userId) {
		User user = new User();

		user.setId(userId);

		return user;
	}

	@RequestMapping(value = "/searchUserGetObject", method = RequestMethod.GET)
	@ResponseBody
	public User searchUserGetObject(User user) {

		return user;
	}

}
