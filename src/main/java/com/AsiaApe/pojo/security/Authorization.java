package com.AsiaApe.pojo.security;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("auth")
public class Authorization {
	
	private long id;
	private String path;
	private List<Role> roles;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return "Authorization [id=" + id + ", path=" + path + ", roles=" + roles + "]";
	}
	
}
