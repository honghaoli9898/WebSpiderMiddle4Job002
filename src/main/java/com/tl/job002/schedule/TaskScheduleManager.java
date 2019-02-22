package com.tl.job002.schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.tl.job002.monitor.MonitorManager;
import com.tl.job002.persistence.DataPersistManager;
import com.tl.job002.pojos.UrlTaskPojo;
import com.tl.job002.utils.DateUtil;

/**
 * 负责任务的调度,决定什么任务先被采集,什么任务后被采集
 * 
 * @author lihonghao
 * @date 2018年11月6日
 */
public class TaskScheduleManager {
	// 存放已用代理
	public static List<String> proxyIpPoolList = new ArrayList<String>();
	// 存放已用账号集合
	public static List<String> userNameList = new ArrayList<String>();
	public static Logger logger = Logger.getLogger(TaskScheduleManager.class);
	// 存放待采集url的集合
	public static LinkedList<UrlTaskPojo> todoTaskPojoList = new LinkedList<UrlTaskPojo>();
	// 存放已采集url的集合
	public static LinkedList<UrlTaskPojo> doneTaskPojoList = new LinkedList<UrlTaskPojo>();
	// 程序中断后，恢复历史纪录所动用的。新闻实体数据的已采集URL的集合,用于判重和增量采集
	public static Set<String> savedNewsEntityUrlSet = new HashSet<String>();
	// 在static方法块中,将所有需要从数据库中恢复的数据进行查询及恢复
	static {
		recovery();
	}

	public static void recovery() {
		synchronized (savedNewsEntityUrlSet) {
			String sql = "select * from news_item_info_middle";
			ResultSet rs = DataPersistManager.getResultSet(sql);
			try {
				while (rs.next()) {
					String title = rs.getString("title");
					String source_url = rs.getString("source_url");
					savedNewsEntityUrlSet.add(title + source_url);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// 恢复历史的数据
			MonitorManager.setTotalNewsEntityNumber(savedNewsEntityUrlSet
					.size());
			// 恢复当天的数据
			sql = "select COUNT(1) from news_item_info_middle where DATE_FORMAT(insert_time,'%Y-%m-%d')='"
					+ DateUtil.getCurrentDay() + "'";
			ResultSet rsCount = DataPersistManager.getResultSet(sql);
			try {
				if (rsCount.next()) {
					int countValue = rsCount.getInt(1);
					MonitorManager.setTotalCurrentDayEntityNumber(countValue);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					rsCount.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 程序中断后添加url到savedNewsEntityUrlSet
	public static void addSavedNewsEntityUrlSet(String saveUrl) {
		synchronized (savedNewsEntityUrlSet) {
			savedNewsEntityUrlSet.add(saveUrl);
		}
	}

	// 判断采集的url是否在集合里
	public static boolean isInSaveNewsEntityUrlSet(String toSaveUrl) {
		synchronized (savedNewsEntityUrlSet) {
			return savedNewsEntityUrlSet.contains(toSaveUrl);
		}
	}

	// 添加一个集合的url到todoAddTaskList
	public static void addUrlPojoList(List<UrlTaskPojo> todoAddTaskList) {
		todoTaskPojoList.addAll(todoAddTaskList);
	}

	// 从todoTaskPojoList中删除一个集合
	public static void removeUrlTaskPojoList(
			List<UrlTaskPojo> todoRemoveTaskList) {
		todoTaskPojoList.removeAll(todoRemoveTaskList);
	}

	// 添加一个UrlTaskPojo对象到todoTaskPojoList中
	public static void addOneUrlPojo(UrlTaskPojo todoAddTask) {
		todoTaskPojoList.add(todoAddTask);
	}

	// 添加一个UrlTaskPojo对象到doneTaskPojoList中
	public static void addDoneUrlPojo(UrlTaskPojo doneAddTask) {
		doneTaskPojoList.add(doneAddTask);
	}

	// 从todoTaskPojoList中移除一个UrlTaskPojo对象
	public static void removeOneUrlTaskPojo(UrlTaskPojo todoRemoveTask) {
		todoTaskPojoList.remove(todoRemoveTask);
	}

	// 已采集的url大小
	public static int getDoneTaskSize() {
		return doneTaskPojoList.size();
	}

	// 带采集的url大小
	public static int getTodoTaskSize() {
		return todoTaskPojoList.size();
	}

	// 从todoTaskPojoList中得到一个UrlTaskPojo对象
	public static UrlTaskPojo take() {
		synchronized (todoTaskPojoList) {
			UrlTaskPojo taskPojo = todoTaskPojoList.pollFirst();
			return taskPojo;
		}
	}

	// 清空todoTaskPojoList列表
	public static synchronized void cleanTodoTaskList() {
		todoTaskPojoList.clear();
	}

	// 清空userNameList
	public static void cleanUserNameList() {
		userNameList.clear();
	}

	// 添加一个账号到userNameList
	public static void addUserNameList(String userName) {
		userNameList.add(userName);
	}

	// 得到userNameList大小
	public static int getUserNameListSize() {
		return userNameList.size();
	}

	// 判断userNameList是否为空
	public static boolean isNull4UserNameList() {
		return userNameList.isEmpty();
	}

	// 清空proxyIpPoolList
	public static void cleanProxyIpPoolList() {
		proxyIpPoolList.clear();
	}

	// 添加一个账号到proxyIpPoolList
	public static void addProxyIpPoolList(String userName) {
		proxyIpPoolList.add(userName);
	}

	// 得到proxyIpPoolList大小
	public static int getProxyIpPoolListSize() {
		return proxyIpPoolList.size();
	}

	// 判断proxyIpPoolList是否为空
	public static boolean isNull4ProxyIpPoolList() {
		return proxyIpPoolList.isEmpty();
	}
}
