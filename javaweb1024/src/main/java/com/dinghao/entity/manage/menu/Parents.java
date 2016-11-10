package com.dinghao.entity.manage.menu;

import java.util.ArrayList;
import java.util.List;

import com.dinghao.entity.manage.menu.Menu;

public class Parents {
	public String getParents() {
		return parents;
	}
	public void setParents(String parents) {
		this.parents = parents;
	}
	public List<Menu> getChildren() {
		return children;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	private String parents;
	private List<Menu> children=new ArrayList<Menu>();
}
