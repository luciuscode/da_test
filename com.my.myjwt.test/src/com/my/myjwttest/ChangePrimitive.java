package com.my.myjwttest;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jwt.meta.model.core.Model;
import org.eclipse.jwt.meta.model.events.Event;
import org.eclipse.jwt.meta.model.organisations.Role;
import org.eclipse.jwt.meta.model.processes.Action;
import org.eclipse.jwt.meta.model.processes.Activity;
import org.eclipse.jwt.meta.model.processes.ActivityEdge;
import org.eclipse.jwt.meta.model.processes.ActivityNode;
import org.eclipse.jwt.meta.model.processes.FinalNode;
import org.eclipse.jwt.meta.model.processes.ForkNode;
import org.eclipse.jwt.meta.model.processes.InitialNode;
import org.eclipse.jwt.meta.model.processes.JoinNode;
import org.eclipse.jwt.we.conf.model.Profile;
import org.eclipse.jwt.we.conf.model.aspects.AspectManager;
import org.eclipse.jwt.we.conf.model.impl.ProfileImpl;
import org.eclipse.jwt.we.model.view.Diagram;
import org.eclipse.jwt.we.model.view.Reference;
import org.eclipse.jwt.we.model.view.ReferenceEdge;
import org.js.model.rbac.AccessControlModel;
import org.js.model.rbac.RBACService;
import org.js.model.rbac.impl.RbacFactoryImpl;
import org.js.model.workflow.ACMConnector;
import org.js.model.workflow.Log;
import org.js.model.workflow.RoleConnector;
import org.js.model.workflow.State;
import org.js.model.workflow.StateEnum;

public class ChangePrimitive {
	public static RBACService rbacService = new RBACService();
	
	public static Action addAction(Model workflowModel, Activity activity,
			Diagram diagram, String name, int actionCoorX, int actionCoorY) {
		// // add related role
		// Role role = addRole(workflowModel, activity, diagram, stageRole,
		// roleName, roleCoorX, roleCoorY);

		// add action
		String actionName = name + " (" + StateEnum.INACTIVE + ") ";
		ActivityNode actNode = WorkflowUtil.addAction(activity, actionName);
		WorkflowViewUtil.setNodeLayout(diagram, actNode, actionCoorX,
				actionCoorY);

		// add aspects for the action
		if (WorkflowConfUtil.containsProfile(workflowModel,
				WorkflowConfUtil.WORKFLOW_PROFILE_NAME)) {
			// add log aspect
			WorkflowConfUtil.addAspectInstance(actNode,
					WorkflowConfUtil.LOG_ASPECT);
			// add state aspect
			State state = (State) WorkflowConfUtil.addAspectInstance(actNode,
					WorkflowConfUtil.STATE_ASPECT);
			WorkflowConfUtil.setState(state, StateEnum.INACTIVE);
		}

		// // add role reference edge
		// addReferenceEdge(workflowModel, activity, diagram, role,
		// (Action) actNode);
		return (Action) actNode;
	}

	public static Action removeAction(Model workflowModel, Activity activity,
			Diagram diagram, String actionName) {
		ActivityNode actNode = WorkflowUtil.getActivityNode(activity,
				actionName);

		// remove log aspect
		WorkflowConfUtil.removeAspectInstance(actNode,
				WorkflowConfUtil.LOG_ASPECT);
		// remove state aspect
		WorkflowConfUtil.removeAspectInstance(actNode,
				WorkflowConfUtil.STATE_ASPECT);
		// remove action
		WorkflowUtil.removeActivityNode(activity, actionName);
		WorkflowViewUtil.removeNodeLayout(diagram, actNode);
		return (Action) actNode;
	}

	public static InitialNode addInitialNode(Activity activity,
			Diagram diagram, String name, int coorX, int coorY) {
		InitialNode initialNode = WorkflowUtil.addInitialNode(activity, name);
		WorkflowViewUtil.setNodeLayout(diagram, initialNode, coorX, coorY);
		return initialNode;
	}

	public static InitialNode removeInitialNode(Activity activity,
			Diagram diagram, String name) {
		ActivityNode actNode = WorkflowUtil.removeActivityNode(activity, name);
		WorkflowViewUtil.removeNodeLayout(diagram, actNode);
		return (InitialNode) actNode;

	}

	public static FinalNode addActivityFinalNode(Activity activity, Diagram diagram,
			String name, int coorX, int coorY) {
		FinalNode finalNode = WorkflowUtil.addFinalNode(activity, name);
		WorkflowViewUtil.setNodeLayout(diagram, finalNode, coorX, coorY);
		return finalNode;
	}

	public static FinalNode removeActivityFinalNode(Activity activity, Diagram diagram,
			String name) {
		ActivityNode actNode = WorkflowUtil.removeActivityNode(activity, name);
		WorkflowViewUtil.removeNodeLayout(diagram, actNode);
		return (FinalNode) actNode;
	}
	public static ForkNode addForkNode(Activity activity, Diagram diagram,
			int coorX, int coorY) {
		ForkNode forkNode = WorkflowUtil.addForkNode(activity);
		WorkflowViewUtil.setNodeLayout(diagram, forkNode, coorX, coorY);
		return (ForkNode) forkNode;
	}

