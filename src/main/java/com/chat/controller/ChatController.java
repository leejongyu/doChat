package com.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chat.domain.Users;
import com.chat.service.UserService;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Slf4j
@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping
public class ChatController {

	private final UserService userService;

	@GetMapping("/chat")
	public String GetChat(Model model, String userName) {
		log.info("go chat(userName= {})", userName);

		if (userName.equals("anonymousUser")) {
			return "redirect:/login";
		} else {

			Users user = userService.read(userName);
			if (user == null) {
				return "/mainPage";
			} else {

				model.addAttribute("user", user);
			}
		}

		log.info("ChatController - chat Get()");

		return "/chat";
	}

}
