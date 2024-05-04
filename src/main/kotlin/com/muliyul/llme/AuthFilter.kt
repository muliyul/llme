package com.muliyul.llme

import io.dropwizard.auth.*
import io.dropwizard.auth.oauth.*
import io.dropwizard.core.setup.*
import jakarta.inject.*
import jakarta.ws.rs.container.*
import jakarta.ws.rs.ext.*
import jakarta.ws.rs.ext.Provider
import org.eclipse.jetty.server.Authentication.*
import org.glassfish.jersey.server.filter.*
import java.security.Principal
import java.util.*


@Provider
class OAuthDynamicFeature @Inject constructor(
	environment: Environment
) : AuthDynamicFeature(
	OAuthCredentialAuthFilter.Builder<Principal>()
		.setAuthenticator {
			Optional.ofNullable(Principal { it })
		}
		.setAuthorizer { user, role, ctx -> true }
		.setPrefix("Bearer")
		.buildAuthFilter()
) {
	init {
		environment.jersey().register(RolesAllowedDynamicFeature::class.java)
		environment.jersey().register(AuthValueFactoryProvider.Binder(Principal::class.java))
	}
}
