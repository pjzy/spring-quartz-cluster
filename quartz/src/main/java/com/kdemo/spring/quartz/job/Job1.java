package com.kdemo.spring.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdemo.spring.quartz.service.TextService;

@Service
@DisallowConcurrentExecution
public class Job1 implements Job {
	private static final Logger logger = LoggerFactory.getLogger(Job1.class);
	
	@Autowired
	private TextService textService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		textService.print();
		
		try {
			Thread.sleep(60_000);
		} catch (InterruptedException e) {
			logger.error("Cann't process thread waiting", e);
		}
		System.out.println("End");
	}
	


}
