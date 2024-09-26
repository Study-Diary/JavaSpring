package com.seikim.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@RequiredArgsConstructor
@Configuration
public class AwsS3Config {
	private final AwsS3Properties awsS3Properties;

	@Bean
	public S3Client s3Client() {
		Region region = Region.of(awsS3Properties.getRegion());
		StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(
				AwsBasicCredentials.create(awsS3Properties.getAccessKeyId(), awsS3Properties.getSecretAccessKey()));

		return S3Client.builder()
				.region(region)
				.credentialsProvider(staticCredentialsProvider)
				.build();
	}
}
