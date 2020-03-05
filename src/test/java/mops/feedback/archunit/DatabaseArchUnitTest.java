package mops.feedback.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import mops.FeedbackApplication;
import org.springframework.stereotype.Repository;

@AnalyzeClasses(packagesOf = FeedbackApplication.class)
public class DatabaseArchUnitTest {
  @ArchTest
  static final ArchRule repositoriesareindatabasepackage = classes().that()
      .areAnnotatedWith(Repository.class).should().haveSimpleNameEndingWith("Repository");

  @ArchTest
  static final ArchRule dtosareindtopackage =
      classes().that().resideInAPackage("..dto..").should().haveSimpleNameEndingWith("Dto");
}
