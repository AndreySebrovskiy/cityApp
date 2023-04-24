package com.jdms.grad.cities.application.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Builder
@Jacksonized
@Data
public class ObjectId {
  private UUID id;
}
