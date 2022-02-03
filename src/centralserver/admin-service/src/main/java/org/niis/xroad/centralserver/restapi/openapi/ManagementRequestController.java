/**
 * The MIT License
 *
 * Copyright (c) 2019- Nordic Institute for Interoperability Solutions (NIIS)
 * Copyright (c) 2018 Estonian Information System Authority (RIA),
 * Nordic Institute for Interoperability Solutions (NIIS), Population Register Centre (VRK)
 * Copyright (c) 2015-2017 Estonian Information System Authority (RIA), Population Register Centre (VRK)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.niis.xroad.centralserver.restapi.openapi;

import lombok.RequiredArgsConstructor;
import org.niis.xroad.centralserver.openapi.ManagementRequestsApi;
import org.niis.xroad.centralserver.openapi.model.ManagementRequest;
import org.niis.xroad.centralserver.openapi.model.ManagementRequestPage;
import org.niis.xroad.centralserver.restapi.converter.ManagementRequestConverter;
import org.niis.xroad.centralserver.restapi.service.ManagementRequestService;
import org.niis.xroad.restapi.config.audit.AuditEventMethod;
import org.niis.xroad.restapi.config.audit.RestApiAuditEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManagementRequestController implements ManagementRequestsApi {

    private final ManagementRequestService service;
    private final ManagementRequestConverter converter = new ManagementRequestConverter();

    @Override
    @AuditEventMethod(event = RestApiAuditEvent.ADD_MANAGEMENT_REQUEST)
    @PreAuthorize("hasPermission(#request, 'ADD')")
    public ResponseEntity<Integer> addManagementRequest(ManagementRequest request) {
        return ResponseEntity.ok(service.addRequest(converter.convert(request)));
    }

    @Override
    @PreAuthorize("hasAuthority('VIEW_MANAGEMENT_REQUESTS')")
    public ResponseEntity<ManagementRequestPage> getManagementRequests() {
        return null;
    }

    @Override
    @AuditEventMethod(event = RestApiAuditEvent.REVOKE_MANAGEMENT_REQUEST)
    @PreAuthorize("hasPermission(#id, 'MANAGEMENT_REQUEST', 'REVOKE')")
    public ResponseEntity<Void> revokeManagementRequest(Integer id) {
        return null;
    }
}
