package me.winterbelle.prompvault.repositories;

import me.winterbelle.prompvault.models.data.PolicyKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyKeywordRepository extends JpaRepository<PolicyKeyword, Long> {
}
