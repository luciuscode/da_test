package com.my.myjwttest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jwt.meta.model.core.Comment;
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
import org.eclipse.jwt.meta.model.processes.impl.ActivityNodeImpl;
import org.eclipse.jwt.we.conf.model.Aspect;
import org.eclipse.jwt.we.conf.model.ConfModel;
import org.eclipse.jwt.we.conf.model.Profile;
import org.eclipse.jwt.we.editors.WEEditor;
import org.eclipse.jwt.we.editors.actions.external.WEExternalAction;
import org.eclipse.jwt.we.misc.views.LayoutDataManager;
import org.eclipse.jwt.we.model.view.Diagram;
import org.eclipse.jwt.we.model.view.LayoutData;
import org.eclipse.jwt.we.model.view.Reference;
import org.eclipse.jwt.we.model.view.ReferenceEdge;
import org.eclipse.jwt.we.model.view.ViewFactory;
import org.eclipse.jwt.we.model.view.impl.DiagramImpl;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;

public class MyAction1 extends WEExternalAction {

	WEEditor workflowEditor;
	Model workflowModel;
	Activity activity;
	Diagram viewModel;
	ConfModel confModel;
	Resource workflowResource;
	Resource workflowViewResource;
	Resource workflowConfResource;

	ProcessesFactory processFactory = ProcessesFactory.eINSTANCE;
	ViewFactory viewFactory = ViewFactory.eINSTANCE;
	EventsFactory eventsFactory = EventsFactory.eINSTANCE;
	OrganisationsFactory orgFactory = OrganisationsFactory.eINSTANCE;

	public MyAction1() {
	}

	@Override
	public ImageDescriptor getImage() {
		return null;
	}

	@Override
	public void run() {

		initialRes();
		clean();
		// save();
		// initialWorkflow();
		// testConf();
		test();
		layout();
		save();
		workflowEditor.refreshPages();
		// printAll();
	}

	/**
	 * initial all of the resource.
	 */
	public void initialRes() {
		workflowEditor = getActiveWEEditor();
		workflowResource = getActiveResource();
		workflowViewResource = WorkflowUtil
				.getWorkflowViewReousrce(workflowResource);
		workflowConfResource = WorkflowUtil
				.getWorkflowConfReousrce(workflowResource);
		workflowModel = (Model) workflowResource.getContents().get(0);

		activity = getActiveActivitySheet().getActivityModel();
		viewModel = workflowEditor.getDiagramData();
		confModel = (ConfModel) workflowConfResource.getContents().get(0);
	}

	/**
	 * clean the worklfow.
	 */
	public void clean() {
		activity.getNodes().clear();
		activity.getEdges().clear();

		workflowModel.getElements().removeAll(
				workflowModel.getElements().subList(1,
						workflowModel.getElements().size()));

		viewModel.getReferenceEdges().clear();
		viewModel.getReferences().clear();
		viewModel.getLayoutData().clear();

		// confModel.getProfiles().clear();
	}

	/**
	 * initial the workflow.
	 */
	public void initialWorkflow() {
	}

