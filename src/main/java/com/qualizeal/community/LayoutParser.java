package com.qualizeal.community;

import com.qualizeal.community.layouts.Layout;
import com.qualizeal.community.layouts.exceptions.DuplicateLayoutsFoundException;
import com.qualizeal.community.layouts.exceptions.LayoutNotFoundException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LayoutParser {

    public Layout parseLayout(String layoutName, String yamlFile) {
        Layout layout = parseAndMergeLayouts(yamlFile).getOrDefault(layoutName, null);
        if(Objects.nonNull(layout)) {
            return layout;
        } else {
            throw new LayoutNotFoundException(layoutName, yamlFile);
        }
    }

    public Map<String, Layout> parseLayouts(String yamlFile) {
        Yaml yaml = new Yaml(new Constructor(Layout.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(yamlFile);
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        List<Layout> layouts = new ArrayList<>();
        yaml.loadAll(inputStream).forEach(o -> layouts.add((Layout) o));
        return layouts.stream().collect(Collectors.toMap(Layout::getName, Function.identity()));
    }

    public Map<String, Layout> parseAndMergeLayouts(String... yamlFiles) {
        Map<String, Layout> layouts = new HashMap<>();
        for(String file: yamlFiles) {
            Map<String, Layout> tempLayouts = parseLayouts(file);
            tempLayouts.forEach((key, value) -> {
                if (!layouts.containsKey(key)) {
                    layouts.put(key, value);
                } else {
                    throw new DuplicateLayoutsFoundException(key);
                }
            });
        }
        return layouts;
    }
}

