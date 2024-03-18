package ui.suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages(value = { "ui.app" })
@IncludeTags(value = { "check" })
public class CheckTestSuite {

}
