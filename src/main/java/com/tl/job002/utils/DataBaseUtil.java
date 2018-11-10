package com.tl.job002.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * mysql 数据库工具类
 * 
 * @author lihonghao
 * @date 2018年11月10日
 */
public class DataBaseUtil {
	private String driver;
	private String url;
	private String username;
	private String password;
	private Connection dbConn;

	public Connection getDbConn() {
		return dbConn;
	}

	public void setDbConn(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public DataBaseUtil(String driver, String url, String username, String password)
			throws ClassNotFoundException, SQLException {
		super();
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		init();
	}

	public ResultSet getResultSetByStat(String query) throws SQLException {
		PreparedStatement stat = dbConn.prepareStatement(query);
		return stat.executeQuery(query);
	}

	public ResultSet getResultSet(String query) throws SQLException {
		Statement stat = dbConn.createStatement();
		return stat.executeQuery(query);
	}

	public PreparedStatement getPreparedStatement(String sql) throws SQLException {
		return dbConn.prepareStatement(sql);
	}

	public void init() throws SQLException, ClassNotFoundException {
		Class.forName(this.driver);
		dbConn = DriverManager.getConnection(url, username, password);
	}

	public void close() throws SQLException {
		dbConn.close();
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String mysqlDriver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8";
		String userName = "root";
		String password = "haojiayou@";
		DataBaseUtil dbUtil = new DataBaseUtil(mysqlDriver, url, userName, password);
		String sql = "select * from news_item_info_middle";
		// 获取执行sql的对象
		PreparedStatement ps = dbUtil.getPreparedStatement(sql);
		ResultSet rs = dbUtil.getResultSetByStat(sql);
		// 填充sql及执行相关语句
		while(rs.next()){
			String title = rs.getString("title");
			String source_url = rs.getString("source_url");
			String post_time = rs.getString("post_time");
			String insert_time = rs.getString("insert_time");
			System.out.println(title);
			System.out.println(source_url);
			System.out.println(post_time);
			System.out.println(insert_time);
		}
		rs.close();
		dbUtil.close();
	}
}