	/**
	 * save the workflow.
	 */
	public void save() {
		Map options = new HashMap();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		try {
			workflowModel.eResource().save(options);
			viewModel.eResource().save(options);
			confModel.eResource().save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testConf() {
		Iterator it = confModel.eAllContents();
		Aspect aspect = null;
		while (it.hasNext()) {
			EObject object = (EObject) it.next();
			if (object instanceof Aspect)
				aspect = (Aspect) object;
		}
		// System.out.println("confModel.getProfiles(): "+confModel.getProfiles());
		// System.out.println(confModel.getProfiles().get(0).getAspects());
		// System.out.println(confModel.getProfiles().get(0).getAspects().get(0).getTargetModelElements());
		// System.out.println(confModel.getAspectInstances().get(2).getTargetModelElement());
		// System.out.println(confModel.getAspectInstances().get(1).getTargetModelElement());
		System.out.println(activity);
		System.out.println(workflowModel);
		// System.out.println(workflowModel.getNodes().indexOf(confModel.getAspectInstances().get(0).getTargetModelElement()));
	}

	public void printAll() {
		System.out.println(workflowModel.getElements());
		Iterator it = viewModel.getLayoutData().iterator();
		while (it.hasNext()) {
			LayoutData layoutdata = (LayoutData) it.next();
			// System.out.println(layoutdata.getDescribesElement());
			// System.out.println(((Reference)layoutdata.getDescribesElement()).getContainedIn());
			// System.out.println(((Reference)layoutdata.getDescribesElement()).getReference());
		}
		// System.out.println(workflowModel.getEdges());
		// System.out.println(viewModel.getLayoutData());
		// System.out.println(viewModel.getReferenceEdges());
		// System.out.println(viewModel.getReferences());
		// System.out.println(confModel.getAspectInstances());
		// System.out.println(confModel.getEnrichedModel());
		// System.out.println(((Profile) confModel.eContents().get(0))
		// .getAspects().get(0).getAspectInstanceEType());
	}

	//TODO: set the layout for the workflow after changes
	public void layout() {

	}

	public void test() {
		InitialNode iniNode = addInitialNode(100, 200);
		Action action1 = addAction(200, 200, null);
		addEdge(iniNode, action1);
		ForkNode forkNode1 = addForkNode(300, 200);
		addEdge(action1, forkNode1);
		Action action2 = addAction(400, 200, null);
		addEdge(forkNode1, action2);
		Action action3 = addAction(400, 400, null);
		addEdge(forkNode1, action3);
		addRole(200, 50, action2, null);
		addRole(400, 50, action3, null);
		Action action4 = addAction(500, 200, null);
		Action action5 = addAction(500, 400, null);
		addEdge(action2, action4);
		addEdge(action3, action5);
		JoinNode joinNode1 = addJoinNode(600, 200);
		addEdge(action4, joinNode1);
		addEdge(action5, joinNode1);
		Event event1 = addEvent(700, 200, null);
		addEdge(joinNode1, event1);
		FinalNode finalNode = addFinalNode(800, 200);
		addEdge(event1, finalNode);
	}

	public Action addAction(int coorX, int coorY, String name) {
		Action action = WorkflowUtil.addAction(activity, name);
		WorkflowViewUtil.setNodeLayout(viewModel, action, coorX, coorY);
		return action;
	}

	public InitialNode addInitialNode(int coorX, int coorY) {
		InitialNode initialNode = WorkflowUtil.addInitialNode(activity);
		WorkflowViewUtil.setNodeLayout(viewModel, initialNode, coorX, coorY);
		return initialNode;
	}

	public FinalNode addFinalNode(int coorX, int coorY) {
		FinalNode finalNode = WorkflowUtil.addFinalNode(activity);
		WorkflowViewUtil.setNodeLayout(viewModel, finalNode, coorX, coorY);
		return finalNode;
	}

	public ForkNode addForkNode(int coorX, int coorY) {
		ForkNode forkNode = WorkflowUtil.addForkNode(activity);
		WorkflowViewUtil.setNodeLayout(viewModel, forkNode, coorX, coorY);
		return forkNode;
	}

	public JoinNode addJoinNode(int coorX, int coorY) {
		JoinNode joinNode = WorkflowUtil.addJoinNode(activity);
		WorkflowViewUtil.setNodeLayout(viewModel, joinNode, coorX, coorY);
		return joinNode;

	}

	public void addEdge(ActivityNode source, ActivityNode target) {
		WorkflowUtil.addEdge(activity, source, target);
	}

	public Event addEvent(int coorX, int coorY, String name) {
		Event event = WorkflowUtil.addEvent(activity, name);
		WorkflowViewUtil.setNodeLayout(viewModel, event, coorX, coorY);
		return event;
	}

	public Role addRole(int coorX, int coorY, Action action, String name) {
		Role role = WorkflowUtil.addRole(workflowModel, action, name);
		Reference reference = WorkflowViewUtil.setRoleLayout(workflowModel,
				viewModel, activity, action, role, coorX, coorY);
		WorkflowViewUtil.setReferenceEdge(viewModel, activity, action,
				reference);
		return role;
	}
}
