package dev.stratospheric;

import dev.stratospheric.cdk.ApplicationEnvironment;
import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;

public class StratosphericCognitoApp {

  public static void main(final String[] args) {
    App app = new App();

    String environmentName = (String) app.getNode().tryGetContext("environmentName");
    requireNonEmpty(environmentName, "context variable 'environmentName' must not be null");

    String applicationName = (String) app.getNode().tryGetContext("applicationName");
    requireNonEmpty(applicationName, "context variable 'applicationName' must not be null");

    String accountId = (String) app.getNode().tryGetContext("accountId");
    requireNonEmpty(accountId, "context variable 'accountId' must not be null");

    String region = (String) app.getNode().tryGetContext("region");
    requireNonEmpty(region, "context variable 'region' must not be null");

    String applicationUrl = (String) app.getNode().tryGetContext("applicationUrl");
    requireNonEmpty(applicationUrl, "context variable 'applicationUrl' must not be null");

    String loginPageDomainPrefix = (String) app.getNode().tryGetContext("loginPageDomainPrefix");
    requireNonEmpty(loginPageDomainPrefix, "context variable 'loginPageDomainPrefix' must not be null");

    Environment awsEnvironment = makeEnv(accountId, region);

    ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment(
      applicationName,
      environmentName
    );

    new StratosphericCognitoStack(app, "cognito", awsEnvironment, applicationEnvironment, new StratosphericCognitoStack.CognitoInputParameters(
      applicationName,
      applicationUrl,
      loginPageDomainPrefix));

    app.synth();
  }

  static Environment makeEnv(String account, String region) {
    return Environment.builder()
      .account(account)
      .region(region)
      .build();
  }

  static void requireNonEmpty(String string, String message) {
    if (string == null || string.isBlank()) {
      throw new IllegalArgumentException(message);
    }
  }
}
