/**
 */
package org.eclipse.jwt.conf.template.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.jwt.conf.template.Template;
import org.eclipse.jwt.conf.template.TemplateFactory;
import org.eclipse.jwt.conf.template.TemplatePackage;

import org.eclipse.jwt.meta.model.application.ApplicationPackage;

import org.eclipse.jwt.meta.model.core.CorePackage;

import org.eclipse.jwt.meta.model.data.DataPackage;

import org.eclipse.jwt.meta.model.events.EventsPackage;

import org.eclipse.jwt.meta.model.functions.FunctionsPackage;

import org.eclipse.jwt.meta.model.organisations.OrganisationsPackage;

import org.eclipse.jwt.meta.model.primitiveTypes.PrimitiveTypesPackage;

import org.eclipse.jwt.meta.model.processes.ProcessesPackage;

import org.eclipse.jwt.we.conf.model.ConfPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TemplatePackageImpl extends EPackageImpl implements TemplatePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass templateEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.jwt.conf.template.TemplatePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TemplatePackageImpl() {
		super(eNS_URI, TemplateFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link TemplatePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TemplatePackage init() {
		if (isInited) return (TemplatePackage)EPackage.Registry.INSTANCE.getEPackage(TemplatePackage.eNS_URI);

		// Obtain or create and register package
		TemplatePackageImpl theTemplatePackage = (TemplatePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TemplatePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TemplatePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ConfPackage.eINSTANCE.eClass();
		CorePackage.eINSTANCE.eClass();
		ProcessesPackage.eINSTANCE.eClass();
		EventsPackage.eINSTANCE.eClass();
		PrimitiveTypesPackage.eINSTANCE.eClass();
		FunctionsPackage.eINSTANCE.eClass();
		OrganisationsPackage.eINSTANCE.eClass();
		ApplicationPackage.eINSTANCE.eClass();
		DataPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTemplatePackage.createPackageContents();

		// Initialize created meta-data
		theTemplatePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTemplatePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TemplatePackage.eNS_URI, theTemplatePackage);
		return theTemplatePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTemplate() {
		return templateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplate_Action() {
		return (EReference)templateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateFactory getTemplateFactory() {
		return (TemplateFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		templateEClass = createEClass(TEMPLATE);
		createEReference(templateEClass, TEMPLATE__ACTION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ConfPackage theConfPackage = (ConfPackage)EPackage.Registry.INSTANCE.getEPackage(ConfPackage.eNS_URI);
		ProcessesPackage theProcessesPackage = (ProcessesPackage)EPackage.Registry.INSTANCE.getEPackage(ProcessesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		templateEClass.getESuperTypes().add(theConfPackage.getAspectInstance());

		// Initialize classes and features; add operations and parameters
		initEClass(templateEClass, Template.class, "Template", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTemplate_Action(), theProcessesPackage.getAction(), null, "action", null, 0, 1, Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TemplatePackageImpl
