package com.tl.job002.ui;

import java.util.List;

import com.tl.job002.utils.StaticValue;

/**
 * 该类作用为用户接口的管理类，包括种子添加接口 种子添加的不同方式和来源
 * @author lihonghao
 * @date 2018年11月6日
 */
public class UIManager {
	public String getRootUrlByDirect(){
		return "http://news.youth.cn/gn/";
	}
	public String getRootUrlByStaticValue(){
		return StaticValue.rootUrl;
	}
	public List<String> getRootUrlBySeedFileForClassPath(String fileName){
		return null;
	}
}
