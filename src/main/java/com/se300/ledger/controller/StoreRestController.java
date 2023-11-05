package com.se300.ledger.controller;

import com.se300.ledger.model.Store;
import com.se300.ledger.model.StoreModelException;
import com.se300.ledger.service.StoreModelAPI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * StoreRestController class implementation exposing Store API
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
@RestController
@SecurityRequirement(name = "basicAuth")
public class StoreRestController {

    @Autowired
    StoreModelAPI storeModelService;

    @Operation(summary = "Create Store", tags = {"stores"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Store.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/stores")
    public Store createStore(@RequestBody Store store) throws StoreModelException {
        return storeModelService.provisionStore(store.getId(), store.getAddress(), store.getDescription(), null);
    }

    @Operation(summary = "Retrieve Store By Id", tags = {"stores"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Store.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", description = "There is no stores with such id", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/stores/{id}")
    public Store getStoreById(@PathVariable Long id) throws StoreModelException {
        return storeModelService.showStore(id, null);
    }

}