package org.acme;

import java.util.List;

import org.acme.model.Product;
import org.acme.model.ProductWithComments;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class ProductResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("products")
    public List<Product> products() {
        return Product.listAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("productsWithComments")
    public List<ProductWithComments> productsWithComments() {
        return ProductWithComments.listAll();
    }

}
