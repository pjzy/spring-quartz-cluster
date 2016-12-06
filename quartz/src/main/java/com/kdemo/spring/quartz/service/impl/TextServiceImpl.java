package com.kdemo.spring.quartz.service.impl;

import org.springframework.stereotype.Service;

import com.kdemo.spring.quartz.service.TextService;

@Service
public class TextServiceImpl implements TextService {

	@Override
	public void print() {
		System.out.println("Hello");
		
	}

}
