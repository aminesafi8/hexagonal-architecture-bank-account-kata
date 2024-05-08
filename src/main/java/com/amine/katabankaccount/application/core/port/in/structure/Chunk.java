package com.amine.katabankaccount.application.core.port.in.structure;

import java.util.Collection;

public record Chunk<T>(Collection<T> content, long totalElements) {
}
