/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers;
import Configurations.DatabaseConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

/**
 *
 * @author youp
 */
public class LayeredConfiguration extends Configuration
{
    @JsonProperty
    public DatabaseConfiguration database;
    
    @JsonProperty
    public SwaggerBundleConfiguration swagger;
}
