this is a jwt extension template project that shows how to create a simple extension for jwt. 

project structure

1. installation
eclipse -> help -> install new software -> juno -> soa development -> java workflow tooling
requred plugin:
org.eclipse.jwt.converter
org.eclipse.meta
org.eclipse.we
org.eclipse.we.conf.edit
org.eclipse.we.conf.model
org.eclipse.we.conf.editor
org.eclipse.we.conf.property.edit
org.eclipse.we.conf.property.model
org.eclipse.we.conf.we

2. set the plugin.xml 
-> dependencies: add plugins of "org.eclipse.jwt.we.conf.model" & "org.eclipse.jwt.meta"

3. create emf empty project (e.g., org.js.model.workflow.template)
1) create your customer ecore file (e.g., template.ecore)
2) load resource -> browse registered packages -> org.eclipse.jwt.conf & org.eclipse.jwt/processes
3) the root class should be have the "AspectInstance" as parent class

ecore file example : template.ecore
<?xml version="1.0" encoding="UTF-8"?>
<ecore:EPackage xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore" name="template" nsURI="org.js.model.workflow.template" nsPrefix="template">
<eClassifiers xsi:type="ecore:EClass" name="Template" eSuperTypes="platform:/plugin/org.eclipse.jwt.we.conf.model/model/ConfMetaModel.ecore#//AspectInstance">
<eStructuralFeatures xsi:type="ecore:EReference" name="action" eType="ecore:EClass platform:/plugin/org.eclipse.jwt.meta/src/org/eclipse/jwt/meta/ecore/JWTMetaModel.ecore#//processes/Action"/>
</eClassifiers>
</ecore:EPackage>


4. create the generate model file and generate the codes
-> maybe bugs after generating: in the *.java files we need to change "import org.eclipse.jwt.we.conf..." to "org.eclipse.jwt.we.conf.model..."
-> e.g., change "import org.eclipse.jwt.we.conf.AspectInstance;" 
to "import org.eclipse.jwt.we.conf.model.AspectInstance;"

5. set the plugin.xml
-> extensions -> add extention "org.eclipse.jwt.we.conf.model.conf" -> give the unique id and name -> set conf pointing to our *.conf file (e.g., template.conf)

6. create the *.conf file
1) create new folder with name "conf"
2) create *.conf file (new -> java workflow tooling -> new aspect configuration model)
3) add the profile and aspect

conf file example: template.conf
<?xml version="1.0" encoding="UTF-8"?>
<conf:ConfModel xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:conf="org.eclipse.jwt.conf" xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore">
<profiles name="org.js.model.workflow.template.profile">
<aspects id="org.js.model.workflow.template.aspect" autocreated="false" multiple="false">
<aspectInstanceEType xsi:type="ecore:EClass" href="org.js.model.workflow.template#//Template"/>
<targetModelElements href="org.eclipse.jwt/processes#//Action"/>
</aspects>
</profiles>
</conf:ConfModel>

7. 	run the eclipse app. with the project and the required plugin.