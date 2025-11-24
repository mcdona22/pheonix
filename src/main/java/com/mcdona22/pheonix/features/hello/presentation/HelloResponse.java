package com.mcdona22.pheonix.features.hello.presentation;

import java.io.Serializable;

public record HelloResponse(String message, String version) implements Serializable {
}
