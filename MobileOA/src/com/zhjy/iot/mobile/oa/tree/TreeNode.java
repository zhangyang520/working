package com.zhjy.iot.mobile.oa.tree;

import java.util.List;

import com.zhjy.iot.mobile.oa.utils.SortArrayList;
import com.zhjy.iot.mobile.oa.utils.SortArrayList.ListAddListener;

public class TreeNode {
	/**
	 * 节点id
	 */
	private String nodeId;
	/**
	 * 父节点id
	 */
	private String deptId;
	private String localNodeId="";
	private String localDeptId="";
	/**
	 * 是否展开
	 */
	private boolean isExpand = false;
	private boolean isChecked = false;
	private boolean isHideChecked = true;
	private boolean idDeptFlag=false;
	private int sortNo;
	
	/**
	 * 节点名字
	 */
	private String nodeName;
	/**
	 * 节点级别
	 */
	private int level;
	/**
	 * 节点展示图标
	 */
	private int icon;
	/**
	 * 节点所含的子节点
	 */
	private SortArrayList<TreeNode> childrenNodes = new SortArrayList<TreeNode>();
	/**
	 * 节点的父节点
	 */
	private TreeNode parent;

	public TreeNode() {
		childrenNodes.setListAddListener(new ListAddListener<TreeNode>() {

			@Override
			public int processAddList(SortArrayList<TreeNode> datas,
					TreeNode data){
				System.out.println("childrenNodes processAddList ....:"+datas.toString());
			    int targetSortNum=data.getSortNo();
			    if(datas.size()==1){
			    	//如果总的个数为1
			    	if(datas.get(0).getSortNo()<targetSortNum){
			    		return -1;
			    	}
			    	return 0;
			    }else if(datas.size()>1){
			    	//如果总的个数>1
			    	for(int i=0;i<datas.size()-1;++i){
						if(datas.get(i).getSortNo()>targetSortNum){
							return i;
						}else if(targetSortNum>=datas.get(i).getSortNo() && 
											targetSortNum<=datas.get(i+1).getSortNo()){
							return i+1;
						}
					}
			    }
				return -1;
			}
		});
	}

	
	public TreeNode(String nodeId, String deptId, String nodeName) {
		this();
		this.nodeId = nodeId;
		this.deptId = deptId;
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


	public String getNodeName() {
		return nodeName;
	}


	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	public boolean isExpand() {
		return isExpand;
	}

	/**
	 * 当父节点收起，其子节点也收起
	 * @param isExpand
	 */
	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
		if (!isExpand) {

			for (TreeNode node : childrenNodes) {
				node.setExpand(isExpand);
			}
		}
	}

	public String getName() {
		return nodeName;
	}

	public void setName(String name) {
		this.nodeName = name;
	}

	public int getLevel() {
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public SortArrayList<TreeNode> getChildrenNodes() {
		return childrenNodes;
	}

	public void setChildrenNodes(SortArrayList<TreeNode> childrenNodes) {
		this.childrenNodes = childrenNodes;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	/**
	 * 判断是否是根节点
	 * 
	 * @return
	 */
	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * 判断是否是叶子节点
	 * 
	 * @return
	 */
	public boolean isLeaf() {
		return childrenNodes.size() == 0;
	}
	

	/**
	 * 判断父节点是否展开
	 * 
	 * @return
	 */
	public boolean isParentExpand()
	{
		if (parent == null)
			return false;
		return parent.isExpand();
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isHideChecked() {
		return isHideChecked;
	}

	public void setHideChecked(boolean isHideChecked) {
		this.isHideChecked = isHideChecked;
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


	/**
	 * @return the idDeptFlag
	 */
	public boolean isIdDeptFlag() {
		return idDeptFlag;
	}


	/**
	 * @param idDeptFlag the idDeptFlag to set
	 */
	public void setIdDeptFlag(boolean idDeptFlag) {
		this.idDeptFlag = idDeptFlag;
	}

	

	public int getSortNo() {
		return sortNo;
	}


	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}


	@Override
	public String toString() {
		return "TreeNode [nodeId=" + nodeId + ", deptId=" + deptId
				+ ", sortNo=" + sortNo + ", level=" + level+"..nodeName:"+nodeName
				+ ", childrenNodes=" + childrenNodes + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
}
