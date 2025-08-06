package com.cloud.cloud_rest.corpskill;

import com.cloud.cloud_rest.corp.Corp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CorpSkillRepository extends JpaRepository<CorpSkill,CorpSkillId> {

    @Query("""
        select distinct cs.skill.name
        from CorpSkill cs
        where cs.corp = :corp
    """)
    List<String> findByCorpMatch(@Param("corp") Corp corp);


}
