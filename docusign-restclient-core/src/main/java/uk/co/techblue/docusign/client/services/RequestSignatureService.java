/*******************************************************************************
 * Copyright 2012 Technology Blueprint Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package uk.co.techblue.docusign.client.services;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import uk.co.techblue.docusign.client.BaseService;
import uk.co.techblue.docusign.client.credential.DocuSignCredentials;
import uk.co.techblue.docusign.client.dto.DocumentSignatureRequest;
import uk.co.techblue.docusign.client.dto.SignatureResponse;
import uk.co.techblue.docusign.client.dto.TemplateSignatureRequest;
import uk.co.techblue.docusign.client.dto.user.LoginAccount;
import uk.co.techblue.docusign.client.exception.ServiceInitException;
import uk.co.techblue.docusign.client.exception.SignatureRequestException;
import uk.co.techblue.docusign.client.resources.RequestSignatureResource;
import uk.co.techblue.docusign.client.utils.DocuSignUtils;

/**
 * The Class RequestSignatureService.
 */
public class RequestSignatureService extends BaseService<RequestSignatureResource> {

    /**
     * Instantiates a new request signature service.
     * 
     * @param serverUri
     *            the server uri
     * @param credentials
     *            the credentials
     * @throws ServiceInitException
     *             the service init exception
     */
    public RequestSignatureService(final String serverUri, final DocuSignCredentials credentials)
            throws ServiceInitException {
        super(serverUri, credentials);
    }

    /**
     * Instantiates a new request signature service.
     * 
     * @param serverUri
     *            the server uri
     * @param credentials
     *            the credentials
     * @param loginAccount
     *            the login account
     * @throws ServiceInitException
     *             the service init exception
     */
    public RequestSignatureService(final String serverUri, final DocuSignCredentials credentials,
            final LoginAccount loginAccount) throws ServiceInitException {
        super(loginAccount, credentials);
    }

    /**
     * Send from template.
     * 
     * @param signatureRequest
     *            the signature request
     * @return the signature response
     * @throws SignatureRequestException
     *             the signature request exception
     */
    public SignatureResponse sendFromTemplate(final TemplateSignatureRequest signatureRequest) throws SignatureRequestException {
        final Response clientResponse = resourceProxy.sendFromTemplate(signatureRequest);
        return parseEntityFromResponse(clientResponse, SignatureResponse.class, SignatureRequestException.class);
    }

    /**
     * Send document.
     * 
     * @param signatureRequest
     *            the signature request
     * @return the signature response
     * @throws SignatureRequestException
     *             the signature request exception
     */
    public SignatureResponse sendDocument(final DocumentSignatureRequest signatureRequest) throws SignatureRequestException {
        final MultipartFormDataOutput dataOut = DocuSignUtils.generateMultipartFormDataOutput(signatureRequest);
        final Response clientResponse = resourceProxy.sendDocument(dataOut);
        return parseEntityFromResponse(clientResponse, SignatureResponse.class, SignatureRequestException.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.co.techblue.docusign.client.Service#getResourceClass()
     */
    @Override
    protected Class<RequestSignatureResource> getResourceClass() {
        return RequestSignatureResource.class;
    }

}
