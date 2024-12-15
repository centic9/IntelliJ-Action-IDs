package org.dstadler.intellij.xml;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ActionXmlHandlerTest {
    protected static final String XML = """
            <idea-plugin>
              <actions>
                <action id="Vcs.ShowTabbedFileHistory" class="com.intellij.openapi.vcs.actions.TabbedShowHistoryAction"
                        icon="AllIcons.Vcs.History"/>
                <action id="Vcs.ShowHistoryForRevision" class="com.intellij.openapi.vcs.actions.TabbedShowHistoryForRevisionAction"
                        icon="AllIcons.Vcs.History"/>
            
                <group class="com.intellij.openapi.vcs.actions.VcsActionGroup" id="VcsGroup"/>
            
                <group class="com.intellij.openapi.vcs.actions.VcsGroupsWrapper" id="VcsFileGroupPopup" popup="true"/>
            
                <group id="VersionControlsGroup">
                  <reference ref="VcsFileGroupPopup"/>
                </group>
            
                <group id="GoToChangeMarkerGroup">
                  <separator/>
                  <action id="VcsShowNextChangeMarker" class="com.intellij.openapi.vcs.actions.ShowNextChangeMarkerAction"
                          icon="AllIcons.Actions.NextOccurence"/>
                  <action id="VcsShowPrevChangeMarker" class="com.intellij.openapi.vcs.actions.ShowPrevChangeMarkerAction"
                          icon="AllIcons.Actions.PreviousOccurence"/>
            
                  <add-to-group group-id="GoToMenu" anchor="last"/>
                </group>
              </actions>
            </idea-plugin>
            """;

    @Test
    void testXml() throws IOException, SAXException {
        final SortedMap<String, ActionIDDef> actions = new ActionXmlHandler().parseContent(
				new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8)));
        assertNotNull(actions);
        assertEquals(4, actions.size(), "Had: " + actions);
        final ActionIDDef action = actions.get("VcsShowNextChangeMarker");
        assertEquals("GoToChangeMarkerGroup", action.getGroup(), "Had: " + action);
    }
}