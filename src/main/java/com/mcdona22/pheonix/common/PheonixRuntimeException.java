package com.mcdona22.pheonix.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PheonixRuntimeException extends RuntimeException {
    final ApiError error;
}
