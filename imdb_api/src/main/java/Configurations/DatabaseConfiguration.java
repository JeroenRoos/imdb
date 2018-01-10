/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import static javassist.CtMethod.ConstParameter.string;

/**
 *
 * @author Remy
 */
public class DatabaseConfiguration
{
    @JsonProperty
    public String mongoURI;
    
    @JsonProperty
    public String dbName;
}
