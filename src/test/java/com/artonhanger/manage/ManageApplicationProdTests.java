package com.artonhanger.manage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "prod")
@ActiveProfiles("prod")
@SpringBootTest
class ManageApplicationProdTests {
	@Value("${cloud.aws.credentials.accessKey}")
	String cloudAwsCredentialsAccessKey;

	@Value("${cloud.aws.credentials.secretKey}")
	String cloudAwsCredentialsSecretKey;

	@Value("${cloud.aws.s3.bucket}")
	String cloudAwsS3Bucket;

	@Value("${cloud.aws.cloudFront.domain}")
	String cloudAwsCloudFrontDomain;

	@Value("${cloud.aws.domain-uri}")
	String cloudAwsDomainUri;

	@Value("${spring.mail.host}")
	String springMailHost;

	@Value("${spring.mail.port}")
	String springMailPort;

	@Value("${spring.mail.username}")
	String springMailUsername;

	@Value("${spring.mail.password}")
	String springMailPassword;

	@Value("${thirdPartyProperties.imweb-api.key}")
	String thirdPartyPropertiesImwebApiKey;

	@Value("${thirdPartyProperties.imweb-api.secret}")
	String thirdPartyPropertiesImwebApiSecret;

	@Test
	void contextLoads() {
	}

	@Test
	void 프로퍼티가_기본값이면_실패() {
		assertNotEquals("CLOUD_AWS_CREDENTIALS_ACCESS_KEY", cloudAwsCredentialsAccessKey);
		assertNotEquals("CLOUD_AWS_CREDENTIALS_SECRET_KEY", cloudAwsCredentialsSecretKey);
		assertNotEquals("CLOUD_AWS_S3_BUCKET", cloudAwsS3Bucket);
		assertNotEquals("CLOUD_AWS_CLOUD_FRONT_DOMAIN", cloudAwsCloudFrontDomain);
		assertNotEquals("CLOUD_AWS_DOMAIN_URI", cloudAwsDomainUri);
		assertNotEquals("SPRING_MAIL_HOST", springMailHost);
		assertNotEquals("SPRING_MAIL_PORT", springMailPort);
		assertNotEquals("SPRING_MAIL_USERNAME", springMailUsername);
		assertNotEquals("SPRING_MAIL_PASSWORD", springMailPassword);
		assertNotEquals("THIRD_PARTY_PROPERTIES_IMWEB_API_KEY", thirdPartyPropertiesImwebApiKey);
		assertNotEquals("THIRD_PARTY_PROPERTIES_IMWEB_API_SECRET", thirdPartyPropertiesImwebApiSecret);
	}

}
