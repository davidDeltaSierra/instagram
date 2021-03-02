package br.com.instagram.config;

import br.com.instagram.integration.pagination.Variable;
import br.com.instagram.util.BeanUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class VariableComponent {
    private Variable variable;

    public static Variable getVariableFromRequest() {
        VariableComponent variableComponent = BeanUtil.getBean(VariableComponent.class);
        return variableComponent.getVariable();
    }
}
