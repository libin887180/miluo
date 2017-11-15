package com.zhongdi.miluo.model;

import java.io.Serializable;

/**
 * 更新属性对象 
 *
 */
public class Update implements Serializable {


	/**
	 * version_name : V1.2
	 * version_code : 2
	 * force_upgrade : 1
	 * add_time : 2017-11-13
	 * content : 系统升级测试2
	 * url : http:baidu.com
	 */

	private String version_name;
	private int version_code;
	private int force_upgrade;
	private String add_time;
	private String content;
	private String url;

	public String getVersion_name() {
		return version_name;
	}

	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}

	public int getVersion_code() {
		return version_code;
	}

	public void setVersion_code(int version_code) {
		this.version_code = version_code;
	}

	public int getForce_upgrade() {
		return force_upgrade;
	}

	public void setForce_upgrade(int force_upgrade) {
		this.force_upgrade = force_upgrade;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
