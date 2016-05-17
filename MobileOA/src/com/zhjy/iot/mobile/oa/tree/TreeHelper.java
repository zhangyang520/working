package com.zhjy.iot.mobile.oa.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.zhjy.iot.mobile.oa.R;


public class TreeHelper {

	/**
	 * 根据所有节点获取可见节点
	 * 
	 * @param allNodes
	 * @return
	 */
	public static List<TreeNode> filterVisibleNode(List<TreeNode> allNodes) {
		List<TreeNode> visibleNodes = new ArrayList<TreeNode>();
		for (TreeNode node : allNodes) {
			// 如果为根节点，或者上层目录为展开状态
			if (node.isRoot() || node.isParentExpand()) {
				setNodeIcon(node);
				visibleNodes.add(node);
			}
		}
		return visibleNodes;
	}

	/**
	 * 获取排序的所有节点
	 * 
	 * @param datas
	 * @param defaultExpandLevel
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> List<TreeNode> getSortedNodes(List<T> datas,
			int defaultExpandLevel, boolean isHide)
			throws IllegalAccessException, IllegalArgumentException {
		List<TreeNode> sortedNodes = new ArrayList<TreeNode>();
		// 将用户数据转化为List<Node>
		List<TreeNode> nodes = convertData2Nodes(datas, isHide);
		// 拿到根节点
		List<TreeNode> rootNodes = getRootNodes(nodes);
		for(TreeNode rootNode:rootNodes){
			System.out.println("rootNodes:"+rootNode.getName()+"...sortNo:"+rootNode.getSortNo());
			for(TreeNode childNode:rootNode.getChildrenNodes()){
				System.out.println("rootNodes:"+rootNode.getName()+"...sortNo:"+rootNode.getSortNo()+"..childNode:"+childNode.toString());
				for(TreeNode childNode1:childNode.getChildrenNodes()){
					System.out.println("rootNodes:"+rootNode.getName()+"...sortNo:"+rootNode.getSortNo()+"..childNode:"+childNode.getName()+"..childNode1:"+childNode1.toString());
				}
			}
		}
		// 排序以及设置Node间关系
		for (TreeNode node : rootNodes){
			addNode(sortedNodes, node, defaultExpandLevel, 1);
		}
		return sortedNodes;
	}

	/**
	 * 把一个节点上的所有的内容都挂上去
	 */
	private static void addNode(List<TreeNode> nodes, TreeNode node,
			int defaultExpandLeval, int currentLevel) {

		nodes.add(node);
		if (defaultExpandLeval >= currentLevel) {
			node.setExpand(true);
		}

		if (node.isLeaf())
			return;
		for (int i = 0; i < node.getChildrenNodes().size(); i++) {
			addNode(nodes, node.getChildrenNodes().get(i), defaultExpandLeval,
					currentLevel + 1);
		}
	}

	/**
	 * 获取所有的根节点
	 * 
	 * @param nodes
	 * @return
	 */
	public static List<TreeNode> getRootNodes(List<TreeNode> nodes) {
		List<TreeNode> rootNodes = new ArrayList<TreeNode>();
		for (TreeNode node : nodes) {
			if (node.isRoot()) {
				rootNodes.add(node);
			}
		}

		return rootNodes;
	}

	/**
	 * 将泛型datas转换为node
	 * 
	 * @param datas
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> List<TreeNode> convertData2Nodes(List<T> datas, boolean isHide)
			throws IllegalAccessException, IllegalArgumentException {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode node = null;

		for (T t : datas) {
			String id = "-1";
			String pId ="-1";
			String name = null;
			String localNodeId="";
			String localDeptId="";
			int sortNo=-1;
			boolean isDeptFlag=false;
			Class<? extends Object> clazz = t.getClass();
			Field[] declaredFields = clazz.getDeclaredFields();
			/**
			 * 与MyNodeBean实体一一对应
			 */
			for (Field f : declaredFields) {
				if ("nodeId".equals(f.getName())) {
					f.setAccessible(true);
					id = (String) f.get(t);
				}

				if ("deptId".equals(f.getName())) {
					f.setAccessible(true);
					pId = (String) f.get(t);
				}

				if ("nodeName".equals(f.getName())) {
					f.setAccessible(true);
					name = (String) f.get(t);
				}
				if ("localNodeId".equals(f.getName())) {
					f.setAccessible(true);
					localNodeId = (String) f.get(t);
				}
				if ("localDeptId".equals(f.getName())) {
					f.setAccessible(true);
					localDeptId = (String) f.get(t);
				}

				if("idDeptFlag".equals(f.getName())){
					f.setAccessible(true);
					isDeptFlag=(Boolean) f.get(t);
				}
				
				if("sortNo".equals(f.getName())){
					f.setAccessible(true);
					sortNo=(Integer) f.get(t);
				}
				if ("desc".equals(f.getName())) {
					continue;
				}

				if ("length".equals(f.getName())) {
					continue;
				}

				if (id.equals("-1") && pId.equals("-1")  && name == null) {
					break;
				}
			}
			
