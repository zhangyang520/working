package com.zhjy.iot.mobile.oa.protocol;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import com.zhjy.iot.mobile.oa.Inner.BaseProtocol;
import com.zhjy.iot.mobile.oa.dao.NodeDao;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
/**
 * 
 * @项目名：MoblieOA
 * @类名称：同步数据的协议
 * @类描述：进行解析保存所有用户数据的
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-5-12 上午11:34:39
 * @version
 * 
 */
public class SynchronizeDataProtocol extends BaseProtocol<List<Node>> {

	@Override
	protected List<Node> parseJson(String jsonStr) throws JsonParseException,
			ContentException {
		return null;
	}

	@Override
	protected List<Node> parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		
		System.out.println("");
		if(soapObject.hasProperty("status") && "0".equals(soapObject.getProperty("status").toString())){
			if(soapObject.hasProperty("msg")){
				throw new ContentException(soapObject.getProperty("msg").toString());
			}else{
				throw new ContentException("数据传输有误!");
			}
		}else{
			//进行解析后台的同步的数据
			Object data=null;
			List<Node> deptNodes=new ArrayList<Node>();
			List<Node> peopleNodes=new ArrayList<Node>(); 
			String status=soapObject.getProperty("status").toString();
			String msg=soapObject.getProperty("msg").toString();
			System.out.println("status:"+status+"...msg:"+msg);
			//首先进行遍历所有的集合数据
			for(int i=0;i<soapObject.getPropertyCount();++i){
				//进行循环
				data=soapObject.getProperty(i);
				if(data instanceof SoapPrimitive){
					
				}else if(data instanceof SoapObject){
					if(((SoapObject) data).hasProperty("msg")){
						
					}else if(((SoapObject) data).hasProperty("empid")){
						Node peopleNode=null;
						//如果是人员
						if(!((SoapObject) data).hasProperty("empname") || !((SoapObject) data).hasProperty("orgid")){
							throw new ContentException("数据传输有误!");
						}else{
							try {
							    Double.parseDouble(((SoapObject) data).getProperty("orgid").toString());
							    peopleNode=new Node();
								peopleNode.setNodeId(((SoapObject) data).getProperty("empid").toString());
								peopleNode.setNodeName(((SoapObject) data).getProperty("empname").toString());
								peopleNode.setDeptId(((SoapObject) data).getProperty("orgid").toString());
								peopleNode.setSortNo(Integer.valueOf(((SoapObject) data).getProperty("sortno").toString()));
								//进行设置localNodeId
								peopleNode.setLocalNodeId(i+"");
								peopleNode.setIdDeptFlag(false);
								peopleNodes.add(peopleNode);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				   else if(((SoapObject) data).hasProperty("orgid")){
							//如果是部门
							Node deptNode=null;
							//如果是人员
							if(!((SoapObject) data).hasProperty("parentorgid") || !((SoapObject) data).hasProperty("orgname")){
								throw new ContentException("数据传输有误!");
							}else{
								 try {
									Double.parseDouble(((SoapObject) data).getProperty("parentorgid").toString());
									deptNode=new Node();
									deptNode.setNodeId(((SoapObject) data).getProperty("orgid").toString());
									deptNode.setNodeName(((SoapObject) data).getProperty("orgname").toString());
									deptNode.setDeptId(((SoapObject) data).getProperty("parentorgid").toString());
									deptNode.setSortNo(Integer.valueOf(((SoapObject) data).getProperty("sortno").toString()));
									//进行设置localNodeId
									deptNode.setLocalNodeId(i+"");
									deptNode.setIdDeptFlag(true);
									deptNodes.add(deptNode);
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									deptNode=new Node();
									deptNode.setNodeId(((SoapObject) data).getProperty("orgid").toString());
									deptNode.setNodeName(((SoapObject) data).getProperty("orgname").toString());
									deptNode.setDeptId(MobileOAConstant.DEFAULT_DEPT_STRING);
									deptNode.setSortNo(Integer.valueOf(((SoapObject) data).getProperty("sortno").toString()));
//									System.out.println("SynchronizeDataProtocol...name:"+deptNode.getNodeName()+
//																		"sortno:"+((SoapObject) data).getProperty("sortno").toString());
									//进行设置localNodeId
									deptNode.setLocalNodeId(i+"");
									deptNode.setIdDeptFlag(true);
									deptNodes.add(deptNode);
								}
							}
						}
				}else{
					throw new ContentException("数据传输有误!");
				}
			}
				
			//循环之后:进行设置本地的关系
			int parentCount=deptNodes.size();
			
			//进行遍历父节点,进行设置各自的父子关系
			Node parentNode=null;
			System.out.println("parentCount:"+parentCount);
			for(int i=0;i<parentCount;++i ){
				parentNode=deptNodes.get(i);
				System.out.println("i:"+i);
//				parentNode.setLocalNodeId(i+"");
				for(int j=0;j<parentCount;++j){
//					System.out.println("parentNode.getDeptId():"+parentNode.getDeptId()+//
//									"........deptNodes.get(j).getNodeId():"+deptNodes.get(j).getNodeId());
					if(parentNode.getDeptId().equals(deptNodes.get(j).getNodeId()) 
										&& !parentNode.getNodeId().equals(deptNodes.get(j).getNodeId())){
//						System.out.println("name:"+parentNode.getNodeName()+//
//											"........deptNodes.get(j).getNodeId():"+deptNodes.get(j).getNodeId());
//						parentNode.setDeptId(deptNodes.get(j).getNodeId());
						parentNode.setLocalDeptId(deptNodes.get(j).getLocalNodeId());
						break;
					}
				}
			}
			
			//进行遍历子节点
			Node peopleNode=null;
			for(int i=0;i<peopleNodes.size();++i){
				peopleNode=peopleNodes.get(i);
//				peopleNode.setLocalNodeId((i+parentCount)+"");
				//进行遍历父节点
				for(int j=0;j<parentCount;++j){
					if(deptNodes.get(j).getNodeId().equals(peopleNode.getDeptId())){
//						peopleNode.setDeptId(deptNodes.get(j).getNodeId());
						peopleNode.setLocalDeptId(deptNodes.get(j).getLocalNodeId());
						break;
					}
				}
			}
//			System.out.println("SynchronizeDataProtocol parseJson toString:"+peopleNodes.toString());
			deptNodes.addAll(peopleNodes);
			//进行保存到数据库中
//			System.out.println("SynchronizeDataProtocol 同步数据成功..............");
			NodeDao.getInstance().saveNodeList(deptNodes);
			return deptNodes;
			}
		}
}
