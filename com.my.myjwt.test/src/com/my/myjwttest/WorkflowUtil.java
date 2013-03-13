package com.my.myjwttest;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jwt.meta.model.core.GraphicalElement;
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
	
//	public static ArrayList<GraphicalElement> modelEleContainer = new ArrayList<GraphicalElement>();

	public static final String WORKFLOW_EXTENSION = "workflow";
	public static final String WORKFLOW_VIEW_EXTENSION = "workflow_view";
	public static final String WORKFLOW_CONF_EXTENSION = "workflow_conf";

	public static ProcessesFactory processFactory = ProcessesFactory.eINSTANCE;
	public static EventsFactory eventsFactory = EventsFactory.eINSTANCE;
	public static OrganisationsFactory orgFactory = OrganisationsFactory.eINSTANCE;

	/**
	 * get the workflow view resource for the given workflow resource.
	 * 
	 * @param workflowResource
	 * @return
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
	 * @return
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

	public static InitialNode addInitialNode(Activity activity) {
		InitialNode initNode = processFactory.createInitialNode();
		activity.getNodes().add(initNode);
		return initNode;
	}

	public static FinalNode addFinalNode(Activity activity) {
		FinalNode finalNode = processFactory.createFinalNode();
		activity.getNodes().add(finalNode);
		return finalNode;
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

	public static Event addEvent(Activity activity, String name){
		Event event = eventsFactory.createEvent();
		if (name == null || name.equals("")) {
			name = "Event" + String.valueOf(getEventList(activity).size());
		}
		event.setName(name);
		activity.getNodes().add(event);
		return event;
	}
	public static ActivityEdge addEdge(Activity activity, ActivityNode source,
			ActivityNode target) {
		ActivityEdge actEdge = processFactory.createActivityEdge();
		actEdge.setSource(source);
		actEdge.setTarget(target);
		activity.getEdges().add(actEdge);
		return actEdge;
	}

	public static Role addRole(Model workflowModel, Action action, String name) {
		Role role =orgFactory.createRole();
		if (name == null || name.equals("")) {
			name = "Role" + String.valueOf(getRoleList(workflowModel).size());
		}
		role.setName(name);
//		action.setPerformedBy(role);
		workflowModel.getElements().add(role);
		
		return role;
	}
	/**
	 * get the action list from the activity model.
	 * 
	 * @param activity
	 * @return
	 */
	public static ArrayList<Action> getActionList(Activity activity) {
		EList actNodes = activity.getNodes();
		ArrayList<Action> result = new ArrayList<Action>();
		Iterator<Action> it = actNodes.iterator();
		while (it.hasNext()) {
			ActivityNode actNode = it.next();
			if (actNode instanceof Action) {
				result.add((Action) actNode);
			}
		}
		return result;
	}

	public static ArrayList<Role> getRoleList(Model workflowModel){
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
	
	public static ArrayList<Event> getEventList(Activity activity){
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

}
