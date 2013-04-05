package com.my.myjwttest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jwt.meta.model.core.Model;
import org.eclipse.jwt.meta.model.events.Event;
import org.eclipse.jwt.meta.model.events.EventsFactory;
import org.eclipse.jwt.meta.model.organisations.OrganisationsFactory;
import org.eclipse.jwt.meta.model.organisations.Role;
import org.eclipse.jwt.meta.model.processes.Action;
import org.eclipse.jwt.meta.model.processes.Activity;
import org.eclipse.jwt.meta.model.processes.ActivityNode;
import org.eclipse.jwt.meta.model.processes.FinalNode;
import org.eclipse.jwt.meta.model.processes.ForkNode;
import org.eclipse.jwt.meta.model.processes.InitialNode;
import org.eclipse.jwt.meta.model.processes.JoinNode;
import org.eclipse.jwt.meta.model.processes.ProcessesFactory;
import org.eclipse.jwt.we.conf.model.Aspect;
import org.eclipse.jwt.we.conf.model.AspectInstance;
import org.eclipse.jwt.we.conf.model.ConfModel;
import org.eclipse.jwt.we.conf.model.aspects.AspectManager;
import org.eclipse.jwt.we.conf.model.impl.AspectInstanceImpl;
import org.eclipse.jwt.we.conf.model.resource.ConfModelResourceManager;
import org.eclipse.jwt.we.conf.model.resource.ConfResourceException;
import org.eclipse.jwt.we.editors.WEEditor;
import org.eclipse.jwt.we.editors.actions.external.WEExternalAction;
import org.eclipse.jwt.we.misc.util.EMFHelper;
import org.eclipse.jwt.we.model.view.Diagram;
import org.eclipse.jwt.we.model.view.LayoutData;
import org.eclipse.jwt.we.model.view.Reference;
import org.eclipse.jwt.we.model.view.ViewFactory;
import org.js.model.rbac.Permission;
import org.js.model.rbac.RbacFactory;
import org.js.model.workflow.Log;
import org.js.model.workflow.State;
import org.js.model.workflow.StateEnum;
import org.js.model.workflow.WorkflowFactory;

public class TestAction extends MyAction {

	WEEditor workflowEditor;
	Model workflowModel;
	Activity activity;
	Diagram diagram;
	ConfModel confModel;
	Resource workflowResource;
	Resource workflowViewResource;
	Resource workflowConfResource;
	EditingDomain editingDomain;

	ProcessesFactory processFactory = ProcessesFactory.eINSTANCE;
	ViewFactory viewFactory = ViewFactory.eINSTANCE;
	EventsFactory eventsFactory = EventsFactory.eINSTANCE;
	OrganisationsFactory orgFactory = OrganisationsFactory.eINSTANCE;

	String testrole1 = "role1";
	String testrole2 = "role2";
	String testaction1 = "action1";
	String testaction2 = "action2";
	String testevent = "event1";

	public TestAction() {
	}

	@Override
	public ImageDescriptor getImage() {
		return null;
	}

	@Override
	public void run() {

		initialRes();
		clean();
		 addWorkflowELement();
		// removeWorkflowElement();
		testConfElement();
		// removeConfElement();
		// initial();
		// simpleInitial();
		// complexInitial();
		save();
		refresh();
		//
		// printAll();
	}


