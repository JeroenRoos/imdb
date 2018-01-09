/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import nl.inholland.health.DatabaseHealthCheck;
import nl.inholland.layers.model.User;
import nl.inholland.layers.service.AuthenticationService;
import nl.inholland.layers.service.AuthorizationService;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class LayeredApplication extends Application<LayeredConfiguration>
{
    private GuiceBundle<LayeredConfiguration> guiceBundle;
    //private SwaggerBundle<LayeredConfiguration> swaggerBundle;

    public static void main(String[] args) throws Exception
    {
        new LayeredApplication().run(new String[] { "server" } );
        //(args);
        //(new String[] { "server" } );
    }

    @Override
    public void initialize( final Bootstrap<LayeredConfiguration> bootstrap)
    {
        configureGuice();
        bootstrap.addBundle(guiceBundle);
        
        //configureSwagger();
        //bootstrap.addBundle(swaggerBundle);
    }

    private void configureGuice()
    {
        guiceBundle = GuiceBundle.<LayeredConfiguration>newBuilder()
            .addModule( new LayeredModule() )
            .enableAutoConfig(getClass().getPackage().getName())
            .setConfigClass(LayeredConfiguration.class)
            .build();
    }
    
    
    /* private void configureSwagger()
    {
        swaggerBundle = new SwaggerBundle<LayeredConfiguration>() 
            {
                @Override
                protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(LayeredConfiguration configuration) 
                {
                    return configuration.swagger;
                }
            };
    } */

    @Override
    public void run( final LayeredConfiguration configuration, 
                     final Environment environment) throws Exception
    {        
        environment.healthChecks().register( 
                "database", 
                guiceBundle.getInjector().getInstance( 
                        DatabaseHealthCheck.class ) );
        
        // Dit is een letterlijke COPY PASTE uit het stappenplan van Nelleke, maar het werkt niet
        // Werkelijk geen idee waarom
        /* environment.jersey().register( new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                    .setAuthenticator( 
                            guiceBundle.getInjector().getInstance( 
                                    AuthenticationService.class ) )
                    .setAuthorizer( 
                            guiceBundle.getInjector().getInstance( 
                                    AuthorizationService.class ) )
                    .setRealm( "IMDb API" )
                    .buildAuthFilter()));
        
        environment.jersey().register( new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register( RolesAllowedDynamicFeature.class); */
    }
}