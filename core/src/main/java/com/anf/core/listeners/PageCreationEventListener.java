package com.anf.core.listeners;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(service = EventListener.class, immediate = true)
public class PageCreationEventListener implements EventListener {
	@Reference
	private ResourceResolverFactory resolverFactory;

	/**
	 * Resource Resolver
	 */
	private ResourceResolver resolver;

	@Reference
	private SlingRepository repository;

	/**
	 * Session object
	 */
	private Session session;

	/**
	 * Activate method to initialize stuff
	 */
	@Activate
	protected void activate(ComponentContext componentContext) {

		try {

			/**
			 * This map will be used to get session via getServiceResourceResolver() method
			 */
			Map<String, Object> params = new HashMap<>();

			/**
			 * Adding the subservice name in the param map
			 */
			params.put(ResourceResolverFactory.SUBSERVICE, "pageCreationService");

			/**
			 * Getting resource resolver from the service factory
			 */
			resolver = resolverFactory.getServiceResourceResolver(params);

			/**
			 * Adapting the resource resolver to session object
			 */
			session = resolver.adaptTo(Session.class);

			/**
			 * Adding the event listener
			 * 
			 */
			session.getWorkspace().getObservationManager().addEventListener(this, Event.NODE_ADDED,
					"/content/anf-code-challenge/us/en", true, null, null, false);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Deactivate
	protected void deactivate() {

		if (session != null) {

			session.logout();
		}
	}

	@Override
	public void onEvent(EventIterator events) {
		try {

			while (events.hasNext()) {
				Event event = events.nextEvent();
				Map<String, Object> properties = new HashMap<>();

				properties.put("pageCreated:", true);

				resolver.create(resolver.getResource(event.getPath()), event.getPath(), properties);
				session.save();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}