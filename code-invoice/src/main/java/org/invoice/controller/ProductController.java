package org.invoice.controller;

import org.invoice.model.Product;
import org.invoice.service.product.ProductService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
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
    @Operation(
            summary = "Listar todos os produtos",
            description = "Recupera uma lista de produtos, com filtro opcional por descrição"
    )
    public Response findAll(
            @Parameter(description = "Termo para filtrar produtos por descrição")
            @QueryParam("filter") String filter) {
        return Response.ok(productService.list(filter)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Obter produto por ID",
            description = "Recupera um produto específico com base no ID fornecido"
    )
    public Response findById(
            @Parameter(description = "ID do produto a ser recuperado", required = true)
            @PathParam("id") Long id) {
        return Response.ok(productService.getById(id)).build();
    }

    @POST
    @Operation(
            summary = "Criar um novo produto",
            description = "Cadastra um novo produto no sistema"
    )
    public Response create(Product product) {
        Product createdProduct = productService.create(product);
        return Response
                .status(Response.Status.CREATED)
                .entity(createdProduct)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Atualizar um produto existente",
            description = "Atualiza as informações de um produto já cadastrado"
    )
    public Response update(
            @Parameter(description = "ID do produto a ser atualizado", required = true)
            @PathParam("id") Long id,
            Product product) {
        Product updatedProduct = productService.update(id, product);
        return Response.ok(updatedProduct).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Excluir um produto",
            description = "Remove um produto do sistema, desde que não tenha movimentações associadas"
    )
    public Response delete(
            @Parameter(description = "ID do produto a ser excluído", required = true)
            @PathParam("id") Long id) {
        productService.delete(id);
        return Response.noContent().build();
    }
}