package ir.uid.model.repository;

import ir.uid.model.entity.OTQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OTQRepository extends JpaRepository<OTQ, String> {
    OTQ findByLid(String lid);
}
