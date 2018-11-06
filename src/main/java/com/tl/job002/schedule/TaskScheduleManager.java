package com.tl.job002.schedule;

import java.util.LinkedList;
import java.util.List;

import com.tl.job002.pojos.UrlTaskPojo;

/**
 * 负责任务的调度,决定什么任务先被采集,什么任务后被采集
 * 
 * @author lihonghao
 * @date 2018年11月6日
 */
public class TaskScheduleManager {
	public static LinkedList<UrlTaskPojo> todoTaskPojoList = new LinkedList<UrlTaskPojo>();
	public static LinkedList<UrlTaskPojo> doneTaskPojoList = new LinkedList<UrlTaskPojo>();

	public static void addUrlPojoList(List<UrlTaskPojo> todoAddTaskList) {
		todoTaskPojoList.addAll(todoAddTaskList);
	}

	public static void removeUrlTaskPojoList(List<UrlTaskPojo> todoRemoveTaskList) {
		todoTaskPojoList.removeAll(todoRemoveTaskList);
	}

	public static void addOneUrlPojo(UrlTaskPojo todoAddTask) {
		todoTaskPojoList.add(todoAddTask);
	}

	public static void addDoneUrlPojo(UrlTaskPojo doneAddTask) {
		doneTaskPojoList.add(doneAddTask);
	}

	public static void removeOneUrlTaskPojo(UrlTaskPojo todoRemoveTask) {
		todoTaskPojoList.remove(todoRemoveTask);
	}

	public static UrlTaskPojo take() {
		UrlTaskPojo taskPojo = todoTaskPojoList.pollFirst();
		return taskPojo;
	}
}
