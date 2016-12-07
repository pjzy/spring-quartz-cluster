package com.kdemo.spring.quartz.rest.controller;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdemo.spring.quartz.dto.JobDTO;


@RestController
@RequestMapping("/api/jobs")
public class JobController {
	
	private static final Logger logger = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	private Scheduler scheduler;

	
	/**
	 * Get list of registered jobs
	 * @return
	 * @throws SchedulerException
	 */
	@SuppressWarnings("unchecked")
	@GetMapping
	public List<JobDTO> getJobs() throws SchedulerException {
		List<JobDTO> jobs = new ArrayList<>();
		

		for (String groupName : scheduler.getJobGroupNames()) {

			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

				String jobName = jobKey.getName();
				String jobGroup = jobKey.getGroup();

				// get job's trigger
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			
				Trigger trigger = null;
				if(triggers instanceof LinkedList) {
					trigger = ((LinkedList<Trigger>) triggers).getLast();
				}
				else {
					trigger = triggers.get(0);
				}
				
				String simpleName = trigger.getClass().getSimpleName();
				TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			
				
				JobDTO job = new JobDTO();
				BeanUtils.copyProperties(trigger, job);
				job.setJobName(jobName);
				job.setJobGroup(jobGroup);
				job.setTriggerState(triggerState);
				job.setType(simpleName);
				
				jobs.add(job);
			}

		}
		
		return jobs;
	}
	
	
	/**
	 * Trigger a now now
	 * @param jobGroup
	 * @param jobName
	 * @return
	 */
	@PostMapping("/{jobGroup}/{jobName}/trigger")
	public Boolean fire(@PathVariable String jobGroup, @PathVariable String jobName) {
		boolean isFire = false;
				
		JobKey jobKey = new JobKey(jobName, jobGroup);
		try {
			scheduler.triggerJob(jobKey);
			isFire = true;
		} catch (SchedulerException e) {
			logger.error("");
		};
		
		return isFire;
	}
	
	
	/**
	 * Pause a job
	 * @param jobGroup
	 * @param jobName
	 * @return
	 */
	@PostMapping("/{jobGroup}/{jobName}/deactivate")
	public Boolean pause(@PathVariable String jobGroup, @PathVariable String jobName) {
		boolean isPause = false;
				
		JobKey jobKey = new JobKey(jobName, jobGroup);
		try {
			scheduler.pauseJob(jobKey);
			isPause = true;
		} catch (SchedulerException e) {
			logger.error("");
		};
		
		return isPause;
	}
	
	/**
	 * Resume a job
	 * @param jobGroup
	 * @param jobName
	 * @return
	 */
	@PostMapping("/{jobGroup}/{jobName}/activate")
	public Boolean resume(@PathVariable String jobGroup, @PathVariable String jobName) {
		boolean isResume = false;
				
		JobKey jobKey = new JobKey(jobName, jobGroup);
		try {
			scheduler.resumeJob(jobKey);
			isResume = true;
		} catch (SchedulerException e) {
			logger.error("");
		};
		
		return isResume;
	}
}
