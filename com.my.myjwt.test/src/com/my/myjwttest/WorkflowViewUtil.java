package com.my.myjwttest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jwt.meta.model.core.GraphicalElement;
import org.eclipse.jwt.meta.model.core.Model;
import org.eclipse.jwt.meta.model.organisations.Role;
import org.eclipse.jwt.meta.model.processes.Action;
import org.eclipse.jwt.meta.model.processes.Activity;
import org.eclipse.jwt.we.model.view.Diagram;
import org.eclipse.jwt.we.model.view.LayoutData;
import org.eclipse.jwt.we.model.view.Reference;
import org.eclipse.jwt.we.model.view.ReferenceEdge;
import org.eclipse.jwt.we.model.view.ViewFactory;

public class WorkflowViewUtil {
	public static final String WORKFLOW_VIEW_TECHNICAL = "Technical";
	public static ViewFactory viewFactory = ViewFactory.eINSTANCE;

	/**
	 * get the layout data for the given graphical element
	 * 
	 * @param lodatalist
	 *            LayoutData List from the editor
	 * @param gEle
	 * @return layout data
	 */
	public static LayoutData getNodeLayout(EList<LayoutData> lodatalist,
			GraphicalElement gEle) {
		Iterator<LayoutData> it = lodatalist.iterator();
		while (it.hasNext()) {
			LayoutData layoutdata = it.next();
			GraphicalElement desEle = layoutdata.getDescribesElement();
			if (desEle.equals(gEle)) {
				return layoutdata;
			}
		}
		return null;
	}

	/**
	 * get the layout data for the given role
	 * 
	 * @param lodatalist
	 *            LayoutData List from the editor
	 * @param reference
	 *            reference for a role
	 * @return layout data
	 */
	public static LayoutData getRoleLayout(EList<LayoutData> lodatalist,
			Reference reference) {
		Iterator<LayoutData> it = lodatalist.iterator();
		while (it.hasNext()) {
			LayoutData layoutdata = it.next();
			GraphicalElement desEle = layoutdata.getDescribesElement();
			if (desEle instanceof Reference) {
				if (((Reference) desEle).equals(reference)) {
					return layoutdata;
				}
			}
		}
		return null;
	}

	public static void setNodeLayout(Diagram viewModel, GraphicalElement gEle,
			int CoorX, int CoorY, String viewid) {
		EList<LayoutData> lodatalist = viewModel.getLayoutData();
		LayoutData layoutdata = getNodeLayout(lodatalist, gEle);
		layoutdata.setViewid(viewid);
		layoutdata.setX(CoorX);
		layoutdata.setY(CoorY);
	}

	public static void setNodeLayout(Diagram viewModel, GraphicalElement gEle,
			int CoorX, int CoorY) {
		EList<LayoutData> lodatalist = viewModel.getLayoutData();
		LayoutData layoutdata = getNodeLayout(lodatalist, gEle);
		layoutdata.setX(CoorX);
		layoutdata.setY(CoorY);
	}

	/**
	 * set the layout the role.
	 * @param workflowModel
	 * @param viewModel
	 * @param activity
	 * @param action
	 * @param role
	 * @param coorX
	 * @param coorY
	 * @return
	 */
	public static Reference setRoleLayout(Model workflowModel,
			Diagram viewModel, Activity activity, Action action, Role role,
			int coorX, int coorY) {

		Reference reference = viewFactory.createReference();
		reference.setContainedIn(activity);
		reference.setReference(role);
		viewModel.getReferences().add(reference);

		// TODO: set the right view id
		LayoutData layoutdata = getRoleLayout(viewModel.getLayoutData(),
				reference);
		if (layoutdata == null) {
			layoutdata = viewFactory.createLayoutData();
			layoutdata.setDescribesElement(reference);
			layoutdata.setViewid("Technical");
			layoutdata.setInitialized(true);
			viewModel.getLayoutData().add(layoutdata);
		}
		layoutdata.setX(coorX);
		layoutdata.setY(coorY);
		return reference;
	}

	/**
	 * set the reference edge between role and action
	 * @param viewModel
	 * @param activity
	 * @param action
	 * @param reference
	 */
	public static void setReferenceEdge(Diagram viewModel, Activity activity,
			Action action, Reference reference) {
		ReferenceEdge referenceEdge = viewFactory.createReferenceEdge();
		referenceEdge.setReference(reference);
		referenceEdge.setContainedIn(activity);
		referenceEdge.setAction(action);
		viewModel.getReferenceEdges().add(referenceEdge);
		reference.getReferenceEdges().add(referenceEdge);
		referenceEdge.setReference(reference);
	}
}