	public static ForkNode removeForkNode(Activity activity, Diagram diagram,
			ForkNode forkNode) {
		ActivityNode actNode = WorkflowUtil.removeActivityNode(activity,
				forkNode);
		WorkflowViewUtil.removeNodeLayout(diagram, actNode);
		return (ForkNode) actNode;
	}

	public static JoinNode addJoinNode(Activity activity, Diagram diagram,
			int coorX, int coorY) {
		JoinNode joinNode = WorkflowUtil.addJoinNode(activity);
		WorkflowViewUtil.setNodeLayout(diagram, joinNode, coorX, coorY);
		return (JoinNode) joinNode;
	}

	public static JoinNode removeJoinNode(Activity activity, Diagram diagram,
			JoinNode joinNode) {
		ActivityNode actNode = WorkflowUtil.removeActivityNode(activity,
				joinNode);
		WorkflowViewUtil.removeNodeLayout(diagram, actNode);
		return (JoinNode) actNode;
	}

	public static ActivityEdge addEdge(Activity activity, ActivityNode source,
			ActivityNode target) {
		return WorkflowUtil.addEdge(activity, source, target);
	}

	public static ActivityEdge removeEdge(Activity activity,
			ActivityNode source, ActivityNode target) {
		return WorkflowUtil.removeEdge(activity, source, target);
	}

	/**
	 * we use event in jwt as final flow node in activity diagram
	 * 
	 * @param activity
	 * @param diagram
	 * @param name
	 * @param coorX
	 * @param coorY
	 * @return
	 */
	public static Event addFlowFinalNode(Activity activity, Diagram diagram,
			String name, int coorX, int coorY) {
		Event event = WorkflowUtil.addEvent(activity, name);
		WorkflowViewUtil.setNodeLayout(diagram, event, coorX, coorY);
		return event;
	}

	public static Event removeFlowFinalNode(Activity activity, Diagram diagram,
			String name) {
		ActivityNode actNode = WorkflowUtil.removeActivityNode(activity, name);
		WorkflowViewUtil.removeNodeLayout(diagram, actNode);
		return (Event) actNode;
	}

	public static Role addRole(Model workflowModel, Activity activity,
			Diagram diagram, org.js.model.rbac.Role roleType, String name,
			int roleCoorX, int roleCoorY) {
		// add role
		Role role = WorkflowUtil.addRole(workflowModel, name);
		WorkflowViewUtil.setRoleLayout(diagram, activity, role, roleCoorX,
				roleCoorY);

		// add role aspect
		if (WorkflowConfUtil.containsProfile(workflowModel,
				WorkflowConfUtil.WORKFLOW_PROFILE_NAME)) {
			ACMConnector acmconnector = (ACMConnector) WorkflowConfUtil
					.getAspectInstance(workflowModel,
							WorkflowConfUtil.ACM_ASPECT);
			// add rbac role into the rbac model
			AccessControlModel acm = (AccessControlModel) acmconnector
					.getAcmref();
			RoleConnector roleConnector = (RoleConnector) WorkflowConfUtil
					.addAspectInstance(role, WorkflowConfUtil.ROLE_ASPECT);
			org.js.model.rbac.Role rbacRole = RbacFactoryImpl.eINSTANCE
					.createRole();
//			rbacService.getParentRoles(rbacRole).add(stageRole);
			rbacRole.getParentRoles().add(roleType);
			rbacRole.setName(name);
			rbacRole.setId(name);
			acm.getRoles().add(rbacRole);
			try {
				acm.eResource().save(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			WorkflowConfUtil.setRoleRef(roleConnector, rbacRole);
		}
		return role;
	}
	public static Role removeRole(Model workflowModel, Activity activity,
			Diagram diagram, String roleName) {
		Role role = WorkflowUtil.getRole(workflowModel, roleName);
		// remove role aspect
		if (WorkflowConfUtil.containsProfile(workflowModel,
				WorkflowConfUtil.WORKFLOW_PROFILE_NAME)) {
			WorkflowConfUtil.removeAspectInstance(role,
					WorkflowConfUtil.ROLE_ASPECT);
			//remove rbac role from rbac model
			ACMConnector acmconnector = (ACMConnector) WorkflowConfUtil
					.getAspectInstance(workflowModel,
							WorkflowConfUtil.ACM_ASPECT);
			AccessControlModel acm = (AccessControlModel) acmconnector
					.getAcmref();
			acm.getRoles().remove(getRBACRole(acm,roleName));
		}
		// remove role
		WorkflowViewUtil.removeRoleLayout(diagram, role);
		WorkflowUtil.removeRole(workflowModel, roleName);
		// remove the related actions
//		removeAction(workflowModel, activity, diagram, roleName);
		return role;
	}
	public static ReferenceEdge addRoleActionRef(Model workflowModel, Activity activity,
			Diagram diagram, Role role, Action action){
		return WorkflowViewUtil.setReferenceEdge(diagram, activity, role,
				action);
	}
	public static ReferenceEdge removeRoleActionRef(Diagram diagram, Role role,
			Action action) {
		return WorkflowViewUtil.removeReferenceEdge(diagram, role, action);
	}
	
	public static org.js.model.rbac.Role getRBACRole(AccessControlModel acm, String name){
		EList<org.js.model.rbac.Role> roles = acm.getRoles();
		for(org.js.model.rbac.Role role:roles){
			if(role.getId().equals(name)){
				return role;
			}
		}
		return null;
	}
}
