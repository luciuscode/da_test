package com.my.myjwttest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jwt.meta.model.core.Model;
import org.eclipse.jwt.meta.model.events.Event;
import org.eclipse.jwt.meta.model.events.EventsFactory;
import org.eclipse.jwt.meta.model.organisations.OrganisationsFactory;
import org.eclipse.jwt.meta.model.processes.Action;
import org.eclipse.jwt.meta.model.processes.Activity;
import org.eclipse.jwt.meta.model.processes.ActivityNode;
import org.eclipse.jwt.meta.model.processes.FinalNode;
import org.eclipse.jwt.meta.model.processes.InitialNode;
import org.eclipse.jwt.meta.model.processes.ProcessesFactory;
import org.eclipse.jwt.we.conf.model.ConfModel;
import org.eclipse.jwt.we.editors.WEEditor;
import org.eclipse.jwt.we.editors.actions.external.WEExternalAction;
import org.eclipse.jwt.we.model.view.Diagram;
import org.eclipse.jwt.we.model.view.ViewFactory;

public class InitialAction extends MyAction {

	public InitialAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {

		initialRes();
		initialWorkflow();
		save();
		refresh();
	}

	/**
	 * initial the workflow.
	 */
	public void initialWorkflow() {
		String initialEvent = "initialEvent";
		String finalEvent = "finalEvent";
		InitialNode initNode=ChangePrimitive.addInitialNode(activity, diagram, initialEvent, 200,
				200);
		Action action =ChangePrimitive.addAction(workflowModel, activity, diagram,
				WorkflowUtil.ACTION_WAITING, 400, 200);
		ChangePrimitive.addEdge(activity, initNode, action);
		FinalNode finaNode =ChangePrimitive.addActivityFinalNode(activity, diagram, finalEvent, 600, 200);
		ChangePrimitive.addEdge(activity, action, finaNode);
		// WorkflowUtil.addInitialNode(activity, "initial");
		// ActivityNode initialNode = WorkflowUtil.getActivityNode(activity,
		// "initial");
		// WorkflowViewUtil.setNodeLayout(diagram, initialNode, 200, 200);

		// WorkflowUtil.addAction(activity, WorkflowUtil.ACTION_WAITING);
		// ActivityNode waitingAction = WorkflowUtil.getActivityNode(activity,
		// initialEvent);
		// WorkflowViewUtil.setNodeLayout(diagram, waitingAction, 400, 200);
		//
		// WorkflowUtil.addEdge(activity, initialNode, waitingAction);
		//
		// WorkflowUtil.addFinalNode(activity, "final");
		// ActivityNode finalNode = WorkflowUtil
		// .getActivityNode(activity, "final");
		// WorkflowViewUtil.setNodeLayout(diagram, finalNode, 500, 200);
		//
		// WorkflowUtil.addEdge(activity, event, finalNode);
	}
}
