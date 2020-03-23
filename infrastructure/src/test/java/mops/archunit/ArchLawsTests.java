package mops.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static mops.archunit.ArchRulesConfig.MOPS_CONTROLLERS;
import static mops.archunit.ArchRulesConfig.MOPS_CONTROLLERS_BASE;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchIgnore;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.stereotype.Controller;


@AnalyzeClasses(importOptions = ImportOption.DoNotIncludeTests.class, packages = "mops")
public class ArchLawsTests {
  //@TODO REMOVE THE REPOSITORY CLASSES FROM THE CONTROLLERS PACKAGE

  @ArchIgnore
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
}
