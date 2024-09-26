package com.seikim.spring;

import java.io.IOException;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Getter
public enum FileFixture {
	FILE_1(new MockMultipartFile("FILE_1", new byte[] {})),
	FILE_2(new MockMultipartFile("FILE_2", new byte[] {}));

	private MultipartFile multipartFile;

	FileFixture(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public RequestBody toRequestBody() throws IOException {
		return RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize());
	}

	public PutObjectRequest toPutObjectRequest() {
		return PutObjectRequest.builder()
				.bucket("my-bucket")
				.key("my-key")
				.contentType(multipartFile.getContentType())
				.build();
	}
}
