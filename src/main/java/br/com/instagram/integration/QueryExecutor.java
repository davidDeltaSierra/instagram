package br.com.instagram.integration;

import br.com.instagram.config.Hash;
import br.com.instagram.integration.pagination.Variable;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import lombok.Value;
import org.openqa.selenium.Cookie;

import java.util.Set;

@Value
@Builder(builderClassName = "Builder")
public class QueryExecutor<T> {
    Set<Cookie> cookies;
    Variable variable;
    TypeReference<T> typeReference;
    Hash hash;
    Set<String> queryVariables;
    String entryPoint;
}
