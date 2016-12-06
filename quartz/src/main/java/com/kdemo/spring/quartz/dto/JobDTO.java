package com.kdemo.spring.quartz.dto;

import java.util.Date;

import org.quartz.Trigger.TriggerState;

public class JobDTO {
	private String jobName;
	private String jobGroup;
	private Date nextFireTime;
	private Date previousFireTime;
	private String calendarName;
	private String description;
	private Date endTime;
	private Date finalFireTime;
	private int misfireInstruction;
	private int priority;
	private Date startTime;
	private TriggerState triggerState;
	private String type;
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public Date getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public Date getPreviousFireTime() {
		return previousFireTime;
	}
	public void setPreviousFireTime(Date previousFireTime) {
		this.previousFireTime = previousFireTime;
	}
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getFinalFireTime() {
		return finalFireTime;
	}
	public void setFinalFireTime(Date finalFireTime) {
		this.finalFireTime = finalFireTime;
	}
	public int getMisfireInstruction() {
		return misfireInstruction;
	}
	public void setMisfireInstruction(int misfireInstruction) {
		this.misfireInstruction = misfireInstruction;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public TriggerState getTriggerState() {
		return triggerState;
	}
	public void setTriggerState(TriggerState triggerState) {
		this.triggerState = triggerState;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "JobDTO [jobName=" + jobName + ", jobGroup=" + jobGroup + ", nextFireTime=" + nextFireTime
				+ ", previousFireTime=" + previousFireTime + ", calendarName=" + calendarName + ", description="
				+ description + ", endTime=" + endTime + ", finalFireTime=" + finalFireTime + ", misfireInstruction="
				+ misfireInstruction + ", priority=" + priority + ", startTime=" + startTime + ", triggerState="
				+ triggerState + ", type=" + type + "]";
	}
	
}