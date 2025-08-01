package com.cloud.cloud_rest.corp;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorpService {
    private final CorpRepository corpRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public CorpResponse.CorpDTO save(CorpRequest.SaveDTO saveDTO){
        String bcyPassword = bCryptPasswordEncoder.encode(saveDTO.getPassword());
        Corp corp = saveDTO.toEntity(bcyPassword);
        corpRepository.save(corp);
        return new CorpResponse.CorpDTO(corp);
    }

}
