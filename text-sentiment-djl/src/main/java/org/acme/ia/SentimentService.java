package org.acme.ia;

import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.Classifications.Classification;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SentimentService {
    
    @Inject
    @BertBase
    ZooModel<String, Classifications> bertBase;

    public String analyze(String text) throws TranslateException {
        try (Predictor<String, Classifications> predictor = bertBase.newPredictor()) {
            Classifications res = predictor.predict(text);
            Classification best = res.best();

            return best.getClassName();
        }
    }

    @PreDestroy
    public void closeModels() {
        this.bertBase.close();
    }

}
