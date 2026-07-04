package pet_utf8;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OwnerMapper {
    List<Owner> selectAll();
    int insert(Owner owner);
    int update(Owner owner);
    int delete(@Param("oid") int oid);
}