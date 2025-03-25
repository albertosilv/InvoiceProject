package org.invoice.controller;

import org.invoice.dto.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.invoice.service.invoice.InvoiceService;

@Path("/api/invoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Notas Fiscais", description = "Operações de gerenciamento de notas fiscais")
public class InvoiceController {

    @Inject
    InvoiceService invoiceService;

    @POST
    @Operation(summary = "Cria uma nova nota fiscal")
    public Response create(@Valid InvoiceDTO invoiceDTO) {
        InvoiceResponseDTO response = invoiceService.create(invoiceDTO);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Busca uma nota fiscal por ID")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(invoiceService.findById(id)).build();
    }

    @GET
    @Operation(summary = "Lista todas as notas fiscais")
    public Response findAll(@QueryParam("filter") String filter) {
        return Response.ok(invoiceService.findAll(filter)).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza uma nota fiscal existente")
    public Response update(
            @PathParam("id") Long id,
            @Valid InvoiceDTO invoiceDTO) {
        return Response.ok(invoiceService.update(id, invoiceDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Exclui uma nota fiscal")
    public Response delete(@PathParam("id") Long id) {
        invoiceService.delete(id);
        return Response.noContent().build();
    }


    @POST
    @Path("/{invoiceId}/items")
    @Operation(summary = "Adiciona um item a uma nota fiscal")
    public Response addItem(
            @PathParam("invoiceId") Long invoiceId,
            @Valid InvoiceItemDTO itemDTO) {
        InvoiceItemResponseDTO response = invoiceService.createItem(invoiceId, itemDTO);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{invoiceId}/items/{itemId}")
    @Operation(summary = "Atualiza um item de uma nota fiscal")
    public Response updateItem(
            @PathParam("invoiceId") Long invoiceId,
            @PathParam("itemId") Long itemId,
            @Valid InvoiceItemDTO itemDTO) {
        return Response.ok(invoiceService.updateItem(invoiceId, itemId, itemDTO)).build();
    }

    @DELETE
    @Path("/{invoiceId}/items/{itemId}")
    @Operation(summary = "Remove um item de uma nota fiscal")
    public Response removeItem(
            @PathParam("invoiceId") Long invoiceId,
            @PathParam("itemId") Long itemId) {
        invoiceService.removeItem(invoiceId, itemId);
        return Response.noContent().build();
    }
}