	public void complexInitial() {
		InitialNode iniNode = addInitialNode(100, 200);
		Action action1 = addAction(activity, diagram, null, 200, 200);
		addEdge(iniNode, action1);
		ForkNode forkNode1 = addForkNode(300, 200);
		addEdge(action1, forkNode1);
		Action action2 = addAction(activity, diagram, null, 400, 200);
		addEdge(forkNode1, action2);
		Action action3 = addAction(activity, diagram, null, 400, 400);
		addEdge(forkNode1, action3);
		addRole(null, 200, 50, action2, "provider1");
		addRole(null, 400, 50, action3, "provider2");
		Action action4 = addAction(activity, diagram, null, 500, 200);
		Action action5 = addAction(activity, diagram, null, 500, 400);
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

	public void simpleInitial() {
		// TODO Auto-generated method stub

	}


	/**
	 * clean the worklfow.
	 */
	public void clean() {
		// remove the activity element
		activity.getNodes().clear();
		activity.getEdges().clear();
		// remove the roles
		workflowModel.getElements().removeAll(
				workflowModel.getElements().subList(1,
						workflowModel.getElements().size()));
		// remove the layout data
		diagram.getReferenceEdges().clear();
		diagram.getReferences().clear();
		diagram.getLayoutData().clear();
//		confModel.getAspectInstances().clear();
//		 confModel.getProfiles().clear();
	}

	/**
	 * initial the workflow.
	 */
	public void initial() {
		// InitialNode iniNode = addInitialNode(100, 200);
		// Action action1 = addAction(activity, diagram, null, 200, 200);
		// addEdge(iniNode, action1);
		// ForkNode forkNode1 = addForkNode(300, 200);
		// addEdge(action1, forkNode1);
		// Action action2 = WorkflowUtil.addAction(activity, testaction1);
		// addEdge(forkNode1, action2);
		// Action action3 = WorkflowUtil.addAction(400, 400, null);
		// addEdge(forkNode1, action3);
		// addRole(null, 200, 50, action2, null);
		// addRole(null, 400, 50, action3, null);
		// Action action4 = WorkflowUtil.addAction(500, 200, null);
		// Action action5 = WorkflowUtil.addAction(500, 400, null);
		// addEdge(action2, action4);
		// addEdge(action3, action5);
		// JoinNode joinNode1 = addJoinNode(600, 200);
		// addEdge(action4, joinNode1);
		// addEdge(action5, joinNode1);
		// Event event1 = addEvent(700, 200, null);
		// addEdge(joinNode1, event1);
		// FinalNode finalNode = addFinalNode(800, 200);
		// addEdge(event1, finalNode);
	}

	/**
	 * add the element and related layout data
	 */
	public void addWorkflowELement() {
		// ActivityNode tempAct=null;
		// // add action
		// WorkflowUtil.addAction(activity, testaction1);
		// tempAct= WorkflowUtil.getActivityNode(activity, testaction1);
		// WorkflowViewUtil.setNodeLayout(diagram, tempAct, 100, 100);
		// WorkflowUtil.addAction(activity, testaction2);
		// tempAct= WorkflowUtil.getActivityNode(activity, testaction2);
		// WorkflowViewUtil.setNodeLayout(diagram, tempAct, 200, 200);
		// // add event
		// WorkflowUtil.addEvent(activity, testevent);
		// tempAct= WorkflowUtil.getActivityNode(activity, testevent);
		// WorkflowViewUtil.setNodeLayout(diagram, tempAct, 200, 200);
		// // add initial node
		// WorkflowUtil.addInitialNode(activity);
		// tempAct= WorkflowUtil.getActivityNode(activity,
		// WorkflowUtil.INITIAL_NODE_NAME);
		// WorkflowViewUtil.setNodeLayout(diagram, tempAct, 200, 200);
		// // add final node
		// WorkflowUtil.addFinalNode(activity);
		// tempAct= WorkflowUtil.getActivityNode(activity,
		// WorkflowUtil.FINAL_NODE_NAME);
		// WorkflowViewUtil.setNodeLayout(diagram, tempAct, 200, 200);
		// // add join node
		// tempAct=WorkflowUtil.addJoinNode(activity);
		// WorkflowViewUtil.setNodeLayout(diagram, tempAct, 200, 200);
		// // add fork node
		// tempAct=WorkflowUtil.addForkNode(activity);
		// WorkflowViewUtil.setNodeLayout(diagram, tempAct, 200, 200);
		// // add edge between action 1 and event1
		// WorkflowUtil.addEdge(activity,
		// WorkflowUtil.getActivityNode(activity, testaction1),
		// WorkflowUtil.getActivityNode(activity, testevent));
		// // add role
		// WorkflowUtil.addRole(workflowModel, testrole1);
		// WorkflowViewUtil.setRoleLayout(diagram, activity,
		// WorkflowUtil.getRole(workflowModel, testrole1), 50, 50);
		// // add reference edge between role and action
		// WorkflowViewUtil.setReferenceEdge(diagram, activity,
		// (Action) WorkflowUtil.getActivityNode(activity, testaction1),
		// WorkflowUtil.getRole(workflowModel, testrole1));
		// WorkflowViewUtil.setReferenceEdge(diagram, activity,
		// (Action) WorkflowUtil.getActivityNode(activity, testaction2),
		// WorkflowUtil.getRole(workflowModel, testrole1));
	}

	/**
	 * 1. if we do not add layout data for workflow element, jwt will
	 * automatically create a layout data for the element.
	 * 
	 * 2 if we need to delete a workflow element, first remove element, then
	 * layout. Because jwt editor will automatically generate layout data if
	 * element is not removed
	 */
	public void testWorkflowElement() {
		ActivityNode aciNode = null;

		// add action
		WorkflowUtil.addAction(activity, testaction1);
		aciNode = WorkflowUtil.getActivityNode(activity, testaction1);
		WorkflowViewUtil.setNodeLayout(diagram, aciNode, 100, 100);
		WorkflowUtil.addAction(activity, testaction2);
		aciNode = WorkflowUtil.getActivityNode(activity, testaction2);
		WorkflowViewUtil.setNodeLayout(diagram, aciNode, 200, 200);

		// remove action
		aciNode = WorkflowUtil.removeActNode(activity, testaction1);
		WorkflowViewUtil.removeNodeLayout(diagram, aciNode);
		aciNode = WorkflowUtil.removeActNode(activity, testaction2);
		WorkflowViewUtil.removeNodeLayout(diagram, aciNode);

		// add event
		WorkflowUtil.addEvent(activity, testevent);
		aciNode = WorkflowUtil.getActivityNode(activity, testevent);
		WorkflowViewUtil.setNodeLayout(diagram, aciNode, 200, 200);

		// remove event
		WorkflowUtil.addEvent(activity, testevent);
		aciNode = WorkflowUtil.removeActNode(activity, testevent);
		WorkflowViewUtil.removeNodeLayout(diagram, aciNode);

		// add initial node
		WorkflowUtil.addInitialNode(activity);
		aciNode = WorkflowUtil.getActivityNode(activity,
				WorkflowUtil.INITIAL_NODE_NAME);
		WorkflowViewUtil.setNodeLayout(diagram, aciNode, 200, 200);

		// remove initial node
		WorkflowUtil.addInitialNode(activity);
		aciNode = WorkflowUtil.removeActNode(activity,
				WorkflowUtil.INITIAL_NODE_NAME);
		WorkflowViewUtil.removeNodeLayout(diagram, aciNode);

		// add final node
		WorkflowUtil.addFinalNode(activity);
		aciNode = WorkflowUtil.getActivityNode(activity,
				WorkflowUtil.FINAL_NODE_NAME);
		WorkflowViewUtil.setNodeLayout(diagram, aciNode, 200, 200);

		// remove final node
		WorkflowUtil.addFinalNode(activity);
		aciNode = WorkflowUtil.removeActNode(activity,
				WorkflowUtil.FINAL_NODE_NAME);
		WorkflowViewUtil.removeNodeLayout(diagram, aciNode);

		// TODO: remove join/fork node
		// add join node
		aciNode = WorkflowUtil.addJoinNode(activity);
		WorkflowViewUtil.setNodeLayout(diagram, aciNode, 200, 200);
		// add fork node
		aciNode = WorkflowUtil.addForkNode(activity);
		WorkflowViewUtil.setNodeLayout(diagram, aciNode, 200, 200);

		// add edge between action 1 and event1
		WorkflowUtil.addAction(activity, testaction1);
		WorkflowUtil.addEvent(activity, testevent);
		WorkflowUtil.addEdge(activity,
				WorkflowUtil.getActivityNode(activity, testaction1),
				WorkflowUtil.getActivityNode(activity, testevent));

		// remove edge
		WorkflowUtil.removeEdge(activity,
				WorkflowUtil.getActivityNode(activity, testaction1),
				WorkflowUtil.getActivityNode(activity, testevent));
		aciNode = WorkflowUtil.removeActNode(activity, testaction1);
		WorkflowViewUtil.removeNodeLayout(diagram, aciNode);
		aciNode = WorkflowUtil.removeActNode(activity, testevent);
		WorkflowViewUtil.removeNodeLayout(diagram, aciNode);

		// add role
		WorkflowUtil.addRole(workflowModel, testrole1);
		WorkflowUtil.addRole(workflowModel, testrole2);
		WorkflowViewUtil.setRoleLayout(diagram, activity,
				WorkflowUtil.getRole(workflowModel, testrole1), 50, 50);
		WorkflowViewUtil.setRoleLayout(diagram, activity,
				WorkflowUtil.getRole(workflowModel, testrole2), 100, 100);

		// remove role
		WorkflowViewUtil.removeRoleLayout(diagram,
				WorkflowUtil.getRole(workflowModel, testrole1));
		WorkflowViewUtil.removeRoleLayout(diagram,
				WorkflowUtil.getRole(workflowModel, testrole2));
		WorkflowUtil.removeRole(workflowModel, testrole1);
		WorkflowUtil.removeRole(workflowModel, testrole2);

		// add reference edge between role and action
		WorkflowUtil.addAction(activity, testaction1);
		WorkflowUtil.addRole(workflowModel, testrole1);
		WorkflowUtil.addAction(activity, testaction2);
		WorkflowUtil.addRole(workflowModel, testrole2);
		WorkflowViewUtil.setRoleLayout(diagram, activity,
				WorkflowUtil.getRole(workflowModel, testrole1), 50, 50);
		WorkflowViewUtil.setRoleLayout(diagram, activity,
				WorkflowUtil.getRole(workflowModel, testrole2), 100, 100);
		WorkflowViewUtil.setReferenceEdge(diagram, activity,
				(Action) WorkflowUtil.getActivityNode(activity, testaction1),
				WorkflowUtil.getRole(workflowModel, testrole1));
		WorkflowViewUtil.setReferenceEdge(diagram, activity,
				(Action) WorkflowUtil.getActivityNode(activity, testaction2),
				WorkflowUtil.getRole(workflowModel, testrole1));

		// remove reference edge
		WorkflowViewUtil.removeReferenceEdge(diagram,
				WorkflowUtil.getRole(workflowModel, testrole1),
				(Action) WorkflowUtil.getActivityNode(activity, testaction1));
		WorkflowViewUtil.removeReferenceEdge(diagram,
				WorkflowUtil.getRole(workflowModel, testrole1),
				(Action) WorkflowUtil.getActivityNode(activity, testaction2));
	}

	public void testConfElement() {
		ActivityNode tempAct = null;
		
//		 add state aspect
		WorkflowUtil.addAction(activity, testaction1);
		tempAct = WorkflowUtil.getActivityNode(activity, testaction1);
		WorkflowViewUtil.setNodeLayout(diagram, tempAct, 100, 100);
		WorkflowConfUtil.addAspectInstance(tempAct,
				WorkflowConfUtil.STATE_ASPECT);
		State state = (State) WorkflowConfUtil.getAspectInstance(tempAct,
				WorkflowConfUtil.STATE_ASPECT);
		WorkflowConfUtil.setState(state, StateEnum.RUNNING);
		
		// remove state aspect
		tempAct = WorkflowUtil.getActivityNode(activity, testaction1);
		WorkflowConfUtil.removeAspectInstance(tempAct, WorkflowConfUtil.STATE_ASPECT);
	}

	public void removeConfElement() {
		// TODO Auto-generated method stub

	}

	public void testSimpleInitial() {
		// Action action = processFactory.createAction();
		// action.setName("action1");
		// activity.getNodes().add(action);
		// test add layout

		// InitialNode iniNode = addInitialNode(100, 200);
		Action action1 = addAction(activity, diagram, "task1", 200, 200);
		// addEdge(iniNode, action1);
		// Action action2 = addAction(300, 200, null);
		// addEdge(action1, action2);
		// FinalNode finalNode = addFinalNode(400, 200);
		// addEdge(action2, finalNode);

		addRole(null, 200, 50, action1, "provider1");
		// addRole(null, 300, 50, action2, "provider2");
	}

	/**
	 * save the workflow.
	 */
	public void save() {
		Map<String, String> options = new HashMap<String, String>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		try {
			workflowModel.eResource().save(options);
			// workflowModel.eResource().save(null);
			diagram.eResource().save(options);
//			confModel.eResource().save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testjwt() {
		Action action = addAction(activity, diagram, "test", 100, 200);
		activity.getNodes().add(action);

		EObject modelEle = activity.getNodes().get(0);

		Aspect aspect = AspectManager.INSTANCE.getAspect(modelEle,
				"org.js.model.workflow.template.aspect");
		System.out.println(aspect);
		AspectManager.INSTANCE.createAndAddAspectInstance(aspect, activity
				.getNodes().get(0));
		Log ai = (Log) AspectManager.INSTANCE.getAspectInstance(modelEle,
				aspect);
		// ai.get
	}

	public void testadd() {
		Action action = addAction(activity, diagram, "test", 100, 200);
		activity.getNodes().add(action);

		EObject modelEle = activity.getNodes().get(0);

		Aspect aspect = AspectManager.INSTANCE.getAspect(modelEle,
				WorkflowConfUtil.STATE_ASPECT);
		System.out.println(aspect);
		AspectManager.INSTANCE.createAndAddAspectInstance(aspect, activity
				.getNodes().get(0));
		State ai = (State) AspectManager.INSTANCE.getAspectInstance(modelEle,
				aspect);

		ai.setState(StateEnum.INACTIVE);
	}

	public void testdelete() {
		EObject modelEle = activity.getNodes().get(0);
		Aspect aspect = AspectManager.INSTANCE.getAspect(modelEle,
				WorkflowConfUtil.LOG_ASPECT);
		AspectManager.INSTANCE.getAspectInstance(modelEle, aspect);
		// AspectManager.INSTANCE.getConfModel(modelEle).getAspectInstances()
		// .remove(ai);
		activity.getNodes().remove(modelEle);
		diagram.getLayoutData().remove(0);
	}

	public void testConf() {
		// Action action = addAction(100, 200, "test");
		// Role role = addRole(null,100, 100, action, "test");

		// System.out.println(activity.getNodes().get(0));

		// EObject eob
		// =EcoreUtil.create(confModel.getProfiles().get(0).getAspects().get(0).getAspectInstanceEType().get);

		/**
		 * this is the code for generating class instance of conf model
		 */

		// System.out.println(confModel.getProfiles().get(0).getAspects().get(0)
		// .getAspectInstanceEType().getEPackage());
		// EPackage epack = confModel.getProfiles().get(0).getAspects().get(0)
		// .getAspectInstanceEType().getEPackage();
		// String name = confModel.getProfiles().get(0).getAspects().get(0)
		// .getAspectInstanceEType().getInstanceClassName();
		// System.out.println(name);
		// SampleStaticAspect ssa = (SampleStaticAspect) EcoreUtil
		// .create(toEClass(name));
		// System.out.println(EcoreUtil.create(toEClass(name)));
		// ((SampleStaticAspect) confModel.getAspectInstances().get(0))
		// .setSampleactionref((Action) activity.getNodes().get(1));
		// workflowEditor.getEmfEditingDomain().loadResource(
		// confModel.eResource().getURI().path());

		AspectInstance aspectIns = AspectManager.INSTANCE.getAspectInstance(
				activity.getNodes().get(0), confModel.getProfiles().get(0)
						.getAspects().get(0));
		// AspectManager.INSTANCE.
		System.out.println("config: "
				+ AspectManager.INSTANCE.getConfModel(activity.getNodes()
						.get(0)));
		System.out.println("orig: " + confModel);

		System.out.println("confmodel: " + aspectIns);
		// System.out.println("orig: "+confModel.getAspectInstances().get(0));
		// System.out.println(AspectManager.INSTANCE.getAspectInstances(activity.getNodes().get(0)));

		EObject modelEle = activity.getNodes().get(0);

		Aspect aspect = AspectManager.INSTANCE.getAspect(modelEle,
				"jwt.conf.extension.logaspect");
		System.out.println(aspect);
		AspectInstance ai = AspectManager.INSTANCE.createAndAddAspectInstance(
				aspect, activity.getNodes().get(0));
		System.out.println(ai);
		// AspectManager.INSTANCE.getAspectInstances(activity.getNodes().get(0)).get(0);
		// ai.setSampleintprop(3);
		// ai.setSampleactionref((Action)modelEle);
		// AspectManager.INSTANCE.getConfModel(activity.getNodes().get(0));
		// ConfModelResourceManager.INSTANCE.
		// EClass targetEClass = (EClass) epack.getEClassifier(name);
		// System.out.println(targetEClass);
		// EcoreUtil.create(targetEClass);
		// System.out.println(toEClass("staticaspect:SampleStaticAspect"));

		// Iterator it = confModel.eAllContents();
		// AspectInstance aspectIns = null;
		// while (it.hasNext()) {
		// EObject object = (EObject) it.next();
		// if (object instanceof AspectInstance) {
		// aspectIns = (AspectInstance) object;
		// System.out.println("aspectIns: "+aspectIns);
		// System.out.println("getTargetModelElement: "+aspectIns.getTargetModelElement());
		// }
		// }
		// System.out.println("confModel.getProfiles(): "+confModel.getProfiles());
		// System.out.println(confModel.getProfiles().get(0).getAspects());
		// System.out.println(confModel.getProfiles().get(0).getAspects().get(0).getTargetModelElements());
		// System.out.println(confModel.getAspectInstances().get(2).getTargetModelElement());
		// System.out.println(confModel.getAspectInstances().get(1).getTargetModelElement());
		// EStructuralFeature
		// eref=aspectIns.getTargetModelElement().eContainingFeature();
		// System.out.println(aspectIns);
		// System.out.println(workflowModel);
		// System.out.println(workflowModel.getNodes().indexOf(confModel.getAspectInstances().get(0).getTargetModelElement()));
	}

	/**
	 * 
	 * @param modelElementEType
	 * @return
	 */
	public EClass toEClass(String modelElementEType) {
		int packageEndIndex = Math.max(modelElementEType.lastIndexOf('.'),
				modelElementEType.lastIndexOf('/'));
		String targetEPackageName = modelElementEType.substring(0,
				packageEndIndex);
		String targetEClassName = modelElementEType
				.substring(packageEndIndex + 1);
		EPackage targetEPackage = Registry.INSTANCE
				.getEPackage(targetEPackageName);
		if (targetEPackage == null) {
			return null;
		}
		EClass targetEClass = (EClass) targetEPackage
				.getEClassifier(targetEClassName);
		if (targetEClass == null) {
			return null;
		}
		return targetEClass;
	}

	public void printAll() {
		System.out.println(workflowModel.getElements());
		Iterator it = diagram.getLayoutData().iterator();
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

	// TODO: set the layout for the workflow after changes
	public void layout() {

	}

	public Action addAction(Activity activity, Diagram diagram, String name,
			int coorX, int coorY) {
		Action action = WorkflowUtil.addAction(activity, name);
		WorkflowViewUtil.setNodeLayout(diagram, action, coorX, coorY);
		return action;
	}

	public InitialNode addInitialNode(int coorX, int coorY) {
		InitialNode initialNode = WorkflowUtil.addInitialNode(activity);
		WorkflowViewUtil.setNodeLayout(diagram, initialNode, coorX, coorY);
		return initialNode;
	}

	public FinalNode addFinalNode(int coorX, int coorY) {
		FinalNode finalNode = WorkflowUtil.addFinalNode(activity);
		WorkflowViewUtil.setNodeLayout(diagram, finalNode, coorX, coorY);
		return finalNode;
	}

	public ForkNode addForkNode(int coorX, int coorY) {
		ForkNode forkNode = WorkflowUtil.addForkNode(activity);
		WorkflowViewUtil.setNodeLayout(diagram, forkNode, coorX, coorY);
		return forkNode;
	}

	public JoinNode addJoinNode(int coorX, int coorY) {
		JoinNode joinNode = WorkflowUtil.addJoinNode(activity);
		WorkflowViewUtil.setNodeLayout(diagram, joinNode, coorX, coorY);
		return joinNode;

	}

	public void addEdge(ActivityNode source, ActivityNode target) {
		WorkflowUtil.addEdge(activity, source, target);
	}

	public Event addEvent(int coorX, int coorY, String name) {
		Event event = WorkflowUtil.addEvent(activity, name);
		WorkflowViewUtil.setNodeLayout(diagram, event, coorX, coorY);
		return event;
	}

	public Role addRole(org.js.model.rbac.Role rbacRole, int coorX, int coorY,
			Action action, String name) {
		Role role = WorkflowUtil.addRole(workflowModel, name);
		Reference reference = WorkflowViewUtil.setRoleLayout(diagram, activity,
				role, coorX, coorY);
		WorkflowViewUtil.setReferenceEdge(diagram, activity, action, role);
		// WorkflowConfModelUtil.addRoleAspect(confModel, role, rbacRole);
		return role;
	}

}
