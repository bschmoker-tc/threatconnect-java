/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.service.error;

import com.cyber2.api.lib.server.response.service.ApiServiceResponse;

/**
 *
 * @author James
 */
public class OwnerNotFoundApiServiceResponse extends ApiServiceResponse
{
    public OwnerNotFoundApiServiceResponse()
    {
        super("The requested owner was not found");
    }
}
