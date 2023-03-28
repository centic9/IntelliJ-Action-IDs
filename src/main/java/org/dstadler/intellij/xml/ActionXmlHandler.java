package org.dstadler.intellij.xml;

import org.dstadler.commons.xml.AbstractSimpleContentHandler;
import org.xml.sax.Attributes;

/**
 * A SAX-based XML parser which creates {@link ActionIDDef}s out of the Action-XML.
 */
public class ActionXmlHandler extends AbstractSimpleContentHandler<String, ActionIDDef> {
    private String group = null;

    @Override
    public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String qName, Attributes attributes) {
        // <action id="Vcs.ShowTabbedFileHistory" class="com.intellij.openapi.vcs.actions.TabbedShowHistoryAction" icon="AllIcons.Vcs.History"/>
        String id = attributes.getValue("id");
        if(id == null) {
            // some entries only have a class
            id = attributes.getValue("class");
        }
        if(localName.equals("action")) {
            ActionIDDef def = new ActionIDDef();

            def.setId(id);
            def.setText(attributes.getValue("text"));
            if(group != null) {
                def.setGroup(group);
            }

            if(id == null) {
                System.out.println("Could not read id of " + attributes.getValue(0));
            } else {
                configs.put(id, def);
            }
        } else if (localName.equals("group")) {
            group = id;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (localName.equals("group")) {
            group = null;
        }
    }
}
