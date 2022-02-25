package com.qualizeal.community;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.qualizeal.community.layouts.Layout;
import com.qualizeal.community.layouts.Section;
import com.qualizeal.community.layouts.exceptions.DuplicateLayoutsFoundException;
import com.qualizeal.community.layouts.exceptions.LayoutNotFoundException;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.yaml.snakeyaml.error.YAMLException;

public class LayoutParserTest {
    @Test
    public void testParseLayoutNotFound() {
        assertThrows(LayoutNotFoundException.class, () -> (new LayoutParser()).parseLayout("SomeSection", "Layout.yaml"));
    }

    @Test
    public void testParseInvalidLayout() {
        assertThrows(YAMLException.class, () -> (new LayoutParser()).parseLayout("SomeSection", "InvalidLayout.yaml"));
    }

    @Test
    public void testParseLayoutHappyPath() {
        Layout actualParseLayoutResult = (new LayoutParser()).parseLayout("login", "Layout.yaml");
        assertEquals("login", actualParseLayoutResult.getName());
        List<Section> sections = actualParseLayoutResult.getSections();
        assertEquals(3, sections.size());
        assertEquals("default", actualParseLayoutResult.getSection());
        Section getResult = sections.get(1);
        assertEquals("Section(name=MyAppFrame, locators=[{name=button, web=//button}])", getResult.toString());
        Section getResult1 = sections.get(0);
        assertEquals("Section(name=Main, locators=[{name=username, web=//myxpath, mobile=//mobile}, {name=password,"
                + " web=//webuser, ios=//iosuser, android=//androiduser}, {name=signin, web=//webuser, ios=//iosuser,"
                + " android=//androiduser}, {name=iframe#1, web=//iframe}])", getResult1.toString());
        assertEquals("Main", getResult1.getName());
        List<Map<String, String>> locators = getResult1.getLocators();
        assertEquals(4, locators.size());
        assertEquals("MyAppFrame", getResult.getName());
        Section getResult2 = sections.get(2);
        assertEquals(
                "Section(name=Footer, locators=[{name=footerNotes, web=#webuser, ios=//iosuser, android=/" + "/androiduser}])",
                getResult2.toString());
        assertEquals("Footer", getResult2.getName());
        List<Map<String, String>> locators1 = getResult.getLocators();
        assertEquals(1, locators1.size());
        List<Map<String, String>> locators2 = getResult2.getLocators();
        assertEquals(1, locators2.size());
        assertEquals(2, locators1.get(0).size());
        assertEquals(4, locators2.get(0).size());
        assertEquals(4, locators.get(2).size());
        assertEquals(2, locators.get(3).size());
        assertEquals(3, locators.get(0).size());
        assertEquals(4, locators.get(1).size());
    }

    @Test
    public void testParseLayouts() {
        assertTrue((new LayoutParser()).parseAndMergeLayouts().isEmpty());
    }

    @Test
    public void testDuplicateLayouts() {
        assertThrows(DuplicateLayoutsFoundException.class, () -> new LayoutParser().parseAndMergeLayouts("Layout.yaml", "Layouts.yaml"));
    }
}

