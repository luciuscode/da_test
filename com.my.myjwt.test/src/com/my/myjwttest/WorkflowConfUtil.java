package com.my.myjwttest;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jwt.meta.model.core.Model;
import org.eclipse.jwt.meta.model.processes.Action;
import org.eclipse.jwt.meta.model.processes.Activity;
import org.eclipse.jwt.meta.model.processes.ForkNode;
import org.eclipse.jwt.meta.model.processes.JoinNode;
import org.eclipse.jwt.we.conf.model.Aspect;
import org.eclipse.jwt.we.conf.model.AspectInstance;
import org.eclipse.jwt.we.conf.model.ConfFactory;
import org.eclipse.jwt.we.conf.model.ConfModel;
import org.eclipse.jwt.we.conf.model.Profile;
import org.eclipse.jwt.we.conf.model.aspects.AspectManager;
import org.eclipse.jwt.we.conf.model.aspects.factory.AspectFactory;
import org.js.model.rbac.AccessControlModel;
import org.js.model.rbac.Permission;
import org.js.model.rbac.Role;
import org.js.model.workflow.ACMConnector;
import org.js.model.workflow.ConfSequence;
import org.js.model.workflow.InsertNodesContainer;
import org.js.model.workflow.Log;
import org.js.model.workflow.RoleConnector;
import org.js.model.workflow.State;
import org.js.model.workflow.StateEnum;
import org.js.model.workflow.WorkflowFactory;

public class WorkflowConfUtil {

	public static WorkflowFactory workflowFactory = WorkflowFactory.eINSTANCE;
	public static ConfFactory confFactory = ConfFactory.eINSTANCE;
	public static final String ACM_FILE_EXTENSION = "rbac";
	public static final String WORKFLOW_PROFILE_NAME = "org.js.model.workflow.profile";
	

	public static final String ACM_ASPECT = "org.js.model.workflow.acmaspect";
	public static final String ROLE_ASPECT = "org.js.model.workflow.roleaspect";
	public static final String LOG_ASPECT = "org.js.model.workflow.logaspect";
	public static final String INSERTNODES_ASPECT = "org.js.model.workflow.insertnodesaspect";
	public static final String STATE_ASPECT = "org.js.model.workflow.stateaspect";
	public static final String CONFSEQUENCE_ASPECT = "org.js.model.workflow.confsequenceaspect";

	public static AspectInstance addAspectInstance(EObject modelElement,
			String aspectName) {
		Aspect aspect = AspectManager.INSTANCE.getAspect(modelElement,
				aspectName);
		return AspectManager.INSTANCE.createAndAddAspectInstance(aspect,
				modelElement);
	}

	public static AspectInstance getAspectInstance(EObject modelElement,
			String aspectName) {
		Aspect aspect = AspectManager.INSTANCE.getAspect(modelElement,
				aspectName);
		return AspectManager.INSTANCE.getAspectInstance(modelElement, aspect);
	}

	public static AspectInstance removeAspectInstance(EObject modelElement,
			String aspectName) {
		AspectInstance aspectInstance = getAspectInstance(modelElement,
				aspectName);
		aspectInstance.setTargetModelElement(null);
		AspectManager.INSTANCE.getConfModel(modelElement).getAspectInstances()
				.remove(aspectInstance);
		return aspectInstance;
	}
	
	public static void setACMRef(ACMConnector acmconnector,
			AccessControlModel acm) {
		acmconnector.setAcmref(acm);
	}
	public static void setRoleRef(RoleConnector roleconnector,
			Role role) {
		roleconnector.setRoleref(role);
	}
	public static void addPermissions(Log log,
			List<Permission> permissionList) {
		log.getPermissions().addAll(permissionList);
	}
	public static void addPermission(Log log,
			Permission permission) {
		log.getPermissions().add(permission);
	}
	public static void removePermission(Log log,
			Permission permission){
		log.getPermissions().remove(permission);
	}
	public static void removePermissions(Log log,
			List<Permission> permissionList) {
		log.getPermissions().removeAll(permissionList);
	}

	public static void setState(State state,
			StateEnum stateEnum) {
		state.setState(stateEnum);
	}
	
	public static boolean containsProfile(Model workflowModel, String profileName){
		List<Profile> activeProfiles =AspectManager.INSTANCE.getActivatedProfiles(workflowModel);
		for(Profile profile:activeProfiles){
			if(profile.getName().equals(profileName)){
				return true;
			}
		}
		return false;
	}
	//TODO: ensure if we need the aspects of insertnode and confseq 
//	public static void setConfSequence(ConfSequence confSeq,
//			List<Role> roles) {
//		confSeq.getRole().addAll(roles);
//	}
//	public static void addInsertNodes(InsertNodesContainer inCon,
//	Permission permission) {
//log.getPermissions().add(permission);
//}
	//
	// public static RoleAspect addRoleAspect(ConfModel confModel, Role role) {
	// RoleAspect roleAsp = staAspFactory.createRoleAspect();
	// roleAsp.setId("");
	// roleAsp.setTargetModelElement(role);
	// confModel.getAspectInstances().add(roleAsp);
	// return roleAsp;
	// }
	//
	// public static Log addLogAspect(ConfModel confModel, Action action) {
	// Log log = staAspFactory.createLog();
	// log.setTargetModelElement(action);
	// log.setId("");
	// confModel.getAspectInstances().add(log);
	// return log;
	// }
	//
	// public static InsertNodesAspect addRoleAspect(ConfModel confModel,
	// Activity activity) {
	// InsertNodesAspect insNodesAsp = staAspFactory.createInsertNodesAspect();
	// insNodesAsp.setTargetModelElement(activity);
	// insNodesAsp.setId("");
	// confModel.getAspectInstances().add(insNodesAsp);
	// return insNodesAsp;
	// }
	//
	// public static State addStateAspect(ConfModel confModel, Action action) {
	// State state = staAspFactory.createState();
	// state.setTargetModelElement(action);
	// state.setId("");
	// confModel.getAspectInstances().add(state);
	// return state;
	// }
	//
	// public static void refACM(ACMAspect acmasp, AccessControlModel acm) {
	// acmasp.setAcmref(acm);
	// }
	//
	// public static void refRole(RoleAspect roleasp,
	// org.js.model.rbac.Role rbacRole) {
	// roleasp.setRoleref(rbacRole);
	// }
	//
	// public static void refInsertNodes(InsertNodesAspect insertAsp,
	// InsertNodes insertNodes, Action action, ForkNode forkNode,
	// JoinNode joinNode) {
	// // EList nodesList = insertAsp.getInsertNodes();
	// // for(InsertNodes insertNodes:nodesList){
	// //
	// // }
	// // if(insertAsp.getInsertNodes().c)
	// insertNodes.setAction(action);
	// insertNodes.setForknode(forkNode);
	// insertNodes.setJoinnode(joinNode);
	// insertAsp.getInsertNodes().add(insertNodes);
	// }
}
