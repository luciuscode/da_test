package com.my.myjwttest;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jwt.we.conf.model.AspectInstance;
import org.eclipse.jwt.we.editors.actions.external.WEExternalAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.js.model.rbac.AccessControlModel;
import org.js.model.rbac.impl.AccessControlModelImpl;
import org.js.model.workflow.ACMConnector;

public class ImportModelAction extends MyAction {

	private final int OK = 0;
	private final int CANCEL = 1;
	private Shell shell;
	private ResourceSet resourceSet;
	private Resource rbacRes;

	public ImportModelAction() {
	}

	@Override
	public ImageDescriptor getImage() {
		return null;
	}

	@Override
	public void run() {
		initialRes();
		loadModel();
		save();
		refresh();

	}

	/**
	 * select the model file and import it if it is a rbac model file.
	 */
	public void loadModel() {
		shell = workflowEditor.getSite().getShell();
		resourceSet = new ResourceSetImpl();
		ResourceDialog resourceDiaglog = new ResourceDialog(shell,
				"Load Model", SWT.OPEN);
		int state = resourceDiaglog.open();
		// TODO: if the workflow model already includes a acm model, update the
		// acm model and contained elements
		if (state != CANCEL) {
			if (resourceDiaglog.getURIText() != "") {
				URI uri = resourceDiaglog.getURIs().get(0);
				System.out.println(uri.fileExtension());
				if (uri.fileExtension().equals(
						WorkflowConfUtil.ACM_FILE_EXTENSION)) {
					rbacRes = resourceSet.getResource(resourceDiaglog.getURIs()
							.get(0), true);
					try {
						rbacRes.load(Collections.EMPTY_MAP);
					} catch (IOException e) {
						e.printStackTrace();
					}
					loadACModel();
				} else {
					String info = "Please selected rbac model file with the file extension "
							+ WorkflowConfUtil.ACM_FILE_EXTENSION + ".";

					MessageDialog.openInformation(getActiveShell(), "Warning",
							info);
				}
			}
		}
	}

	/**
	 * connect access control model to jwt model.
	 */
	public void loadACModel() {
		TreeIterator<EObject> rbacIt = rbacRes.getAllContents();
		AccessControlModel acm = null;

		while (rbacIt.hasNext()) {
			EObject object = rbacIt.next();
			if (object instanceof AccessControlModel) {
				acm = (AccessControlModelImpl) object;
			}
		}

		ACMConnector acmconnector = (ACMConnector) WorkflowConfUtil
				.addAspectInstance(workflowModel, WorkflowConfUtil.ACM_ASPECT);
		WorkflowConfUtil.setACMRef(acmconnector, acm);
	}
}
