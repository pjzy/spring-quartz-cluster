package com.kdemo.spring.quartz.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdemo.spring.quartz.dto.JobDTO;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

	@Autowired
	private Scheduler scheduler;

	@GetMapping
	public List<JobDTO> getJobs() throws SchedulerException {
		List<JobDTO> jobs = new ArrayList<>();
		

		for (String groupName : scheduler.getJobGroupNames()) {

			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

				String jobName = jobKey.getName();
				String jobGroup = jobKey.getGroup();

				// get job's trigger
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				Trigger trigger = triggers.get(0);
				
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
}
