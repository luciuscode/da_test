package com.my.myjwttest;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jwt.meta.model.core.Model;
import org.eclipse.jwt.meta.model.core.PackageableElement;
import org.eclipse.jwt.meta.model.events.Event;
import org.eclipse.jwt.meta.model.events.EventsFactory;
import org.eclipse.jwt.meta.model.organisations.OrganisationsFactory;
import org.eclipse.jwt.meta.model.organisations.Role;
import org.eclipse.jwt.meta.model.processes.Action;
import org.eclipse.jwt.meta.model.processes.Activity;
import org.eclipse.jwt.meta.model.processes.ActivityEdge;
import org.eclipse.jwt.meta.model.processes.ActivityNode;
import org.eclipse.jwt.meta.model.processes.FinalNode;
import org.eclipse.jwt.meta.model.processes.ForkNode;
import org.eclipse.jwt.meta.model.processes.InitialNode;
import org.eclipse.jwt.meta.model.processes.JoinNode;
import org.eclipse.jwt.meta.model.processes.ProcessesFactory;

public class WorkflowUtil {

	public static final String WORKFLOW_EXTENSION = "workflow";
	public static final String WORKFLOW_VIEW_EXTENSION = "workflow_view";
	public static final String WORKFLOW_CONF_EXTENSION = "workflow_conf";
	public static final String INITIAL_NODE_NAME = "InitialNode";
	public static final String FINAL_NODE_NAME = "FinalNode";

	public static ProcessesFactory processFactory = ProcessesFactory.eINSTANCE;
	public static EventsFactory eventsFactory = EventsFactory.eINSTANCE;
	public static OrganisationsFactory organisatoinFactory = OrganisationsFactory.eINSTANCE;

	/**
	 * get the workflow view resource for the given workflow resource.
	 * 
	 * @param workflowResource
	 * @return workflow view resource
	 */
	public static Resource getWorkflowViewReousrce(Resource workflowResource) {
		URI workflowViewUri = workflowResource.getURI().trimFileExtension()
				.appendFileExtension(WORKFLOW_VIEW_EXTENSION);
		ResourceSet resourceSet = new ResourceSetImpl();
		return resourceSet.getResource(workflowViewUri, true);
	}

	/**
	 * get the workflow configuration resource for the given workflow resource.
	 * 
	 * @param workflowResource
	 * @return workflow configration resource
	 */
	public static Resource getWorkflowConfReousrce(Resource workflowResource) {
		URI workflowConfUri = workflowResource.getURI().trimFileExtension()
				.appendFileExtension(WORKFLOW_CONF_EXTENSION);
		ResourceSet resourceSet = new ResourceSetImpl();
		return resourceSet.getResource(workflowConfUri, true);
	}

	public static Action addAction(Activity activity, String name) {
		Action action = processFactory.createAction();
		if (name == null || name.equals("")) {
			name = "Task" + String.valueOf(getActionList(activity).size());
		}
		action.setName(name);
		activity.getNodes().add(action);
		return action;
	}

	public static Event addEvent(Activity activity, String name) {
		Event event = eventsFactory.createEvent();
		if (name == null || name.equals("")) {
			name = "Event" + String.valueOf(getEventList(activity).size());
		}
		event.setName(name);
		activity.getNodes().add(event);
		return event;
	}
	
	/**
	 * remove activity node (event, action, initial/final node)
	 * @param activity
	 * @param name
	 * @return
	 */
	public static ActivityNode removeActNode(Activity activity, String name){
		ActivityNode actNode = (ActivityNode)getActivityNode(activity, name);
		if(actNode !=null){
			activity.getNodes().remove(actNode);
		}
		return actNode;
	}
	
	public static Role addRole(Model workflowModel, String name) {
		Role role = organisatoinFactory.createRole();
		if (name == null || name.equals("")) {
			name = "Role" + String.valueOf(getRoleList(workflowModel).size());
		}
		role.setName(name);
		workflowModel.getElements().add(role);
		return role;
	}

	public static Role removeRole(Model workflowModel, String name) {
		Role role =getRole(workflowModel, name);
		if (role!=null) {
			workflowModel.getElements().remove(role);
		}
		return role;
	}

	/**
	 * if there is no initial node in the activity, an initial node will be added.
	 * @param activity
	 * @return
	 */
	public static InitialNode addInitialNode(Activity activity) {
		InitialNode initNode = (InitialNode) getActivityNode(activity,
				INITIAL_NODE_NAME);
		if (initNode == null) {
			initNode = processFactory.createInitialNode();
			initNode.setName(INITIAL_NODE_NAME);
			activity.getNodes().add(initNode);
		}
		return initNode;
	}

	public static InitialNode getInitNode(Activity activity) {
		return (InitialNode) getActivityNode(activity, INITIAL_NODE_NAME);
	}

	/**
	 * if there is no final node in the activity, an final node will be added.
	 * @param activity
	 * @return
	 */
	public static FinalNode addFinalNode(Activity activity) {
		FinalNode finalNode = (FinalNode) getActivityNode(activity,
				FINAL_NODE_NAME);
		if (finalNode == null) {
			finalNode = processFactory.createFinalNode();
			finalNode.setName(FINAL_NODE_NAME);
			activity.getNodes().add(finalNode);
		}
		return finalNode;
	}

