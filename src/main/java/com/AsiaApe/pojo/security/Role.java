package com.AsiaApe.pojo.security;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("role")
public class Role {
	
	private long id;
	private String roleName;
	private List<Authorization> auths;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<Authorization> getAuths() {
		return auths;
	}
	public void setAuths(List<Authorization> auths) {
		this.auths = auths;
	}
	
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", auths=" + auths + "]";
	}
	
}
