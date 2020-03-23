package mops.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static mops.archunit.ArchRulesConfig.MOPS;
import static mops.archunit.ArchRulesConfig.MOPS_CONTROLLERS;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;


@AnalyzeClasses(importOptions = ImportOption.DoNotIncludeTests.class, packages = "mops")
public class ArchLawsTests {


  @ArchTest
  static final ArchRule ALL_CONTROLLERS_SHOULD_RESIDE_IN_CONTROLLERS_PACKAGE = classes()
      .that()
      .areAnnotatedWith(Controller.class)
      .and()
      .resideOutsideOfPackage(MOPS_CONTROLLERS)
      .should()
      .notBeAnnotatedWith(Controller.class);

  @ArchTest
  static final ArchRule CYCLES_INSIDE_THE_PACKAGES_TEST = slices()
      .matching(MOPS + "..")
      .should()
      .beFreeOfCycles();
}


