/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.health.Helpers;

import nl.inholland.health.Helpers.ErrorHandler;
import com.google.inject.Module;
import io.dropwizard.auth.AuthValueFactoryProvider.Binder;
import nl.inholland.layers.service.BaseService;

/**
 *
 * @author C Tiel
 */
public class ErrorHandlerModule implements Module {
 public void configure(Binder binder) {
    binder.bind(ErrorHandler.class).to(BaseService.class);
  }

    @Override
    public void configure(com.google.inject.Binder binder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}