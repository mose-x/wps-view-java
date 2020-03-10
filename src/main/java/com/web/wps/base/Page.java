package com.web.wps.base;

import lombok.Data;

@Data
public class Page {

	private int page = 1;
	private int size = 5;

	public Page(){}

	public Page(int page, int size){
		this.page = page;
		this.size = size;
	}
}