	public static FinalNode getFinalNode(Activity activity) {
		return (FinalNode) getActivityNode(activity, FINAL_NODE_NAME);
	}

	public static ForkNode addForkNode(Activity activity) {
		ForkNode forkNode = processFactory.createForkNode();
		activity.getNodes().add(forkNode);
		return forkNode;
	}

	public static JoinNode addJoinNode(Activity activity) {
		JoinNode joinNode = processFactory.createJoinNode();
		activity.getNodes().add(joinNode);
		return joinNode;
	}

	public static ActivityEdge addEdge(Activity activity, ActivityNode source,
			ActivityNode target) {
		ActivityEdge actEdge = processFactory.createActivityEdge();
		actEdge.setSource(source);
		actEdge.setTarget(target);
		activity.getEdges().add(actEdge);
		return actEdge;
	}

	public static ActivityEdge getEdge(Activity activity, ActivityNode source,
			ActivityNode target) {
		ActivityEdge result=null;
		for (ActivityEdge actiEdge : activity.getEdges()) {
			if (actiEdge.getSource().equals(source)
					&& actiEdge.getTarget().equals(target)) {
				return actiEdge;
			}
		}
		return result;
	}

	public static ActivityEdge removeEdge(Activity activity,
			ActivityNode source, ActivityNode target) {
		ActivityEdge edgeToRemove = null;
		for (ActivityEdge actiEdge : activity.getEdges()) {
			if (actiEdge.getSource().equals(source)
					&& actiEdge.getTarget().equals(target)) {
				edgeToRemove= actiEdge;
			}
		}
		activity.getEdges().remove(edgeToRemove);
		source.getOut().remove(edgeToRemove);
		target.getIn().remove(edgeToRemove);
		return edgeToRemove;
	}

	public static ArrayList<Action> getActionList(Activity activity) {
		EList<ActivityNode> actNodes = activity.getNodes();
		ArrayList<Action> result = new ArrayList<Action>();
		Iterator<ActivityNode> it = actNodes.iterator();
		while (it.hasNext()) {
			ActivityNode actNode = it.next();
			if (actNode instanceof Action) {
				result.add((Action) actNode);
			}
		}
		return result;
	}

	public static ArrayList<Role> getRoleList(Model workflowModel) {
		EList<PackageableElement> pEleList = workflowModel.getElements();
		ArrayList<Role> result = new ArrayList<Role>();
		Iterator<PackageableElement> it = pEleList.iterator();
		while (it.hasNext()) {
			PackageableElement pEle = it.next();
			if (pEle instanceof Role) {
				result.add((Role) pEle);
			}
		}
		return result;
	}

	public static ArrayList<Event> getEventList(Activity activity) {
		EList<ActivityNode> actNodes = activity.getNodes();
		ArrayList<Event> result = new ArrayList<Event>();
		Iterator<ActivityNode> it = actNodes.iterator();
		while (it.hasNext()) {
			ActivityNode actNode = it.next();
			if (actNode instanceof Event) {
				result.add((Event) actNode);
			}
		}
		return result;
	}

	public static Resource getResource(URI uri) {
		ResourceSet resourceSet = new ResourceSetImpl();
		return resourceSet.getResource(uri, true);
	}

	public static Resource getResource(URL fileresource) {
		String path = fileresource.getPath();
		URI uri = URI.createFileURI(path);
		ResourceSet resourceSet = new ResourceSetImpl();
		return resourceSet.getResource(uri, true);
	}

	/**
	 * return role if workflow model contains the role with given name
	 * 
	 * @param activity
	 * @param name
	 * @return
	 */
	public static Role getRole(Model workflowModel, String name) {
		for (PackageableElement role : workflowModel.getElements()) {
			if (role.getName().equals(name)) {
				return (Role)role;
			}
		}
		return null;
	}

	/**
	 * return node if activity contains the node (e.g., action, join/fork node)
	 * with given name
	 * 
	 * @param activity
	 * @param name
	 * @return
	 */
	public static ActivityNode getActivityNode(Activity activity,
			String name) {
		for (ActivityNode actNode : activity.getNodes()) {
			String tempName=actNode.getName();
			if (tempName!=null&&tempName.equals(name))
				return actNode;
		}
		return null;
	}
	
	public static boolean comparatorForRole(Role role1, Role role2){
		return role1.getName().equals(role2.getName());
	} 

	public static boolean comparatorForActNode(ActivityNode actNode1,ActivityNode actNode2){
		return actNode1.getName().equals(actNode2.getName());
	}
	
	/**
	 * check if the activity node name is contained
	 * @param activity
	 * @param name
	 * @return
	 */
	public static boolean containsActNodeName(Activity activity, String name){
		for(ActivityNode actNode:activity.getNodes()){
			if(actNode.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
//	public static Event removeEvent(Activity activity, String name) {
//	Event event = (Event)getActivityNode(activity, name);
//	if(event !=null){
//		activity.getNodes().remove(event);
//	}
//	return event;
//}
	
//	public static Action removeAction(Activity activity, String name) {
//	Action action = (Action)getActivityNode(activity, name);
//	if(action!=null){
//		activity.getNodes().remove(action);
//		action=null;
//	}
//	return action;
//}
}