			System.out.println("name:"+name+"...id:"+id+"..pid:"+pId+"..localDeptId:"+localDeptId+"...localNodeId:"+localNodeId);
			node = new TreeNode(id, pId, name);
			node.setLocalDeptId(localDeptId);
			node.setLocalNodeId(localNodeId);
			node.setHideChecked(isHide);
			node.setIdDeptFlag(isDeptFlag);
			node.setSortNo(sortNo);
			nodes.add(node);
		}

		/**
		 * 比较nodes中的所有节点，分别添加children和parent
		 */
		for (int i = 0; i < nodes.size(); i++) {
			TreeNode n = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); j++) {
				TreeNode m = nodes.get(j);
				if (n.getLocalNodeId().equals(m.getLocalDeptId())) {
					n.getChildrenNodes().add(m);
					m.setParent(n);
				} else if (n.getLocalDeptId().equals(m.getLocalNodeId())) {
					n.setParent(m);
					m.getChildrenNodes().add(n);
				}
			}
		}

		/**
		 * 再重新将子节点分类：没有子节点的节点放在一起
		 * 这个暂时不需要
		 */
//		for (int i = 0; i < nodes.size(); i++) {
//			TreeNode n = nodes.get(i);
//			for (int j = i + 1; j < nodes.size(); j++) {
//				TreeNode m = nodes.get(j);
//				if (n.getLocalNodeId().equals(m.getLocalDeptId())) {
//					n.getChildrenNodes().remove(m);
//					if(m.getChildrenNodes().size()>0){
//						n.getChildrenNodes().add(n.getChildrenNodes().size(),m);
//					}else{
//						n.getChildrenNodes().add(0,m);
//					}
//				} else if (n.getLocalDeptId().equals(m.getLocalNodeId())) {
//					m.getChildrenNodes().remove(n);
//					if(n.getChildrenNodes().size()>0){
//						m.getChildrenNodes().add(m.getChildrenNodes().size(),n);
//					}else{
//						m.getChildrenNodes().add(0,n);
//					}
//					
//				}
//			}
//		}
		for (TreeNode n : nodes) {
			setNodeIcon(n);
		}
		return nodes;
	}

	/**
	 * 设置打开，关闭icon
	 * 
	 * @param node
	 */
	public static void setNodeIcon(TreeNode node) {
		if (node.getChildrenNodes().size() > 0 && node.isExpand()) {
			node.setIcon(R.drawable.tree_expand);
		} else if (node.getChildrenNodes().size() > 0 && !node.isExpand()) {
			node.setIcon(R.drawable.tree_econpand);
		} else
			node.setIcon(-1);
	}

	public static void setNodeChecked(TreeNode node, boolean isChecked) {
		// 自己设置是否选择
		node.setChecked(isChecked);
		/**
		 * 非叶子节点,子节点处理
		 */
		setChildrenNodeChecked(node, isChecked);
		/** 父节点处理 */
		setParentNodeChecked(node);

	}

	/**
	 * 非叶子节点,子节点处理
	 */
	private static void setChildrenNodeChecked(TreeNode node, boolean isChecked) {
		node.setChecked(isChecked);
		if (!node.isLeaf()) {
			for (TreeNode n : node.getChildrenNodes()) {
				// 所有子节点设置是否选择
				setChildrenNodeChecked(n, isChecked);
			}
		}
	}

	/**
	 * 设置父节点选择
	 * 
	 * @param node
	 */
	private static void setParentNodeChecked(TreeNode node) {

		/** 非根节点 */
		if (!node.isRoot()) {
			TreeNode rootNode = node.getParent();
			boolean isAllChecked = true;
			for (TreeNode n : rootNode.getChildrenNodes()) {
				if (!n.isChecked()) {
					isAllChecked = false;
					break;
				}
			}

			if (isAllChecked) {
				rootNode.setChecked(true);
			} else {
				rootNode.setChecked(false);
			}
			setParentNodeChecked(rootNode);
		}
	}

}
