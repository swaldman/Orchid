package com.eden.orchid.options.impl;

import com.eden.orchid.AutoRegister;
import com.eden.orchid.JSONElement;
import com.eden.orchid.options.Option;

@AutoRegister
public class ResourcesOption implements Option {

    private String[] dataExtensions = new String[] {"yml", "yaml", "json"};

    @Override
    public String getFlag() {
        return "resourcesDir";
    }

    @Override
    public JSONElement parseOption(String[] options) {
        return new JSONElement(options[1]);
    }

    @Override
    public JSONElement getDefaultValue() {
        return null;
    }

    @Override
    public int priority() {
        return 90;
    }

    @Override
    public int optionLength() {
        return 2;
    }
}