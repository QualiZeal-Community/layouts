package com.qualizeal.community.layouts;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import com.qualizeal.community.layouts.exceptions.NameUndefinedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SectionTest {
    @Test
    public void testEnrichGetType() {
        Section section = new Section();
        assertSame(section, section.enrich());
    }

    @Test
    public void testEnrichWhenNameUndefined() {
        ArrayList<Map<String, String>> hashMapList = new ArrayList<>();
        hashMapList.add(new HashMap<>());
        assertThrows(NameUndefinedException.class, () -> (new Section("Name", hashMapList)).enrich());
    }

    @Test
    public void testEnrichHappyPath() {
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("name", "42");

        ArrayList<Map<String, String>> hashMapList = new ArrayList<>();
        hashMapList.add(stringStringMap);
        Section section = new Section("Name", hashMapList);
        assertSame(section, section.enrich());
    }
}

