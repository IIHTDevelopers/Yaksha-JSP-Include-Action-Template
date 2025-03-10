package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.yaksha.assignment.controller.GreetingController;
import com.yaksha.assignment.utils.CustomParser;

public class GreetingControllerTest {

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testGreetUserWithValidInput() throws Exception {
		// Mock HttpServletRequest, HttpSession, and Model using Mockito
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		Model model = mock(Model.class);

		// Setup mock behavior
		when(request.getRequestURI()).thenReturn("/greet");
		when(request.getMethod()).thenReturn("GET");

		// Create an instance of the controller
		GreetingController controller = new GreetingController();

		// Call the method with mock request, session, and model
		String viewName = controller.greetUser("John", 25, model);

		// Verify the view name is "greeting"
		boolean isGreetingViewReturned = "greeting".equals(viewName);

		// Verify the model contains the correct greeting message
		verify(model).addAttribute("greetingMessage", "Hello, John. You are 25 years old!");

		// Auto-grading with yakshaAssert
		yakshaAssert(currentTest(), isGreetingViewReturned, businessTestFile);
	}

	@Test
	public void testGreetUserWithModelAttributes() throws Exception {
		// Mock HttpServletRequest, HttpSession, and Model using Mockito
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		Model model = mock(Model.class);

		// Create an instance of the controller
		GreetingController controller = new GreetingController();

		// Call the method with mock request, session, and model
		controller.greetUser("Alice", 30, model);

		// Verify that model attributes are added
		verify(model).addAttribute("greetingMessage", "Hello, Alice. You are 30 years old!");

		// Auto-grading with yakshaAssert
		yakshaAssert(currentTest(), true, businessTestFile);
	}

	@Test
	public void testJspTagsAndHtmlTagClosureInIndexJsp() throws Exception {
		String filePath = "src/main/webapp/index.jsp";

		// Check for form submission and input elements in index.jsp
		boolean hasFormTag = CustomParser.checkJspTagPresence(filePath, "<form");
		boolean hasInputTags = CustomParser.checkJspTagPresence(filePath, "<input");

		// Run auto-grading using yakshaAssert
		yakshaAssert(currentTest(), hasFormTag && hasInputTags, businessTestFile);
	}

	@Test
	public void testJspTagsAndHtmlTagClosureInGreetingJsp() throws Exception {
		String filePath = "src/main/webapp/WEB-INF/views/greeting.jsp";

		// Ensure that greeting.jsp page contains ${greetingMessage} and properly closes
		// HTML tags
		boolean hasGreetingMessageTag = CustomParser.checkJspTagPresence(filePath, "${greetingMessage}");

		// Verify the page includes the correct JSP expression for greeting message
		boolean hasHtmlTagClosure = CustomParser.checkJspTagPresence(filePath, "</body>"); // check for proper closing
																							// of
																							// body tag

		// Run auto-grading using yakshaAssert
		yakshaAssert(currentTest(), hasGreetingMessageTag && hasHtmlTagClosure, businessTestFile);
	}

	@Test
	public void testJspTagsAndHtmlTagClosureInFooterJsp() throws Exception {
		String filePath = "src/main/webapp/WEB-INF/views/footer.jsp";

		// Ensure that footer.jsp contains ${currentYear} for dynamic year and properly
		// closes HTML tags
		boolean hasCurrentYearTag = CustomParser.checkJspTagPresence(filePath, "${currentYear}");

		// Verify the page includes the correct JSP expression for current year
		boolean hasHtmlTagClosure = CustomParser.checkJspTagPresence(filePath, "</footer>"); // check for proper closing
																								// of footer tag

		// Run auto-grading using yakshaAssert
		yakshaAssert(currentTest(), hasCurrentYearTag && hasHtmlTagClosure, businessTestFile);
	}
}
