package com.eden.orchid.impl.server.admin.lists;

import com.eden.orchid.api.resources.resourceSource.DefaultResourceSource;
import com.eden.orchid.api.resources.resourceSource.LocalResourceSource;
import com.eden.orchid.api.resources.resourceSource.OrchidResourceSource;
import com.eden.orchid.api.server.admin.AdminList;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class ResourceSourcesList implements AdminList<OrchidResourceSource> {

    private TreeSet<OrchidResourceSource> list;

    @Inject
    public ResourceSourcesList(Set<LocalResourceSource> localResourceSources, Set<DefaultResourceSource> defaultResourceSources) {
        list = new TreeSet<>();
        list.addAll(localResourceSources);
        list.addAll(defaultResourceSources);
    }

    @Override
    public String getKey() {
        return "resourceSources";
    }

    @Override
    public Collection<OrchidResourceSource> getItems() {
        return list;
    }

    @Override
    public String getItemId(OrchidResourceSource item) {
        return item.getClass().getSimpleName();
    }

    @Override
    public OrchidResourceSource getItem(String id) {
        for(OrchidResourceSource item : getItems()) {
            if(id.equals(item.getClass().getSimpleName())) {
                return item;
            }
        }
        return null;
    }
}