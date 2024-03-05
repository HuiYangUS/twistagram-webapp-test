package demo.ui.suites;

import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import demo.ui.metadata.DriverDataTest;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.IncludeTags;

@Suite
@SuiteDisplayName(value = "Local Edge Driver Test")
@SelectClasses(value = { DriverDataTest.class })
@IncludeTags(value = { "local & edge" })
public class LocalEdgeDriverInfoTestSuite {

}
