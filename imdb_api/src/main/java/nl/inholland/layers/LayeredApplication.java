/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class LayeredApplication extends Application<LayeredConfiguration>
{
    private GuiceBundle<LayeredConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception
    {
        new LayeredApplication().run(new String[] { "server" } );
    }

    @Override
    public void initialize( final Bootstrap<LayeredConfiguration> bootstrap)
    {
        configureGuice();
        bootstrap.addBundle(guiceBundle);
    }

    private void configureGuice()
    {
        guiceBundle = GuiceBundle.<LayeredConfiguration>newBuilder()
            .addModule( new LayeredModule() )
            .enableAutoConfig(getClass().getPackage().getName())
            .setConfigClass(LayeredConfiguration.class)
            .build();
    }

    @Override
    public void run( final LayeredConfiguration configuration, 
                     final Environment environment) throws Exception
    {        
        
    }
}