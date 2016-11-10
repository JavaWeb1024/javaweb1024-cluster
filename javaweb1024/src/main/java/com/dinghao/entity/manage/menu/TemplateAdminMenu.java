package com.dinghao.entity.manage.menu;

import java.util.ArrayList;
import java.util.List;

import com.dinghao.entity.manage.menu.Menu;

public class TemplateAdminMenu {
	private String grandparents;
	private List<Parents> parents=new ArrayList<Parents>();
	public String getGrandparents() {
		return grandparents;
	}
	public void setGrandparents(String grandparents) {
		this.grandparents = grandparents;
	}
	public List<Parents> getParents() {
		return parents;
	}
	public void setParents(List<Parents> parents) {
		this.parents = parents;
	}
	
}
