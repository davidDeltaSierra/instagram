package br.com.instagram.integration;

import br.com.instagram.config.Hash;
import br.com.instagram.integration.pagination.Variable;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder(builderClassName = "Builder")
public class QueryExecutor<T> {
    Map<String, String> cookies;
    Variable variable;
    TypeReference<T> typeReference;
    Hash hash;
    Map<String, String> queryVariables;
    String entryPoint;
}
