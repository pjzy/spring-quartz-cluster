package com.kdemo.spring.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdemo.spring.quartz.service.TextService;

@Service
@DisallowConcurrentExecution
public class Job1 implements Job {
	
	@Autowired
	private TextService textService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		textService.print();
		
		try {
			Thread.sleep(120_000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End");
	}
	


}
