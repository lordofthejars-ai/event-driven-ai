package org.acme;

import java.io.IOException;

import org.acme.ia.SentimentService;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * REST Processor
 */
@Path("/hello")
public class SentimentAnalysisResource {

    @Inject
    SentimentService sentimentService;    

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String hello(String text) throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        return sentimentService.analyze(text);
    }
}
