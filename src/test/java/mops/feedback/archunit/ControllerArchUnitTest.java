package mops.feedback.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import mops.feedback.FeedbackApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@AnalyzeClasses(packagesOf = FeedbackApplication.class)
public class ControllerArchUnitTest {
  @ArchTest
  static final ArchRule annotationofcontrollers = classes().that()
      .areAnnotatedWith(Controller.class).should().beAnnotatedWith(RequestMapping.class);
}
