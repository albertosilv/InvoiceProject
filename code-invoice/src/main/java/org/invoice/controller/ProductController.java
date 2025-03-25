package org.invoice.controller;

import jakarta.validation.Valid;
import org.invoice.dto.ProductDTO;
import org.invoice.dto.ProductResponseDTO;
import org.invoice.service.product.ProductService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Produtos", description = "Operações de gerenciamento de produtos")
public class ProductController {

    @Inject
    ProductService productService;

    @GET
    @Operation(summary = "Listar produtos",
            description = "Retorna todos os produtos ou filtra por descrição")
    public Response findAll(
            @QueryParam("filter") String filter) {
        return Response.ok(productService.findAll(filter)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obter produto por ID")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(productService.findById(id)).build();
    }

    @POST
    @Operation(summary = "Criar novo produto")
    public Response create(@Valid ProductDTO productDTO) {
        ProductResponseDTO created = productService.create(productDTO);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar produto existente")
    public Response update(
            @PathParam("id") Long id,
            @Valid ProductDTO productDTO) {
        return Response.ok(productService.update(id, productDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir produto")
    public Response delete(@PathParam("id") Long id) {
        productService.delete(id);
        return Response.noContent().build();
    }
}