package org.dstadler.intellij;

import org.dstadler.commons.xml.AbstractSimpleContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * A SAX-based XML parser which creates {@link ActionIDDef}s out of the Action-XML.
 */
public class ActionXmlHandler extends AbstractSimpleContentHandler<String, ActionIDDef> {
    private String group = null;

    @Override
    public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String qName, Attributes attributes) throws SAXException {
        // <action id="Vcs.ShowTabbedFileHistory" class="com.intellij.openapi.vcs.actions.TabbedShowHistoryAction" icon="AllIcons.Vcs.History"/>
        if(localName.equals("action")) {
            ActionIDDef def = new ActionIDDef();

            def.setId(attributes.getValue("id"));
            def.setText(attributes.getValue("text"));
            if(group != null) {
                def.setGroup(group);
            }
            configs.put(attributes.getValue("id"), def);
        } else if (localName.equals("group")) {
            group = attributes.getValue("id");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("group")) {
            group = null;
        }
    }
}
