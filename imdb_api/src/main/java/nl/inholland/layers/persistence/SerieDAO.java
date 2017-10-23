/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import javax.inject.Inject;
import nl.inholland.layers.model.Serie;
import org.mongodb.morphia.Datastore;

/**
 *
 * @author Jeroen
 */
public class SerieDAO extends BaseDAO<Serie>
{
    @Inject
    public SerieDAO(Datastore ds)
    {
        super(Serie.class, ds);
    }   
}
