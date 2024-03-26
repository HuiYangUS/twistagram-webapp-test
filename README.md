### TwistaGram Testing Framework
---
- Please create a folder named `configs` under the `resources` package.
- Then create a `config.properties` file under this folder.
- In order to test `TwistaGram`, a local chrome profile must be setup and all chrome browsers must be closed (Legacy - No longer required).
- Please copy all the content in `demo-config.properties` and paste over the ENTIRE	content of `config.properties`, then fill out the last part with your personal information.
- `Chrome For Test` is highly recommended (Not required) in order to avoid updating `Selenium` regularly. 
- Current `Selenium - 4.18.1` uses DevTools `v120`, `v121`, `v122`.
---
- Hui Yang
- 3-24-2024

### Maven
---
- Smoke Test: `mvn clean test -Dtest=SmokeTestSuite -Dheadless=true`
- Regression Test: `mvn clean -Dsurefire.rerunFailingTestsCount=0 test -Dtest=RegressionTestSuite -Dheadless=true`
