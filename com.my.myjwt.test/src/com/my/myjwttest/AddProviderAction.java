package com.my.myjwttest;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jwt.we.editors.actions.external.WEExternalAction;
import org.js.model.rbac.AccessControlModel;
import org.js.model.workflow.ACMConnector;

public class AddProviderAction extends MyAction {

	public AddProviderAction() {
	}

	@Override
	public ImageDescriptor getImage() {
		return null;
	}

	@Override
	public void run() {
		initialRes();
		addProvider();
		save();
		refresh();
	}

	public void addProvider() {
		ACMConnector acmconnector = (ACMConnector) WorkflowConfUtil
				.getAspectInstance(workflowModel, WorkflowConfUtil.ACM_ASPECT);
		AccessControlModel acm = (AccessControlModel) acmconnector.getAcmref();

		ChangePrimitive.addRole(workflowModel, activity, diagram,
				ChangePrimitive.getRBACRole(acm, "role1"), "provider1", 220,
				200);
		ChangePrimitive.removeEdge(activity, WorkflowUtil.getActivityNode(
				activity, "initialEvent"), WorkflowUtil.getActivityNode(
				activity, WorkflowUtil.ACTION_WAITING));
		ChangePrimitive.addEdge(activity,
				WorkflowUtil.getActivityNode(activity, "initialEvent"),
				WorkflowUtil.getActivityNode(activity, "provider1"));
		ChangePrimitive.addEdge(activity, WorkflowUtil.getActivityNode(
				activity, "provider1"), WorkflowUtil.getActivityNode(activity,
				WorkflowUtil.ACTION_WAITING));
	}
}
