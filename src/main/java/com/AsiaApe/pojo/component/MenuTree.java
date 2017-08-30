package com.AsiaApe.pojo.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Alias("menuTree")
public class MenuTree {
	
	public static final String ROOT = "root";
	public static final int LEAF = 1;
	public static final int NOT_LEAF = 0;
	
	private String id;
	private String menuName;
	private String iconPath;
	private String href;
	private String[] tags;
	private boolean isLeaf;
	private state state = new state();
	private String parentId;
	private int orderId;
	private String authPath;
	private List<MenuTree> childrenNode;
	
	public MenuTree() {
	}
	public MenuTree(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JsonProperty("text")
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	@JsonProperty("icon")
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	@JsonProperty("href")
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	@JsonProperty("tags")
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	@JsonProperty("leaf")
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	@JsonProperty("state")
	public state getState() {
		return state;
	}
	public void setState(state state) {
		this.state = state;
	}
	@JsonIgnore
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@JsonIgnore
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	@JsonIgnore
	public String getAuthPath() {
		return authPath;
	}
	public void setAuthPath(String authPath) {
		this.authPath = authPath;
	}
	@JsonProperty("nodes")
	@JsonInclude(Include.NON_NULL)
	public List<MenuTree> getChildrenNode() {
		return childrenNode;
	}
	public void setChildrenNode(List<MenuTree> childrenNode) {
		this.childrenNode = childrenNode;
	}
	public void addChildNode(MenuTree childNode) {
		if (childrenNode == null) {
			childrenNode = new ArrayList<MenuTree>();
		}
		childrenNode.add(childNode);
	}
	@Override
	public String toString() {
		return "MenuTree [id=" + id + ", menuName=" + menuName + ", iconPath=" + iconPath + ", href=" + href + ", tags="
				+ Arrays.toString(tags) + ", isLeaf=" + isLeaf + ", state=" + state + ", parentId=" + parentId
				+ ", orderId=" + orderId + ", authPath=" + authPath + ", childrenNode=" + childrenNode + "]";
	}
	
	public boolean compareId(String id) {
		return this.id.equals(id);
	}
	public boolean compareId(MenuTree node) {
		return this.id.equals(node.getId());
	}
	@JsonIgnore
	public boolean isRoot() {
		return parentId.equals(ROOT);
	}
	
	public class state {
		private boolean checked;
		private boolean disabled;
		private boolean expanded;
		private boolean selected;
		
		public boolean isChecked() {
			return checked;
		}
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
		public boolean isDisabled() {
			return disabled;
		}
		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}
		public boolean isExpanded() {
			return expanded;
		}
		public void setExpanded(boolean expanded) {
			this.expanded = expanded;
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
		@Override
		public String toString() {
			return "state [checked=" + checked + ", disabled=" + disabled + ", expanded=" + expanded + ", selected="
					+ selected + "]";
		}
	}
	
}
