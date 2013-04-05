package com.my.myjwttest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jwt.meta.model.core.Model;
import org.eclipse.jwt.meta.model.events.EventsFactory;
import org.eclipse.jwt.meta.model.organisations.OrganisationsFactory;
import org.eclipse.jwt.meta.model.processes.Activity;
import org.eclipse.jwt.meta.model.processes.ProcessesFactory;
import org.eclipse.jwt.we.conf.model.ConfModel;
import org.eclipse.jwt.we.editors.WEEditor;
import org.eclipse.jwt.we.editors.actions.external.WEExternalAction;
import org.eclipse.jwt.we.model.view.Diagram;
import org.eclipse.jwt.we.model.view.ViewFactory;

/**
 * this class is used as a template of customized jwt action
 * 
 * @author Xi
 * 
 */
public class MyAction extends WEExternalAction {

	WEEditor workflowEditor;
	Model workflowModel;
	Activity activity;
	Diagram diagram;
	ConfModel confModel;
	Resource workflowResource;
	Resource workflowViewResource;
	Resource workflowConfResource;

	ProcessesFactory processFactory = ProcessesFactory.eINSTANCE;
	ViewFactory viewFactory = ViewFactory.eINSTANCE;
	EventsFactory eventsFactory = EventsFactory.eINSTANCE;
	OrganisationsFactory orgFactory = OrganisationsFactory.eINSTANCE;

	public MyAction() {

	}

	@Override
	public ImageDescriptor getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		initialRes();
		save();
		refresh();
	}

	/**
	 * initial all of the resource.
	 */
	public void initialRes() {
		workflowEditor = getActiveWEEditor();
		workflowResource = workflowEditor.getMainModelResource();
		workflowModel = (Model) workflowEditor.getModel();
		activity = workflowEditor.getDisplayedActivityModel();
		diagram = workflowEditor.getDiagramData();
		// activity = getActiveActivitySheet().getActivityModel();
		// EditingDomain editingDomain = workflowEditor.getEmfEditingDomain();

		workflowViewResource = WorkflowUtil
				.getWorkflowViewReousrce(workflowResource);
		workflowConfResource = WorkflowUtil
				.getWorkflowConfReousrce(workflowResource);
		confModel = (ConfModel) workflowConfResource.getContents().get(0);
	}

	/**
	 * save the workflow model.
	 */
	public void save() {
//		Map<String, String> saveOptions = new HashMap<String, String>();
//		saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8");
		
		final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
				Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		
		try {
			workflowModel.eResource().save(saveOptions);
			// workflowModel.eResource().save(null);
			diagram.eResource().save(saveOptions);
			// confModel.eResource().save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * refresh the jwt editor.
	 */
	public void refresh() {
		workflowEditor.refreshOutline();
		workflowEditor.refreshPages();
		workflowEditor.refreshProperties();
		workflowEditor.refreshZoom();
	}
}
