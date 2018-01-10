/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import io.dropwizard.auth.Authorizer;
import nl.inholland.layers.model.User;

/**
 *
 * @author Jeroen
 */
public class AuthorizationService extends BaseService implements Authorizer<User>
{
    // Check and returns if the user has the role to perform a request
    // String role represents the RolesAllowed("") from the resource classes
    @Override
    public boolean authorize(User authenticator, String role)
    {
        return authenticator.hasRole(role);
    }
}
