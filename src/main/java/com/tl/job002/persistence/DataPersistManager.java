package com.tl.job002.persistence;

import java.util.List;

import com.tl.job002.iface.persistence.DataPersistrnceInterface;
import com.tl.job002.pojos.entity.NewsItemEntity;

/**
 * 持久化管理器
 * 
 * @author lihonghao
 * @date 2018年11月10日
 */
public class DataPersistManager {
	public static DataPersistrnceInterface persistrnceInterface = new DataPersist4MysqlImpl();

	public static boolean persist(List<NewsItemEntity> itemList) {
		boolean flag = persistrnceInterface.persist(itemList);
		return flag;
	}
}
