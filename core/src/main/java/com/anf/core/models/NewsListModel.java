package com.anf.core.models;

import java.util.Collections;
import java.util.Iterator;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.service.component.annotations.Reference;

@Model(adaptables = Resource.class)
public class NewsListModel {

	@ValueMapValue
	@Optional
	private String path;

	@Inject
	private ResourceResolver resourceResolver;

	public Iterator<Resource> getChildren() {
		if (this.path == null || this.path.isEmpty()) { // Use StringUtils.isBlank() if you can.
			return Collections.emptyIterator();
		}

		final Resource resource = this.resourceResolver.getResource(this.path);

		if (resource == null) {
			return Collections.emptyIterator();
		}

		return resource.listChildren();
	}

}