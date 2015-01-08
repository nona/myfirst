package org.myfirst.dto;

import java.io.Serializable;

public class ThingDto implements Serializable {

	private static final long serialVersionUID = 6835398256749332836L;
	
	private Long id;
	
	private String tag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	

}
