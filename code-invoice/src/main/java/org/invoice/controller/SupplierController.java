package org.invoice.controller;

import jakarta.validation.Valid;
import org.invoice.dto.ProductDTO;
import org.invoice.dto.ProductResponseDTO;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.invoice.dto.SupplierDTO;
import org.invoice.dto.SupplierResponseDTO;
import org.invoice.service.supplier.SupplierService;

@Path("/api/suppliers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Fornecedores", description = "Operações de gerenciamento de fornecedores")
public class SupplierController {

    @Inject
    SupplierService supplierService;

    @GET
    @Operation(summary = "Listar fornecedores",
            description = "Retorna todos os fornecedores ou filtra por termo")
    public Response findAll(
            @QueryParam("filter") String filter) {
        return Response.ok(supplierService.findAll(filter)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obter fornecedor por ID")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(supplierService.findById(id)).build();
    }

    @POST
    @Operation(summary = "Criar novo fornecedor")
    public Response create(@Valid SupplierDTO supplierDTO) {
        SupplierResponseDTO created = supplierService.create(supplierDTO);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar fornecedor existente")
    public Response update(
            @PathParam("id") Long id,
            @Valid SupplierDTO supplierDTO) {
        return Response.ok(supplierService.update(id, supplierDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir fornecedor")
    public Response delete(@PathParam("id") Long id) {
        supplierService.delete(id);
        return Response.noContent().build();
    }
}