package com.seikim.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "s3")
@Data
public class AwsS3Properties {
	private String accessKeyId;
	private String secretAccessKey;
	private String region;
	private String bucket;
	private String prefix;
	private long maxSizeMb;
	private String allowedFileTypes;
}
