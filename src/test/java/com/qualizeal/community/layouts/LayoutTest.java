package com.qualizeal.community.layouts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.beust.ah.A;
import com.qualizeal.community.layouts.enums.Types;
import com.qualizeal.community.layouts.exceptions.LocatorNotFoundException;
import com.qualizeal.community.layouts.exceptions.SectionNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class LayoutTest {
    @Test
    public void testConstructor() {
        Layout actualLayout = new Layout("Name", new ArrayList<>());

        assertEquals("Name", actualLayout.getName());
        assertTrue(actualLayout.getSections().isEmpty());
        assertEquals("default", actualLayout.getSection());
    }

    @Test
    public void testSection() {
        Layout layout = new Layout();
        layout.setSections(new ArrayList<>());
        layout.section("Section");
        assertTrue(layout.getSections().isEmpty());
        assertEquals("default", layout.getSection());
    }

    @Test
    public void testSectionContents() {
        ArrayList<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section("Name", new ArrayList<>()));

        Layout layout = new Layout();
        layout.setSections(sectionList);
        layout.section("Section");
        assertEquals(1, layout.getSections().size());
        assertEquals("default", layout.getSection());
    }

    @Test
    public void testSectionAccess() {
        ArrayList<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section("Name", new ArrayList<>()));

        Layout layout = new Layout();
        layout.setSections(sectionList);
        layout.section("name");
        assertEquals("Name", layout.getSection());
    }

    @Test
    public void testGet() {
        Layout layout = new Layout();
        layout.setSections(new ArrayList<>());
        assertThrows(SectionNotFoundException.class, () -> layout.get("Name"));
    }

    @Test
    public void testGetMissingSection() {
        ArrayList<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section("Name", new ArrayList<>()));

        Layout layout = new Layout();
        layout.setSections(sectionList);
        assertThrows(SectionNotFoundException.class, () -> layout.get("Name"));
    }

    @Test
    public void testGetBy() {
        Layout layout = new Layout();
        layout.setSections(new ArrayList<>());
        assertThrows(SectionNotFoundException.class, () -> layout.getBy("Name", Types.WEB));
    }

    @Test
    public void testGetByMissingSection() {
        ArrayList<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section("Name", new ArrayList<>()));

        Layout layout = new Layout();
        layout.setSections(sectionList);
        assertThrows(SectionNotFoundException.class, () -> layout.getBy("Name", Types.WEB));
    }

    @Test
    public void testGetDefaultMobileLocator() {
        ArrayList<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section("Name", new ArrayList<>()));
        Map<String,String> locatorMap = new HashMap<>();
        locatorMap.put("name", "testLocator");
        locatorMap.put("mobile", "mobileLocator");
        sectionList.get(0).getLocators().add(locatorMap);
        sectionList.get(0).enrich();
        Layout layout = new Layout();
        layout.setSections(sectionList);
        assertEquals(5, sectionList.get(0).getLocators().get(0).size());
        layout.setSection("Name");
        assertEquals("mobileLocator", layout.get("testLocator").get("ios"));
    }

    @Test
    public void testGetDefaultMissingLocator() {
        ArrayList<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section("Name", new ArrayList<>()));
        Map<String,String> locatorMap = new HashMap<>();
        locatorMap.put("name", "testLocator");
        locatorMap.put("mobile", "mobileLocator");
        sectionList.get(0).getLocators().add(locatorMap);
        sectionList.get(0).enrich();
        Layout layout = new Layout();
        layout.setSections(sectionList);
        assertEquals(5, sectionList.get(0).getLocators().get(0).size());
        layout.setSection("Name");
        assertThrows(LocatorNotFoundException.class, () -> layout.get("NotHereLocator"));
    }
}

