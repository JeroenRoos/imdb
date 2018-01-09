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
    @Override
    public boolean authorize(User authenticator, String role)
    {
        return authenticator.hasRole(role);
    }
}
