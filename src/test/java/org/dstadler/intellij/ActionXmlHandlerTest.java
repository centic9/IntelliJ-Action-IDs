package org.dstadler.intellij;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.SortedMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ActionXmlHandlerTest {
    protected static final String XML = "<idea-plugin>\n" +
            "  <actions>\n" +
            "    <action id=\"Vcs.ShowTabbedFileHistory\" class=\"com.intellij.openapi.vcs.actions.TabbedShowHistoryAction\"\n" +
            "            icon=\"AllIcons.Vcs.History\"/>\n" +
            "    <action id=\"Vcs.ShowHistoryForRevision\" class=\"com.intellij.openapi.vcs.actions.TabbedShowHistoryForRevisionAction\"\n" +
            "            icon=\"AllIcons.Vcs.History\"/>\n" +
            "\n" +
            "    <group class=\"com.intellij.openapi.vcs.actions.VcsActionGroup\" id=\"VcsGroup\"/>\n" +
            "\n" +
            "    <group class=\"com.intellij.openapi.vcs.actions.VcsGroupsWrapper\" id=\"VcsFileGroupPopup\" popup=\"true\"/>\n" +
            "\n" +
            "    <group id=\"VersionControlsGroup\">\n" +
            "      <reference ref=\"VcsFileGroupPopup\"/>\n" +
            "    </group>\n" +
            "\n" +
            "    <group id=\"GoToChangeMarkerGroup\">\n" +
            "      <separator/>\n" +
            "      <action id=\"VcsShowNextChangeMarker\" class=\"com.intellij.openapi.vcs.actions.ShowNextChangeMarkerAction\"\n" +
            "              icon=\"AllIcons.Actions.NextOccurence\"/>\n" +
            "      <action id=\"VcsShowPrevChangeMarker\" class=\"com.intellij.openapi.vcs.actions.ShowPrevChangeMarkerAction\"\n" +
            "              icon=\"AllIcons.Actions.PreviousOccurence\"/>\n" +
            "\n" +
            "      <add-to-group group-id=\"GoToMenu\" anchor=\"last\"/>\n" +
            "    </group>\n" +
            "  </actions>\n" +
            "</idea-plugin>\n";

    @Test
    public void testXml() throws IOException, SAXException {
        final SortedMap<String, ActionIDDef> actions = new ActionXmlHandler().parseContent(new ByteArrayInputStream(XML.getBytes("UTF-8")));
        assertNotNull(actions);
        assertEquals("Had: " + actions, 4, actions.size());
        final ActionIDDef action = actions.get("VcsShowNextChangeMarker");
        assertEquals("Had: " + action, "GoToChangeMarkerGroup", action.getGroup());
    }
}