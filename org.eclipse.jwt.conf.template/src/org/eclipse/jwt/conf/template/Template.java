/**
 */
package org.eclipse.jwt.conf.template;

import org.eclipse.jwt.meta.model.processes.Action;

import org.eclipse.jwt.we.conf.model.AspectInstance;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jwt.conf.template.Template#getAction <em>Action</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jwt.conf.template.TemplatePackage#getTemplate()
 * @model
 * @generated
 */
public interface Template extends AspectInstance {
	/**
	 * Returns the value of the '<em><b>Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' reference.
	 * @see #setAction(Action)
	 * @see org.eclipse.jwt.conf.template.TemplatePackage#getTemplate_Action()
	 * @model
	 * @generated
	 */
	Action getAction();

	/**
	 * Sets the value of the '{@link org.eclipse.jwt.conf.template.Template#getAction <em>Action</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' reference.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(Action value);

} // Template
