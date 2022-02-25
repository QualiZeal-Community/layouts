package com.qualizeal.community.layouts;

import com.qualizeal.community.layouts.enums.Types;
import com.qualizeal.community.layouts.exceptions.LocatorNotFoundException;
import com.qualizeal.community.layouts.exceptions.SectionNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@NoArgsConstructor
public class Layout {
    private String name;
    private List<Section> sections;
    private String section = "default";
    
    public Layout(String name, List<Section> sections) {
        this.setName(name);
        this.setSections(sections);
    }

    public void section(String section) {
        Optional<Section> givenContext = sections.stream().filter(s -> s.getName().equalsIgnoreCase(section)).findAny();
        givenContext.ifPresent(s -> this.section = (s.getName()));
    }

    private Section getCurrentSection() {
        Optional<Section> currentSection = sections.stream()
                .filter(s -> s.getName().equalsIgnoreCase(this.section))
                .findFirst();
        if(currentSection.isPresent()){
            return currentSection.get();
        } else {
            throw new SectionNotFoundException(this.section);
        }
    }

    private Map<String, String> getLocator(Section section, String name) {
        return section.getLocators().stream()
                .filter(l -> l.get("name").equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new LocatorNotFoundException(section.getName(),name));
    }

    public Map<String, String> get(final String name) {
        return getLocator(getCurrentSection().enrich(), name);
    }


    public String getBy(String name, Types type) {
        return get(name).get(type.toString());
    }
}
