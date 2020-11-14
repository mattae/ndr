package org.fhi360.lamis.modules.ndr.config;

import com.foreach.across.modules.hibernate.jpa.repositories.config.EnableAcrossJpaRepositories;
import org.fhi360.lamis.modules.ndr.domain.NDRDomain;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAcrossJpaRepositories(basePackageClasses = {NDRDomain.class})
public class DomainConfiguration {
}
