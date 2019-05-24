package com.iu.page;

public class Search {
	private String kind;
	private String search;
	
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		if(kind==null) {
			this.kind="title";
		}else if(kind.equals("w")) {
			this.kind="writer";
		}else if(kind.equals("c")) {
			this.kind="contents";
		}else {
			this.kind="title";
		}
	}
	
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		if(search==null) {
			this.search="";
		}else {
			this.search = search;
		}
	}
}
