/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.health.Helpers;

import javax.ws.rs.WebApplicationException;

/**
 *
 * @author C Tiel
 */
public class Range extends ErrorHandler {

    private int min;
    private int max;
    
    public Range(String min, String max){
        
        checkInputValue(min,max); 
    }
       

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
        
    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    private void checkInputValue(String min, String max){
        
        int tempMin = 0;
        int tempMax = 0;     
        
        try
        {
            if (!"".equals(min) || "".equals(max)) {
                   
               tempMin = Integer.parseInt(min);
               tempMax = Integer.parseInt(max);   
               
            }else{
                             
              super.emptyField("Please fill in a min and a max for the range");

            }
        }
        catch (NumberFormatException | WebApplicationException ex)
        {
            
            super.parsingError("Something went wrong while converting the range to an integer.");
        }
        
        if (tempMin > tempMax)
        {
            int tempValueHolder = tempMax;
            tempMin = tempMax;
            tempMax = tempValueHolder;
             
            setMin(tempMax);
            setMax(tempMin);
            
        }else{
            
            setMin(tempMin);
            setMax(tempMax);
        }
            
    }
}
