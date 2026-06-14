package me.winterbelle.prompvault.services.policykeyword;

import me.winterbelle.prompvault.models.data.PolicyKeyword;
import me.winterbelle.prompvault.models.dtos.PolicyKeywordDto;

import java.util.List;

public interface PolicyKeywordService {
    List<PolicyKeyword> findAll();

    PolicyKeyword findById(Long id);

    PolicyKeyword create(PolicyKeywordDto dto);

    PolicyKeyword update(Long id, PolicyKeywordDto dto);

    void deleteById(Long id);
}