package com.zhjy.iot.mobile.oa.entity;

import com.lidroid.xutils.db.annotation.Finder;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;

/**
 * 
 * @项目名：MobileOA 
 * @类名称：Node   
 * @类描述：   单位树实体类
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-25 下午2:40:22  
 * @version    
 *
 */
@Table(name="node")
public class Node extends EntityBase{

	private String nodeName;//节点名称
	@Unique
	private String nodeId;//节点Id   这个真实的后台数据的id;
	private String deptId;//前置部门Id  这个真实的后台数据的id;
	private boolean idDeptFlag;//是否是部门标示（true是部门类型，false是人员）
	
	private String localNodeId=""; //这个是本地标识的id
	private String localDeptId=""; //这个是本地标识的副部们的标识
	
	private int sortNo;//这个是排序的子段
	@Finder(targetColumn="localNodeId",valueColumn="localDeptId")
	private Node parent;
	
	public Node(){
		super();
	}

	public Node(String nodeName, String nodeId, String deptId,
			boolean idDeptFlag) {
		super();
		this.nodeName = nodeName;
		this.nodeId = nodeId;
		this.deptId = deptId;
		this.idDeptFlag = idDeptFlag;
	}

	
	public Node(String nodeName, String nodeId, String deptId) {
		super();
		this.nodeName = nodeName;
		this.nodeId = nodeId;
		this.deptId = deptId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public boolean isIdDeptFlag() {
		return idDeptFlag;
	}

	public void setIdDeptFlag(boolean idDeptFlag) {
		this.idDeptFlag = idDeptFlag;
	}

	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	
	
	/**
	 * @return the localNodeId
	 */
	public String getLocalNodeId() {
		return localNodeId;
	}

	/**
	 * @param localNodeId the localNodeId to set
	 */
	public void setLocalNodeId(String localNodeId) {
		this.localNodeId = localNodeId;
	}

	/**
	 * @return the localDeptId
	 */
	public String getLocalDeptId() {
		return localDeptId;
	}

	/**
	 * @param localDeptId the localDeptId to set
	 */
	public void setLocalDeptId(String localDeptId) {
		this.localDeptId = localDeptId;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node [nodeName=" + nodeName + ", nodeId=" + nodeId
				+ ", deptId=" + deptId + ", localNodeId=" + localNodeId
				+ ", localDeptId=" + localDeptId + "]";
	}

	
	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		return true;
	}
}
