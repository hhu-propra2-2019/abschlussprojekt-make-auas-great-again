package mops.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static mops.archunit.ArchRulesConfig.MOPS;
import static mops.archunit.ArchRulesConfig.MOPS_CONTROLLERS;
import static mops.archunit.ArchRulesConfig.MOPS_CONTROLLERS_BASE;
import static mops.archunit.ArchRulesConfig.MOPS_SERVICES;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchIgnore;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


@AnalyzeClasses(importOptions = ImportOption.DoNotIncludeTests.class, packages = "mops")
public class ArchLawsTests {

  //@TODO REMOVE THE REPOSITORY CLASSES FROM THE CONTROLLERS PACKAGE

  @ArchTest
  static final ArchRule ALL_CONTROLLERS_SHOULD_RESIDE_IN_CONTROLLERS_PACKAGE = classes()
      .that()
      .areAnnotatedWith(Controller.class)
      .and()
      .resideOutsideOfPackage(MOPS_CONTROLLERS)
      .should()
      .notBeAnnotatedWith(Controller.class);

  //@TODO REMOVE THE REPOSITORY CLASSES FROM THE CONTROLLERS PACKAGE

  @ArchIgnore
  @ArchTest
  static final ArchRule ALLCLASSES_IN_CONTROLLERSPACKAGE_SHOULD_BE_A_CONTROLLER = classes()
      .that()
      .resideInAPackage(MOPS_CONTROLLERS_BASE)
      .and()
      .areNotAnnotatedWith(WebMvcTest.class)
      .should()
      .beAnnotatedWith(Controller.class);

  @ArchTest
  static final ArchRule CYCLES_INSIDE_THE_PACKAGES_TEST = slices()
      .matching(MOPS + "..")
      .should()
      .beFreeOfCycles();

  //@TODO ADD SERVICES PACKAGE AND INCLUDED ALL SERVICES THERE

  @ArchIgnore
  @ArchTest
  static final ArchRule SERVICES_SHOULD_ANNOTATED_WITH_SERVICE = classes()
      .that()
      .haveSimpleNameContaining("Service")
      .and()
      .resideInAPackage(MOPS_SERVICES)
      .should()
      .beAnnotatedWith(Service.class);
}

