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

    @Query(nativeQuery = true, value = "select * from otq where lid=:lid AND downloaded=false")
    OTQ findByLidEvenDeleted(@Param("lid") String lid);

    @Query(nativeQuery = true, value = "select * from otq where business_id=:id and is_deleted=false limit 1")
    OTQ findByBusinessId(@Param("id") String businessId);

    @Query(nativeQuery = true, value = "select * from otq where otq.business_id=:id")
    List<OTQ> findAllByBusinessId(@Param("id") String businessId);

    @Query(value = "select * from otq where user_id = :userId"
            , nativeQuery = true
            , countQuery = "select count(*) from otq where user_id = :userId")
    Page<OTQ> findAllByUserId(Pageable pageable, @Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select * from otq;")
    List<OTQ> findAllForAdmin();

    @Query(nativeQuery = true, value = "select count(*) from otq")
    Long countAllOTQS();

    @Query(nativeQuery = true
            , value = "select * from otq where user_id=:uid and business_id=:bid order by created_at asc limit 1")
    OTQ findFirstByUserIdAndBusinessIdOrderByCreatedAtAsc(@Param("uid") Long userId, @Param("bid") String businessId);

    @Query(nativeQuery = true
            , value = "select * from otq where user_id=:uid and business_id=:bid order by created_at desc limit 1")
    OTQ findFirstByUserIdAndBusinessIdOrderByCreatedAtDesc(@Param("uid") Long userId, @Param("bid") String businessId);
}
