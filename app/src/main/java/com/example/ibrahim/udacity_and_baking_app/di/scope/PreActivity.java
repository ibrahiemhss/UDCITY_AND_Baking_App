package com.example.ibrahim.udacity_and_baking_app.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
/* this type of scope is per activity so can be per fragment
* per view ,per service etc..*/
/* @notation @Retention will be in run time*/
@Retention(RetentionPolicy.RUNTIME)
/*scope called to determine what kind of element going to specify
 the high scope is the singleton that means the application
  which will be live throughout the entire journey of the app
  @notation @Scope specify saying it is a scope
  */
public @interface PreActivity {
}
