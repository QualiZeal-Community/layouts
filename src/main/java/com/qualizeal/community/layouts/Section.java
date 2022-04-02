package com.qualizeal.community.layouts;

import com.qualizeal.community.layouts.enums.Types;
import com.qualizeal.community.layouts.exceptions.NameUndefinedException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Section {
    String name;
    List<Map<String, String>> locators = new ArrayList<>();

    public Section enrich() {
        for (Map<String, String> locatorMap: locators) {
            if(!locatorMap.containsKey("name")) {
                throw new NameUndefinedException();
            }
            if(!locatorMap.containsKey(Types.WEB.getType())) {
                locatorMap.put(Types.WEB.getType(), null);
            }
            if(locatorMap.containsKey(Types.MOBILE.getType())) {
                if(!locatorMap.containsKey(Types.ANDROID.getType())) {
                    locatorMap.putIfAbsent(Types.ANDROID.getType(), locatorMap.get(Types.MOBILE.getType()));
                }
                if(!locatorMap.containsKey(Types.IOS.getType())) {
                    locatorMap.putIfAbsent(Types.IOS.getType(), locatorMap.get(Types.MOBILE.getType()));
                }
            } else {
                locatorMap.put(Types.MOBILE.getType(), null);
            }
        }
        return this;
    }
}
