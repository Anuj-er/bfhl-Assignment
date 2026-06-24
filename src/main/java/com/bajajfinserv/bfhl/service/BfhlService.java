package com.bajajfinserv.bfhl.service;

import com.bajajfinserv.bfhl.dto.BfhlRequest;
import com.bajajfinserv.bfhl.dto.BfhlResponse;

public interface BfhlService {

    BfhlResponse process(BfhlRequest request);
}
