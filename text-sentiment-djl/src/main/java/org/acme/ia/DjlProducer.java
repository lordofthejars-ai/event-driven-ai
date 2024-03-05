package org.acme.ia;

import java.io.IOException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import ai.djl.MalformedModelException;
import ai.djl.huggingface.translator.TextClassificationTranslatorFactory;
import ai.djl.modality.Classifications;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

public class DjlProducer {

        @ConfigProperty(name = "model.location")
        String model;

        @Inject
        DjlHealthCheck djlHealthCheck;

        @Inject
        Logger log;

        @Produces
        @BertBase
        public ZooModel<String, Classifications> createBert()
                        throws ModelNotFoundException, MalformedModelException, IOException {

                final Criteria<String, Classifications> criteria = Criteria.builder()
                                .setTypes(String.class, Classifications.class)
                                .optModelUrls(model)
                                .optEngine("PyTorch")
                                .optTranslatorFactory(new TextClassificationTranslatorFactory())
                                .optProgress(djlHealthCheck)
                                .build();

                final ZooModel<String, Classifications> zooModel = criteria.loadModel();

                log.info("Model %s loaded.".formatted(model));
                return zooModel;
        }
}
