package org.fhi360.lamis.modules.ndr;

import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.annotations.AcrossDepends;
import com.foreach.across.core.context.configurer.ComponentScanConfigurer;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import org.lamisplus.modules.base.LamisModule;

@AcrossDepends(required = AcrossHibernateJpaModule.NAME)
public class LamisNDRModule extends AcrossModule {
    public static final String NAME = "FHINDRModule";

    public LamisNDRModule() {
        super();
        addApplicationContextConfigurer(
                new ComponentScanConfigurer(getClass().getPackage().getName() + ".web",
                        getClass().getPackage().getName() + ".mapper", getClass().getPackage().getName() + ".service"));
    }

    @Override
    public String getName() {
        return NAME;
    }
}
