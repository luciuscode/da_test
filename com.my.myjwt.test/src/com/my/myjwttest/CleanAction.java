package com.my.myjwttest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jwt.we.editors.actions.external.WEExternalAction;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jwt.meta.model.core.Model;
import org.eclipse.jwt.meta.model.events.Event;
import org.eclipse.jwt.meta.model.events.EventsFactory;
import org.eclipse.jwt.meta.model.organisations.OrganisationsFactory;
import org.eclipse.jwt.meta.model.processes.Activity;
import org.eclipse.jwt.meta.model.processes.ActivityNode;
import org.eclipse.jwt.meta.model.processes.InitialNode;
import org.eclipse.jwt.meta.model.processes.ProcessesFactory;
import org.eclipse.jwt.we.conf.model.ConfModel;
import org.eclipse.jwt.we.editors.WEEditor;
import org.eclipse.jwt.we.editors.actions.external.WEExternalAction;
import org.eclipse.jwt.we.model.view.Diagram;
import org.eclipse.jwt.we.model.view.ViewFactory;

public class CleanAction extends MyAction {

	public CleanAction() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public void run() {

		initialRes();
		clean();
		save();
		refresh();
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
		// confModel.getAspectInstances().clear();
		// confModel.getProfiles().clear();
	}

}
