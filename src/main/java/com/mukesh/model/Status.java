package com.mukesh.model;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;

import com.mukesh.exception.DataNotFoundException;

/**
 * @author Mukesh
 *
 */
public enum Status {
	ONLINE("Online"), BLOCKED("Blocked"), AVAILABLE("Available");

	private static Map<String, Status> FORMAT_MAP = Stream.of(Status.values())
			.collect(Collectors.toMap(s -> s.value.toUpperCase(), Function.identity()));

	private String value;

	Status(String value) {
		this.value = value;
	}

	@JsonCreator // This is the factory method and must be static
	public static Status fromString(String string) {
		return Optional.ofNullable(FORMAT_MAP.get(string.toUpperCase()))
				.orElseThrow(() -> new DataNotFoundException("Status name --" + string
						+ "  is Invalid. It should contains value from this -- (Online, Blocked ,Available)"));
	}

	public String getValue() {
		return this.value;
	}
}
