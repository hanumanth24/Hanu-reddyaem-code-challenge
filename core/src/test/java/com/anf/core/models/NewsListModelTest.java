package com.anf.core.models;


import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
public class NewsListModelTest {
	private Resource resource;
	private NewsListModel newsListModel;
	
	@BeforeEach
    public void setup(AemContext context) throws Exception {
		context.build().resource("/content/myResource", "path", "/content/test").commit();
		newsListModel=context.currentResource("/content/myResource").adaptTo(NewsListModel.class);
	}
	 @Test
	    void testGetMessage() throws Exception {
	        // some very basic junit tests
	       Assert.assertEquals(Collections.emptyIterator(), newsListModel.getChildren());
	    }
}
