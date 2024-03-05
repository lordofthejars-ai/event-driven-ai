package org.acme.ia;

import java.io.IOException;

import ai.djl.MalformedModelException;
import ai.djl.huggingface.translator.TextClassificationTranslatorFactory;
import ai.djl.modality.Classifications;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import jakarta.enterprise.inject.Produces;

public class DjlProducer {

    @Produces
    @BertBase
    public ZooModel<String, Classifications> createBert() throws ModelNotFoundException, MalformedModelException, IOException {

        Criteria<String, Classifications> criteria =
                Criteria.builder()
                        .setTypes(String.class, Classifications.class)
                        .optModelUrls(
                                "djl://ai.djl.huggingface.pytorch/nlptown/bert-base-multilingual-uncased-sentiment")
                        .optEngine("PyTorch")
                        .optTranslatorFactory(new TextClassificationTranslatorFactory())
                        .optProgress(new ProgressBar())
                        .build();
         
                        System.out.println("Loading Model");

                        return criteria.loadModel();
    }
}
