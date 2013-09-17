/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ae97.teamstats.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @version 1.0
 * @author Lord_Ralex
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TeamstatHandler {
}
